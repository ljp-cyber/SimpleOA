package com.ljp.simpleoa.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ljp.simpleoa.model.Worker;
import com.ljp.simpleoa.service.BackupService;
import com.ljp.simpleoa.service.LoginService;
import com.ljp.simpleoa.utils.VerificationCode;

@Controller
public class LoginController {
	
	@Autowired
//	private SessionRegistrySerializable sessionRegistry;
	private SessionRegistry sessionRegistry;

	private LoginService loginService;
	
	@Autowired
	private BackupService backupService;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	public LoginController(LoginService loginService) {
		this.loginService=loginService;
		System.out.println("LoginController.init");
	}
	
	@RequestMapping(value= {"/backup"},method=RequestMethod.GET)
	public String backup(HttpServletRequest request) {
		MimeMessage mMessage=javaMailSender.createMimeMessage();//创建邮件对象
        MimeMessageHelper mMessageHelper;
        Properties prop = new Properties();
        String from;
        try {
            //从配置文件中拿到发件人邮箱地址
            prop.load(this.getClass().getResourceAsStream("/email.properties"));
            from = prop.get("mail.smtp.username")+"";
            mMessageHelper=new MimeMessageHelper(mMessage,true);
            mMessageHelper.setFrom(from);//发件人邮箱
            mMessageHelper.setTo("460222822@qq.com");//收件人邮箱
            mMessageHelper.setSubject("初始化数据库");//邮件的主题
            VerificationCode code = VerificationCode.creat();
            request.getSession().setAttribute("codeId", code.getId());
            mMessageHelper.setText(code.getCode());//邮件的文本内容，true表示文本以html格式打开
            //File file=new File("C:\\Users\\lcl\\Pictures\\Saved Pictures\\blog.csdn.net_Mr__Viking_article_details_81090046.png");//在邮件中添加一张图片 
            //FileSystemResource resource=new FileSystemResource(file);
            //mMessageHelper.addInline("fengye", resource);//这里指定一个id,在上面引用  
            //mMessageHelper.addAttachment("QQ截图20200721221932.png", resource);//在邮件中添加一个附件  
            javaMailSender.send(mMessage);//发送邮件
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		ServletContext servletContext = request.getServletContext();
		servletContext.setAttribute("message", "验证码发送成功");
		return "redirect:/websiteInfo";
	}
	
	@RequestMapping(value= {"/backup_do"},method=RequestMethod.GET)
	public String backup(@RequestParam String code,HttpServletRequest httpServletRequest) {
		System.out.println(code);
		Object object = httpServletRequest.getSession().getAttribute("codeId");
//		String path = httpServletRequest.getContextPath()+"/WEB-INF/classes/simpleoa.sql";
		String path = httpServletRequest.getServletContext().getRealPath("")+"\\WEB-INF\\classes\\simpleoa.sql";
		File f = new File(path);
		System.out.println(f.exists());
		System.out.println(path);
			if(object!=null) {
				long codeId = (Long)object;
				httpServletRequest.getSession().removeAttribute("codeId");
				String string = VerificationCode.get(codeId);
				ServletContext servletContext = httpServletRequest.getServletContext();
				System.out.println(code+","+string);
				if(code.equals(string)) {
					backupService.resetDataBase(path);
					servletContext.setAttribute("message", "恢复成功");
				}else {
					servletContext.setAttribute("message", "验证码错误");
				}
		}
		return "websiteInfo";
	}
	
	@RequestMapping(value= {"/websiteInfo"},method=RequestMethod.GET)
	public String websiteInfo(HttpServletRequest request) {
		//sessionRegistry.clean();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ServletContext servletContext = request.getServletContext();
		System.out.println(servletContext.getServletContextName());
		System.out.println(servletContext.getServerInfo());
		List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
		int sessionCount=0;
		for (int i = 0; i < allPrincipals.size(); i++) {
			UserDetails user=(UserDetails)allPrincipals.get(i);
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

		return "websiteInfo";
	}
	
	@RequestMapping(value= {"/","/index"},method=RequestMethod.GET)
	public String index(HttpServletRequest request) {
		System.out.println("index");
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
		
//		HttpSession session = request.getSession();
		if(new1.equals(new2)) {
			Worker user = (Worker)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Worker worker = loginService.changePasswork(user, old, new1);
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
