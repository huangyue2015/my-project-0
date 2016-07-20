package com.common.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
	/**
	 * 用来锁定手机号，，（每天只能发送10条短信的缓存）
	 */
	public static Map<String,Integer> mobileSendLockMap = new ConcurrentHashMap<>();
	
	/**
	 * token缓存
	 * @param args
	 */
	public static Map<String,Object> tokenMap = new ConcurrentHashMap<>();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
