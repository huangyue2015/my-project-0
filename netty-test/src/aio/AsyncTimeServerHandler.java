package aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Date;
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
			assc.bind(new InetSocketAddress(this.port));
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
		doAccept();
		try
		{
			latch.await();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void doAccept()
	{
		assc.accept(this, new AcceptCompleHandler());
	}
}

class AcceptCompleHandler implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandler>
{

	@Override
	public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment)
	{
		attachment.assc.accept(attachment,this);
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		result.read(buffer,buffer, new ReadCompleHandler(result));
	}

	@Override
	public void failed(Throwable exc, AsyncTimeServerHandler attachment)
	{
		attachment.latch.countDown();
	}
}

class  ReadCompleHandler implements CompletionHandler<Integer, ByteBuffer>
{

	private AsynchronousSocketChannel asc;
	
	public ReadCompleHandler(AsynchronousSocketChannel asc)
	{
		if(this.asc == null)
			this.asc = asc;
	}
	
	@Override
	public void completed(Integer result, ByteBuffer byteBuffer)
	{
		byteBuffer.flip();
		byte[] body = new byte[byteBuffer.remaining()];
		byteBuffer.get(body);
		
		try
		{
			String req = new String(body,"UTF-8");
			System.out.println("the time server receive order :" + req);
			String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(req) ?
					new Date(System.currentTimeMillis()).toString():
					"Bad order";
			doWrite(currentTime);		
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}

	private void doWrite(String response)
	{
		if(response != null && response.trim().length() > 0)
		{
			byte[] bytes = response.getBytes();
			ByteBuffer writeByteBuffer = ByteBuffer.allocate(bytes.length);
			writeByteBuffer.put(bytes);
			writeByteBuffer.flip();
			asc.write(writeByteBuffer, writeByteBuffer, 
					new CompletionHandler<Integer, ByteBuffer>()
			{

				@Override
				public void completed(Integer result, ByteBuffer attachment)
				{
					//如果没有发送完成，继续发送
					if(attachment.hasRemaining())
						asc.write(attachment,attachment,this);
				}

				@Override
				public void failed(Throwable exc, ByteBuffer attachment)
				{
					try
					{
						if(asc != null)
							asc.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}
				
			});
		}
	}
	
	@Override
	public void failed(Throwable exc, ByteBuffer byteBuffer)
	{
		try
		{
			this.asc.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
















