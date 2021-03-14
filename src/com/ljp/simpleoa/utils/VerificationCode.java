package com.ljp.simpleoa.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class VerificationCode {
	
	private static AtomicLong increment = new AtomicLong(0);
	private static Random random = new Random(System.currentTimeMillis());
	private static Map<Long,VerificationCode> map = new ConcurrentHashMap<>();
	
	private long id;
	private String code;
	private Date expires;
	public VerificationCode(long id, String code, Date expires) {
		super();
		this.id = id;
		this.code = code;
		this.expires = expires;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getExpires() {
		return expires;
	}
	public void setExpires(Date expires) {
		this.expires = expires;
	}
	public boolean isExpires() {
		System.out.println(expires);
		System.out.println(new Date(System.currentTimeMillis()));
		boolean b = expires.before(new Date(System.currentTimeMillis()));
		System.out.println(b);
		return b;
	}
	public static VerificationCode creat(int bits,int validitySecond) {
		if(map.size()>1000) {
			Iterator<Long> iterator = map.keySet().iterator();
			while(iterator.hasNext()) {
				long id =iterator.next();
				if(map.get(id).isExpires()) {
					map.remove(id);
				}
			}
		}
		int nextInt = random.nextInt((int)Math.pow(10, bits));
		Date date = new Date(System.currentTimeMillis()+1000*60*validitySecond);
		VerificationCode code = new VerificationCode(increment.incrementAndGet(),String.valueOf(nextInt),date);
		map.put(code.getId(),code);
		return code;
	}
	
	public static VerificationCode creat() {
		return creat(6,5);
	}
	
	public static String get(long id) {
		VerificationCode remove = map.remove(id);
		if(remove.isExpires())return null;
		return remove.getCode();
	}
}
