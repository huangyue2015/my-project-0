package coreManage;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import util.MessageMap;

public class QueueManage
{
	private static Map<String,Queue<?>> queMaps = new HashMap<>();
	private static QueueManage queueManage;
	private static List<Map<String,List<MessageMap>>> mapLists;
	
	public Queue<?> getQueue(String name)
	{
		if(queMaps.get(name) == null)
			throw new RuntimeException("未注册的队列");
		else
			return queMaps.get(name);
	}
	
	public void setQueue(String[] names)
	{
		for(String s : names)
		{
			queMaps.put(s, new LinkedList<>());
		}
	}
	
	public void initQueue()
	{
		String[] names = parserXml(this.getClass().getClassLoader().getResource("").getPath()+"messageMap.xml");
		setQueue(names);
	}
	
	private QueueManage()
	{
		
	}
	
	public String[] parserXml(String fileName)
	{
		List<String> names = new ArrayList<>();
		List<Map<String,List<MessageMap>>> mapLists = new ArrayList<>();
		File inputXml = new File(fileName);
		SAXReader saxReader = new SAXReader();
		try
		{
			Document document = saxReader.read(inputXml);
			Element employees = document.getRootElement();
			for (Iterator i = employees.elementIterator(); i.hasNext();)
			{
				
				Element employee = (Element) i.next();
				String name = employee.attributeValue("name");
				names.add(name);
				List<MessageMap> mapList = new ArrayList<>();
				Map<String,List<MessageMap>> updateMap = new HashMap<>();
				for(Iterator j = employee.elementIterator(); j.hasNext();)
				{
					Element employee2 = (Element) j.next();
					MessageMap messageMap = new MessageMap(employee2.attributeValue("name"),employee2.attributeValue("classPath"));
					mapList.add(messageMap);	
				}
				updateMap.put(name, mapList);
				mapLists.add(updateMap);
			}
			this.mapLists = mapLists;
		}
		catch (DocumentException e)
		{
			System.out.println(e.getMessage());
		}
		return names.toArray(new String[names.size()]);
	}
	
	public static void main(String[] arges)
	{
		new QueueManage();
	}
	
	public static class QueueHamdler
	{
		static Queue<Object> que;
		private String name;
		public QueueHamdler(){}
		public QueueHamdler(String name)
		{
			getQueueManage().initQueue();
			this.name = name;
			this.que = (Queue<Object>) new QueueManage().getQueue(name);
		}
		
		public QueueManage getQueueManage()
		{
			if(queueManage != null)
				return queueManage;
			else
				return new QueueManage();
		}
		
		public void addQueue(Object e)
		{
			synchronized (que)
			{
				this.que.offer(e);
				new Thread(del.getInstance(name)).start();
				System.out.println(new Thread(del.getInstance(name)).isAlive());
			}
		}
		
		public Object pollQueue()
		{
			synchronized (que)
			{
				return this.que.poll();
			}
		}
	}
	
	static class del implements Runnable
	{
		private static String serversName;
		
		private del()
		{
			
		}
		private static del d = new del();
		
		public static del getInstance(String _serversName)
		{
			serversName = _serversName;
			if(d == null)
				return new del();
			return d;
		}
		@Override
		public void run()
		{
			try
			{
				while(true)
				{
					if(QueueHamdler.que.size() == 0)
						break;
					Object o = new QueueHamdler().pollQueue();
					if(o != null)
						updateAll(o);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		public void updateAll(Object o)
		{
			for(Map<String,List<MessageMap>> map : mapLists)
			{
				if(map.get(this.serversName)!=null)
				{
					List<MessageMap> list = map.get(this.serversName);
					for(MessageMap m : list)
					{
						try
						{
							Class c = Class.forName(m.getClassPath());
							Method method = c.getDeclaredMethod(m.getName(), new Class[]{o.getClass()});
							method.setAccessible(true);
							method.invoke(c.newInstance(),o);
						}
						catch (Exception e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		}
		
	}
}
