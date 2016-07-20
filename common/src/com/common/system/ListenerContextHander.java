package com.common.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;


public class ListenerContextHander implements ServletContextListener
{

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent)
	{
		System.out.println("web服务已关闭");
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		System.out.println("web服务开始初始化"+ servletContextEvent.getServletContext().getContextPath());
		/**
		 * 初始化log4j
		 */
		initLog4j(servletContextEvent);
		
		/**
		 * 初始化系统加载类
		 */
		List<String> list = loadInitConf(servletContextEvent);
		if(list != null)
		{
			for(String s : list)
			{
				try {
					Class<?> c = Class.forName(s);
					AbstractInitSystem initSystem = (AbstractInitSystem) c.newInstance();
					initSystem.init();
					initSystem.init(servletContextEvent);
				} catch (Exception e) {
					break;
				}
			}
		}
	}
	
	/**
	 * 初始化log4j日志设置
	 * @param servletContextEvent
	 */
	private void initLog4j(ServletContextEvent servletContextEvent)
	{
		System.out.println("com.common.system.ListenerContextHander.initLog4j : 正在初始化 log4j日志设置信息....");
		String path = servletContextEvent.getServletContext().getRealPath("");
		Date dt=new Date();
	    SimpleDateFormat matter1=new SimpleDateFormat("yyyy-MM-dd");
	    String log_dir = String.format("%s/WEB-INF/logs/%s", path, matter1.format(dt));
		System.setProperty("log.dir", log_dir);
		System.out.println("log日志存放地址:"+System.getProperty("log.dir"));
		PropertyConfigurator.configure(path +"\\log4j.properties");
	}
	
	private List<String> loadInitConf(ServletContextEvent servletContextEvent)
	{
		List<String> list = null;
		String path = servletContextEvent.getServletContext().getRealPath("")+"\\init.conf";
		File file = new File(path);
		Scanner  sc  = null;
		FileInputStream in = null;
		if(file.exists())
		{
			try {
				in = new FileInputStream(file);
				sc = new Scanner(in);
				list = new ArrayList<>();
				while(sc.hasNextLine())
				{
					String s = sc.nextLine();
					list.add(s);
				}
				if(list.size() > 0)
					return list;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
			finally
			{
				if(in != null)
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
				if(sc != null)
					sc.close();
			}
		}
		return null;
	}
}
