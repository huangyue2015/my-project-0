package aio;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.CountDownLatch;

public class AsyncTimeClientHandler implements Runnable,CompletionHandler<Void, AsyncTimeClientHandler>
{

	private String host;
	
	private int port;
	
	private AsynchronousSocketChannel asynchronousSocketChannel;
	
	private CountDownLatch countDownLatch;
	
	public AsyncTimeClientHandler(String host, int port) throws IOException
	{
		this.host = host;
		this.port = port;
		
		this.asynchronousSocketChannel = AsynchronousSocketChannel.open();
		
	}
	
	@Override
	public void run()
	{
		this.countDownLatch = new CountDownLatch(1);
		
		//连接服务端
		this.asynchronousSocketChannel.connect(new InetSocketAddress(this.host, this.port), this, this);
		try
		{
			this.countDownLatch.await();
			if(asynchronousSocketChannel != null)
				asynchronousSocketChannel.close();
		}
		catch (InterruptedException | IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void completed(Void result, AsyncTimeClientHandler attachment)
	{
		byte[] req = "qurey time order".getBytes();
		ByteBuffer writeByteBuffer = ByteBuffer.allocate(req.length);
		writeByteBuffer.put(req);
		writeByteBuffer.flip();
		this.asynchronousSocketChannel.write(writeByteBuffer, writeByteBuffer, new CompletionHandler<Integer, ByteBuffer>()
		{

			@Override
			public void completed(Integer result, ByteBuffer byteBuffer)
			{
				if(byteBuffer.hasRemaining())
					asynchronousSocketChannel.write(byteBuffer, byteBuffer, this);
				else
				{
					ByteBuffer readBuffer = ByteBuffer.allocate(1024);
					asynchronousSocketChannel.read(readBuffer, readBuffer, new CompletionHandler<Integer, ByteBuffer>()
					{

						@Override
						public void completed(Integer result, ByteBuffer byteBuffer)
						{
							byteBuffer.flip();
							byte[] bytes = new byte[byteBuffer.remaining()];
							byteBuffer.get(bytes);
							try
							{
								String body = new String(bytes, "UTF-8");
								System.out.println("Now time is :" + body);
								countDownLatch.countDown();
							}
							catch (UnsupportedEncodingException e)
							{
								e.printStackTrace();
							}
						}

						@Override
						public void failed(Throwable exc, ByteBuffer attachment)
						{
							try
							{
								asynchronousSocketChannel.close();
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
							countDownLatch.countDown();
						}
					});
				}
			}

			@Override
			public void failed(Throwable exc, ByteBuffer attachment)
			{
				try
				{
					asynchronousSocketChannel.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
				countDownLatch.countDown();
			}
		});
	}

	@Override
	public void failed(Throwable exc, AsyncTimeClientHandler attachment)
	{
		exc.printStackTrace();
		try
		{
			this.asynchronousSocketChannel.close();
			this.countDownLatch.countDown();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
