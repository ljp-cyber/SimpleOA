package com.ljp.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.util.Assert;

import com.ljp.utils.IOUtils;

/**
 * 官方的SessionRegistryImpl在服务器重启后数据全部丢失，导致用户多点登陆验证有误，需要写一个可以支持重启的
 * 服务器关闭时把其序列化到本地，启动后加载进来继续运行
 * 
 * @author LiJunPeng
 *
 */
public class SessionRegistrySerializable
		implements SessionRegistry, ApplicationListener<SessionDestroyedEvent>, Serializable {

	private static final long serialVersionUID = -1536576935772309465L;

	protected final Log logger = LogFactory.getLog(SessionRegistrySerializable.class);

	public static final String fileUrl = "/SessionRegistry";

	/** <principal:Object,SessionIdSet> */
	private ConcurrentMap<Object, Set<String>> principals = new ConcurrentHashMap<Object, Set<String>>();
	/** <sessionId:Object,SessionInformation> */
	private Map<String, SessionInformation> sessionIds = new ConcurrentHashMap<String, SessionInformation>();

	public void saveAllSessionInformations() throws Exception {
		System.out.println("正在保存所有会话信息");
		List<Object> allPrincipals = this.getAllPrincipals();
		System.out.println("有几位用户:" + allPrincipals.size());
		for (Object object : allPrincipals) {
			List<SessionInformation> allSessions = this.getAllSessions(object, false);
			System.out.println("有几个会话:" + allSessions.size());
		}
		int i = 0;
		while (!IOUtils.saveObj(fileUrl, this) && i++ < 5) {
			System.out.println("保存失败正在重试。。。");
		}
	}

	public void loadAllSessionInformations() {
		System.out.println("这次自动读取啦！");
		if (principals != null) {
			System.out.println("principals:" + principals.size());
			System.out.println("sessionIds" + sessionIds.size());
		} else {
			principals = new ConcurrentHashMap<Object, Set<String>>();
			sessionIds = new ConcurrentHashMap<String, SessionInformation>();
		}
	}

	@Override
	public void onApplicationEvent(SessionDestroyedEvent arg0) {
		String sessionId = arg0.getId();
		removeSessionInformation(sessionId);
	}

	@Override
	public List<Object> getAllPrincipals() {
		return new ArrayList<Object>(principals.keySet());
	}
	
	public void show() {
		System.out.println("->SessionRegistry.principals.size:"+principals.keySet().size());
		System.out.println("->SessionRegistry.sessionIds.size:"+sessionIds.keySet().size());
		for(Object principal:principals.keySet()) {
			System.out.println("->SessionRegistry.principals:"+principal);
			Set<String> principalSessionIds = principals.get(principal);
			for (String string : principalSessionIds) {
				System.out.println("  sessionId:"+string);
				System.out.println("  lastVisitedTime:"+sessionIds.get(string).getLastRequest());
				System.out.println("  isExpired:"+sessionIds.get(string).isExpired());
			}
		}
	}

	/**
	 * 清理失效session会马上导致并发检测失效了，filter检测不到失效的session
	 * 清理很久不活动的会怎样还有待考究
	 */
	public void clean() {
		long time = 1000 * 60 * 30;
		for (String sessionId : sessionIds.keySet()) {
			SessionInformation sessionInformation = sessionIds.get(sessionId);
			// 清理过期不活跃的session，和没有‘活跃session’的用户
			if (new Date().getTime() - sessionInformation.getLastRequest().getTime() > time) {
				sessionIds.remove(sessionId);

				Set<String> set = principals.get(sessionInformation.getPrincipal());
				if (set != null) {
					set.remove(sessionId);
					if (set.isEmpty()) {
						principals.remove(sessionInformation.getPrincipal());
					}
				}
			}
		}
	}

	@Override
	public List<SessionInformation> getAllSessions(Object principal, boolean includeExpiredSessions) {
		final Set<String> sessionsUsedByPrincipal = principals.get(principal);

		if (sessionsUsedByPrincipal == null) {
			return Collections.emptyList();
		}

		List<SessionInformation> list = new ArrayList<SessionInformation>(sessionsUsedByPrincipal.size());

		for (String sessionId : sessionsUsedByPrincipal) {
			SessionInformation sessionInformation = getSessionInformation(sessionId);

			if (sessionInformation == null) {
				continue;
			}

			if (includeExpiredSessions || !sessionInformation.isExpired()) {
				list.add(sessionInformation);
			}
		}

		return list;
	}

	@Override
	public SessionInformation getSessionInformation(String sessionId) {
		Assert.hasText(sessionId, "SessionId required as per interface contract");

		return sessionIds.get(sessionId);

	}

	@Override
	public void refreshLastRequest(String sessionId) {
		Assert.hasText(sessionId, "SessionId required as per interface contract");

		SessionInformation info = getSessionInformation(sessionId);

		if (info != null) {
			info.refreshLastRequest();
		}

	}

	@Override
	public void registerNewSession(String sessionId, Object principal) {
		Assert.hasText(sessionId, "SessionId required as per interface contract");
		Assert.notNull(principal, "Principal required as per interface contract");

		if (logger.isDebugEnabled()) {
			logger.debug("Registering session " + sessionId + ", for principal " + principal);
		}

		if (getSessionInformation(sessionId) != null) {
			removeSessionInformation(sessionId);
		}

		sessionIds.put(sessionId, new SessionInformation(principal, sessionId, new Date()));

		Set<String> sessionsUsedByPrincipal = principals.get(principal);

		if (sessionsUsedByPrincipal == null) {
			sessionsUsedByPrincipal = new CopyOnWriteArraySet<String>();
			Set<String> prevSessionsUsedByPrincipal = principals.putIfAbsent(principal, sessionsUsedByPrincipal);
			if (prevSessionsUsedByPrincipal != null) {
				sessionsUsedByPrincipal = prevSessionsUsedByPrincipal;
			}
		}

		sessionsUsedByPrincipal.add(sessionId);

		if (logger.isTraceEnabled()) {
			logger.trace("Sessions used by '" + principal + "' : " + sessionsUsedByPrincipal);
		}

	}

	@Override
	public void removeSessionInformation(String sessionId) {
		Assert.hasText(sessionId, "SessionId required as per interface contract");

		SessionInformation info = getSessionInformation(sessionId);

		if (info == null) {
			return;
		}

		if (logger.isTraceEnabled()) {
			logger.debug("Removing session " + sessionId + " from set of registered sessions");
		}

		sessionIds.remove(sessionId);

		Set<String> sessionsUsedByPrincipal = principals.get(info.getPrincipal());

		if (sessionsUsedByPrincipal == null) {
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Removing session " + sessionId + " from principal's set of registered sessions");
		}

		sessionsUsedByPrincipal.remove(sessionId);

		if (sessionsUsedByPrincipal.isEmpty()) {
			// No need to keep object in principals Map anymore
			if (logger.isDebugEnabled()) {
				logger.debug("Removing principal " + info.getPrincipal() + " from registry");
			}
			principals.remove(info.getPrincipal());
		}

		if (logger.isTraceEnabled()) {
			logger.trace("Sessions used by '" + info.getPrincipal() + "' : " + sessionsUsedByPrincipal);
		}

	}

}
