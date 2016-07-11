package com.web.Handler.listener;

import java.io.File;
import java.util.Iterator;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.util.WebServlet;


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
		System.out.println("web服务开始初始化");
		lodeWebServlet();
	}
	
	private void lodeWebServlet()
	{
		String path = this.getClass().getClassLoader().getResource("").getPath();
		path = path.substring(0, path.lastIndexOf("classes/"))+"web_servlet.xml";
		
		File inputXml = new File(path);
		SAXReader saxReader = new SAXReader();
		try
		{
			Document document = saxReader.read(inputXml);
			Element employees = document.getRootElement();
			for(Iterator<Element> i = employees.elementIterator();i.hasNext();)
			{
				Element employee = i.next();
				String name = employee.getName();
				if(name != null)
				{
					switch (name)
					{
						case "services":
							loadServices(employee);
							break;

						default:
							break;
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	private void loadServices(Element employee)
	{
		for(Iterator<Element> i = employee.elementIterator();i.hasNext();)
		{
			Element e = i.next();
			WebServlet.getInstance().setServiceMap(e.getText(), e.attributeValue("methodName"), e.attributeValue("classPath"));
		}
	}
	
}
