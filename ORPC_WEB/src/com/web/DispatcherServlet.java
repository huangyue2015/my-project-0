package com.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet(name="DispatcherServlet",urlPatterns="*.do")
public class DispatcherServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(DispatcherServlet.class);

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp)
	{
		try
		{
			String method = req.getRequestURI();
			
			log.info("拦截到的方法名:" + method);
			
			com.web.WebServlet webServlet = com.web.WebServlet.getInstance();
			Map<String, Correspond<String,String>> map =webServlet.getServiceMap();
			Set<String> keys = map.keySet();
			for(String key : keys)
			{
				if(method.indexOf(req.getContextPath()+key)!= -1)
				{
					Correspond<String,String> correspond = map.get(key);
					Object o = methodInvok(correspond.getName(), correspond.getValue(),req);
					try
					{
						PrintWriter out = resp.getWriter();
						out.print("张三OK");
						out.flush();
						out.close();
						
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
					break;
				}
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private Object methodInvok(String methdname,String classPath,HttpServletRequest req)
	{
		Object o = new Object();
		try
		{
			Class<? extends Object> c = Class.forName(classPath);
			Method method = c.getMethod(methdname,HttpServletRequest.class);
			o =  method.invoke(c.newInstance(),req);
		}
		catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException e)
		{
			e.printStackTrace();
		}
		catch (IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (InvocationTargetException e)
		{
			e.printStackTrace();
		}
		return o;
	}
}
