package com.common.system;

import javax.servlet.ServletContextEvent;

/**
 * 用来做系统初始化
 * @author Administrator
 *
 */
public abstract class AbstractInitSystem {
	public void init()
	{
		init(null);
	}
	
	public void init(ServletContextEvent servletContextEvent)
	{
		
	}
}
