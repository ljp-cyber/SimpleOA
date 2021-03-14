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
		MimeMessage mMessage=javaMailSender.createMimeMessage();//�����ʼ�����
        MimeMessageHelper mMessageHelper;
        Properties prop = new Properties();
        String from;
        try {
            //�������ļ����õ������������ַ
            prop.load(this.getClass().getResourceAsStream("/email.properties"));
            from = prop.get("mail.smtp.username")+"";
            mMessageHelper=new MimeMessageHelper(mMessage,true);
            mMessageHelper.setFrom(from);//����������
            mMessageHelper.setTo("460222822@qq.com");//�ռ�������
            mMessageHelper.setSubject("��ʼ�����ݿ�");//�ʼ�������
            VerificationCode code = VerificationCode.creat();
            request.getSession().setAttribute("codeId", code.getId());
            mMessageHelper.setText(code.getCode());//�ʼ����ı����ݣ�true��ʾ�ı���html��ʽ��
            //File file=new File("C:\\Users\\lcl\\Pictures\\Saved Pictures\\blog.csdn.net_Mr__Viking_article_details_81090046.png");//���ʼ������һ��ͼƬ 
            //FileSystemResource resource=new FileSystemResource(file);
            //mMessageHelper.addInline("fengye", resource);//����ָ��һ��id,����������  
            //mMessageHelper.addAttachment("QQ��ͼ20200721221932.png", resource);//���ʼ������һ������  
            javaMailSender.send(mMessage);//�����ʼ�
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		ServletContext servletContext = request.getServletContext();
		servletContext.setAttribute("message", "��֤�뷢�ͳɹ�");
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
					servletContext.setAttribute("message", "�ָ��ɹ�");
				}else {
					servletContext.setAttribute("message", "��֤�����");
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
			System.out.println("session����"+allSessions.size());
			for (int j = 0; j < allSessions.size(); j++) {
				SessionInformation sessionInformation=allSessions.get(j);
				System.out.println("sessionRegistry��"+sessionInformation.getSessionId());
				System.out.println("sessionRegistry��"+df.format(sessionInformation.getLastRequest()));
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
				model.addAttribute("changePasswordInfo", "�����벻ƥ��,��λ�ô���");
			}else {
				model.addAttribute("changePasswordInfo", "�޸ĳɹ���");
				model.addAttribute("worker", worker);
				System.out.println(worker);
			}
		}else {
			model.addAttribute("changePasswordInfo", "�������������벻��ͬ��");
		}
		
		return "change_password";
	}
	
//	@RequestMapping(value= {"/login_do"},method=RequestMethod.POST)
//	public String login_do(HttpSession httpSession,@RequestParam Worker worker) {
//		Worker loginWorker = loginService.login(worker);
//		if(loginWorker==null) {
//			System.out.println("�˺Ż��������");
//			return "login";
//		}
//		else {
//			System.out.println("��½�ɹ�");
//			httpSession.setAttribute("worker", worker);
//			return "login";
//		}
//	}
}
