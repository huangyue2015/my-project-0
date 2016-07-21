package com.common.system;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;

/**
 * 用来做系统初始化
 * @author Administrator
 *
 */
public abstract class AbstractInitSystem {
	
	protected static Logger logger = Logger.getLogger(AbstractInitSystem.class);
	
	public void init()
	{
		init(null);
	}
	
	public void init(ServletContextEvent servletContextEvent)
	{
		
	}
}
