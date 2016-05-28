package bio.threadPool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class TimeServer
{

	public static void main(String[] args) throws IOException
	{
		int port = 8099;
		ServerSocket serverSocket = null;
		try
		{
			serverSocket = new ServerSocket(port);
			System.out.println("the time Server start in port 8099");
			Socket socket = null;
			while (true)
			{
				socket = serverSocket.accept();
				new TimeServerHandlerExcutePool(50, 10000).excute(new TimeServerHandler(socket));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			if(serverSocket != null)
			{
				System.out.println("the time Server is close");
				serverSocket.close();
				serverSocket = null;
			}
		}

	}

}

class TimeServerHandler implements Runnable
{

	private Socket socket;
	
	public TimeServerHandler(Socket socket)
	{
		this.socket = socket;
	}
	@Override
	public void run()
	{
		BufferedReader in = null;
		PrintWriter out = null;
		
		try
		{
			InputStreamReader inputStreamReader = new InputStreamReader(this.socket.getInputStream());
			in = new BufferedReader(inputStreamReader);
			out = new PrintWriter(this.socket.getOutputStream());
			String currentTime = null;
			String body = null;
			while (true)
			{
				body = in.readLine();
				if(body == null)
					break;
				if(body.equalsIgnoreCase("QUERY TIME ORDER"))
				{
					currentTime = new Date(System.currentTimeMillis()).toString();
					System.out.println(currentTime);
					out.println(currentTime);
					out.flush();
				}
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if(in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
			
			if(out != null)
			{
				try
				{
					out.close();
				}
				catch (Exception e2)
				{
					e2.printStackTrace();
				}
			}	
		}
		finally 
		{
			try
			{
				this.socket.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
}




















