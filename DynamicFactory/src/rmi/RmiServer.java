package rmi;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.util.Properties;

public class RmiServer<T extends Remote>
{
	private T t;
	private String serverName;
	private int port;
	private String url;
	
	public RmiServer(String serverName)
	{
		this.serverName = serverName;
		getURL();
	}

	public RmiServer(T t,String serverName)
	{
		this.t = t;
		this.serverName = serverName;
		getURL();
	}
	
	
	public  void startServer()
	{
		try
		{
			LocateRegistry.createRegistry(port);  
	        Naming.rebind(url,t); 
			System.out.println("Hello Server is ready."); 
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public T startLookup()
	{
		try
		{
			return (T)Naming.lookup(url);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	
	private void getURL()
	{
		InputStream inputStream = null;
		try
		{
			inputStream = this.getClass().getResourceAsStream("rmiConfig.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			String ip = properties.getProperty(this.serverName+"_ip");
			String port = properties.getProperty(this.serverName+"_port");
			this.port = Integer.valueOf(port);
			this.url = String.format("//%s:%s/%s", ip,port,this.serverName);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(inputStream!=null)
				try
				{
					inputStream.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
		}
	}
	
	public static boolean isLocal(String interfacename)
	{
		InputStream inputStream = null;
		boolean flag = true;
		try
		{
			inputStream = RmiServer.class.getResourceAsStream("rmiConfig.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			String value = properties.getProperty(interfacename+"_"+ getLocalProgectName());
			System.out.println(getLocalProgectName());
			flag = Boolean.valueOf(value);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return true;
		}
		finally
		{
			if(inputStream!=null)
				try
				{
					inputStream.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
		}
		return flag;
	}
	
	private static String getLocalProgectName()
	{
		String a = RmiServer.class.getClassLoader().getResource("").getPath();
		String[] as = a.split("/");
		return as[as.length-2];
	}
}
