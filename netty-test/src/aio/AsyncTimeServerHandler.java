package aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeServerHandler implements Runnable
{
	private int port;
	
	CountDownLatch latch;
	
	AsynchronousServerSocketChannel assc;
	
	public AsyncTimeServerHandler(int port)
	{
		this.port = port;
		try
		{
			assc = AsynchronousServerSocketChannel.open();
			assc.bind(new InetSocketAddress(port));
			System.out.println("the time server is start in port :" + port);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void run()
	{
		latch = new CountDownLatch(1);
	}

}
