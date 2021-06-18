package com.ljp.constroller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ljp.bean.Worker;
import com.ljp.security.SessionRegistrySerializable;
import com.ljp.serviceInterface.LoginServiceFace;

@Controller
public class LoginController {
	
	@Autowired
	private SessionRegistrySerializable sessionRegistry;

	private LoginServiceFace loginService;
	
	@Autowired
	public LoginController(LoginServiceFace loginService) {
		this.loginService=loginService;
		System.out.println("LoginController.init");
	}
	
	@RequestMapping(value= {"/","/index"},method=RequestMethod.GET)
	public String index(HttpServletRequest request) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("index");
		
		HttpSession session = request.getSession();
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		System.out.println(authentication.getClass());
		Worker worker = loginService.queryOne(authentication.getName());
		session.setAttribute("worker", worker);
				
		//sessionRegistry.clean();
		List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
		int sessionCount=0;
		for (int i = 0; i < allPrincipals.size(); i++) {
			User user=(User)allPrincipals.get(i);
			System.out.println(user);
			List<SessionInformation> allSessions = sessionRegistry.getAllSessions(allPrincipals.get(i), false);
			for (int j = 0; j < allSessions.size(); j++) {
				SessionInformation sessionInformation=allSessions.get(j);
				System.out.println("sessionRegistry"+sessionInformation.getSessionId());
				System.out.println("sessionRegistry"+df.format(sessionInformation.getLastRequest()));
			}
			sessionCount+=allSessions.size();
		}
//		servletContext.setAttribute("userCount", allPrincipals.size());
//		servletContext.setAttribute("sessionCount", sessionCount);
		session.setAttribute("userCount", allPrincipals.size());
		session.setAttribute("sessionCount", sessionCount);

		

		return "index";
	}
	
	@RequestMapping(value= {"/login"},method=RequestMethod.GET)
	public String login() {
		System.out.println("login");
		return "login";
	}
	
	@RequestMapping(value= {"/to_change_password"},method=RequestMethod.GET)
	public String change_passwork() {
		System.out.println("to_change_password");
		return "change_password";
	}
	
	@RequestMapping(value= {"/change_password"},method=RequestMethod.POST)
	public String change_passwork(
			@RequestParam String old,@RequestParam String new1, @RequestParam String new2,
			Model model,HttpServletRequest request) {
		System.out.println("change_password");
		
		HttpSession session = request.getSession();
		if(new1.equals(new2)) {
			Worker worker = loginService.changePasswork((Worker) session.getAttribute("worker"), old, new1);
			if(worker==null) {
				model.addAttribute("changePasswordInfo", "旧密码不匹配,或位置错误！");
			}else {
				model.addAttribute("changePasswordInfo", "修改成功！");
				model.addAttribute("worker", worker);
				System.out.println(worker);
			}
		}else {
			model.addAttribute("changePasswordInfo", "两次新密码输入不相同！");
		}
		
		return "change_password";
	}
}
