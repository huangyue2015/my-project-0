package com.util;

import java.util.Hashtable;
import java.util.Map;

public class WebServlet
{
	private static  Map<String, Correspond<String,String>> serviceMaps = new Hashtable<>();
	
	private static WebServlet webServlet = new WebServlet();
	
	private WebServlet()
	{
		
	}
	
	public static WebServlet getInstance()
	{
		if(webServlet != null)
			return webServlet;
		else
			return new WebServlet();
	}
	
	public Map<String, Correspond<String,String>> getServiceMap()
	{
		if(serviceMaps != null)
			return serviceMaps;
		else
			return new Hashtable<>();
	}
	
	public void setServiceMap(String urlPatten,String methodName,String classPath)
	{
		Correspond<String,String> service = new Correspond<String,String>(methodName, classPath);
		setServiceMap(urlPatten, service);
	}
	
	public void setServiceMap(String urlPatten , Correspond<String,String> service)
	{
		 serviceMaps.put(urlPatten, service);
	}
}
