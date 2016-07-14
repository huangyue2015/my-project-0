package com.common.util;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

	static
	{
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				int ms = (24 - new Date().getHours()) * 60 * 60 * 1000 ;
				while (true) {
					try 
					{
						Thread.sleep(ms);
						mobileSendLockMap.clear();
						ms = 24 * 60 * 60 * 1000 ;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
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
