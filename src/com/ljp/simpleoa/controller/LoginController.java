package com.ljp.simpleoa.controller;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletContext;
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

import com.ljp.simpleoa.model.Worker;
import com.ljp.simpleoa.security.SessionRegistrySerializable;
import com.ljp.simpleoa.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	private SessionRegistrySerializable sessionRegistry;

	private LoginService loginService;
	
	@Autowired
	public LoginController(LoginService loginService) {
		this.loginService=loginService;
		System.out.println("LoginController.init");
	}
	
	@RequestMapping(value= {"/","/index"},method=RequestMethod.GET)
	public String index(HttpServletRequest request) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("index");
		
		HttpSession session = request.getSession();
		System.out.println("当前session："+session.getId());
		ServletContext servletContext = request.getServletContext();
		System.out.println(servletContext.getServletContextName());
		System.out.println(servletContext.getServerInfo());
		
		//展现request属性
//		Enumeration<String> attributeNames2 = request.getAttributeNames();
//		while(attributeNames2.hasMoreElements()) {
//			String str=attributeNames2.nextElement();
//			System.out.println("keyrequest:"+str);
//			System.out.println("value:"+request.getAttribute(str));
//		}
		//展现session属性
//		Enumeration<String> attributeNames = session.getAttributeNames();
//		while(attributeNames.hasMoreElements()) {
//			String str=attributeNames.nextElement();
//			System.out.println("keysession:"+str);
//			System.out.println("value:"+session.getAttribute(str));
//		}
		//当前用户名
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
			System.out.println("session个数"+allSessions.size());
			for (int j = 0; j < allSessions.size(); j++) {
				SessionInformation sessionInformation=allSessions.get(j);
				System.out.println("sessionRegistry："+sessionInformation.getSessionId());
				System.out.println("sessionRegistry："+df.format(sessionInformation.getLastRequest()));
			}
			sessionCount+=allSessions.size();
		}
		servletContext.setAttribute("userCount", allPrincipals.size());
		servletContext.setAttribute("sessionCount", sessionCount);

		

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
	
//	@RequestMapping(value= {"/login_do"},method=RequestMethod.POST)
//	public String login_do(HttpSession httpSession,@RequestParam Worker worker) {
//		Worker loginWorker = loginService.login(worker);
//		if(loginWorker==null) {
//			System.out.println("账号或密码错误");
//			return "login";
//		}
//		else {
//			System.out.println("登陆成功");
//			httpSession.setAttribute("worker", worker);
//			return "login";
//		}
//	}
}
