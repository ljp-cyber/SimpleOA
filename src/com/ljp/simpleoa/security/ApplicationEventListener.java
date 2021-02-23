package com.ljp.simpleoa.security;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.security.core.session.SessionRegistry;

/**
 * 服务器关闭把会话信息保存到本地
 */
//@Component
public class ApplicationEventListener implements ApplicationListener<ApplicationEvent> {
	
	private boolean save = false;
	
	@Override
	public void onApplicationEvent(ApplicationEvent arg0) {
		if (arg0 instanceof ContextClosedEvent) {
			System.out.println("application close:" + arg0.getSource() + "," + arg0.getTimestamp());
			if (!save) {
				ApplicationContext applicationContext = ((ContextClosedEvent) arg0).getApplicationContext();
				try {
					SessionRegistrySerializable registrySerializable = (SessionRegistrySerializable) applicationContext
							.getBean(SessionRegistry.class);
					registrySerializable.saveAllSessionInformations();
					save = true;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			 //System.out.println("application ohter event:" + arg0.getSource() + "," + arg0.getTimestamp());
		}
	}
}
