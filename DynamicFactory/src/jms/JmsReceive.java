package jms;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;


public class JmsReceive implements Runnable
{

	private String ip;
	private int port;
	IMessageDeal messageDeal;
	
	public JmsReceive(String name,IMessageDeal messageDeal)
	{
		init(name);
		this.messageDeal = messageDeal;
	}
	
	@Override
	public void run()
	{
		acceaaMessage(messageDeal);
	}

	
	public  void acceaaMessage(IMessageDeal messageDeal)
	{
		try
		{
			System.out.println("开始接收请求。。。");
			ServerSocket serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress(ip, port));
			ObjectInputStream inputStream = null;
			while(true)
			{
				Socket socket = serverSocket.accept();
				inputStream = new ObjectInputStream(socket.getInputStream());
				Object obj = inputStream.readObject();
				new Thread(new DealMessage(obj)).start();
			}
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
	
	class DealMessage implements Runnable
	{
		Object o;
		
		
		public DealMessage(Object o)
		{
			this.o = o;
		}
		
		@Override
		public void run()
		{
			messageDeal.addMessage(o);
		}
		
	}
}
