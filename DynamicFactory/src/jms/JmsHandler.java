package jms;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class JmsHandler<T> implements Runnable
{
	private Map<String,T> map = new HashMap<>();
	private String ip;
	private int port;
	
	public JmsHandler(String name ,T t)
	{
		map.put(name, t);
		init(name);
	}

	public JmsHandler(String name)
	{
		init(name);
	}
	
	@Override
	public void run()
	{
		try
		{
			System.out.println("发送请求");
			Socket socket = new Socket(ip, port);
			socket.setSoTimeout(1000);
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(map);
			out.flush();
			socket.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void init(String name)
	{
		InputStream inputStream = null;
		try
		{
			inputStream = this.getClass().getResourceAsStream("jmsConfig.properties");
			Properties properties = new Properties();
			properties.load(inputStream);
			this.ip = properties.getProperty(name+"_ip");
			String port = properties.getProperty(name+"_port");
			this.port = Integer.valueOf(port);
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
}
