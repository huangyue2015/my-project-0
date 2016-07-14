package com.common.util;

import java.util.List;
import java.util.UUID;

public class StringUtil {

	public static void main(String[] args) {
		System.out.println(getRandomNum4());
	}

	public static boolean isNullOrEmpty(String s)
	{
		if(s == null || "".equals(s.trim()))
			return true;
		return false;
	}
	
	public static boolean isNotNullOrEmpty(String s)
	{
		if(s == null || "".equals(s.trim()))
			return false;
		return true;
	}
	
	public static String getUUID()
	{
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
	
	/**
	 * 生成4位随机数(纯数字)
	 */
	public static String getRandomNum4()
	{
		return (int)(Math.random()*9000+1000)+"";
	}
	
	/**
	 * 将List转化为逗号分隔的字符串
	 */
	public static String getStringByList(List<String> list)
	{
		StringBuilder stringBuilder = new StringBuilder();
		boolean flag = true;
		for(String s : list)
		{
			if(flag)
			{
				if(StringUtil.isNotNullOrEmpty(s))
				{
					stringBuilder.append(s);
				}
				flag = false;
			}
			else
			{
				if(StringUtil.isNotNullOrEmpty(s))
				{
					stringBuilder.append(","+s);
				}
			}
		}
		return stringBuilder.toString();
	}
	
}
