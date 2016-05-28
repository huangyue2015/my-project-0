package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class TimeServer
{

	public static void main(String[] args)
	{
		MultiplexerTimeServer m = new MultiplexerTimeServer(8099);
		new Thread(m,"Nio-MultiplexerTimeServer-001").start();
	}

}

/**
 * 多路复用类，主要负责轮询多路复用器Selector,
 * 可以处理多个客户端的并发接入
 * @author HY
 *
 */
class MultiplexerTimeServer implements Runnable
{

	private Selector selector;
	private ServerSocketChannel serverSocketChannel;
	private volatile boolean stop;
	
	public MultiplexerTimeServer(int port)
	{
		try
		{
			this.selector = Selector.open();
			this.serverSocketChannel = ServerSocketChannel.open();
			this.serverSocketChannel.configureBlocking(false);
			this.serverSocketChannel.socket().bind(new InetSocketAddress(port),1024);
			this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("the time server is start in port :" + port);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void stop()
	{
		this.stop = true;
	}
	
	@Override
	public void run()
	{
		while (!stop)
		{
			try
			{
				selector.select(1000);
				Set<SelectionKey> selectKeys = selector.selectedKeys();
				SelectionKey key = null;
				for(Iterator<SelectionKey> it = selectKeys.iterator(); it.hasNext();)
				{
					key = it.next();
					it.remove();
					try
					{
						handleInput(key);
					}
					catch (Exception e) 
					{
						if(key != null)
						{
							key.cancel();
							if(key.channel() != null)
								key.channel().close();
						}
					}
				}
			}
			catch (Throwable e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @throws IOException 
	 */
	private void handleInput(SelectionKey key) throws IOException
	{
		if(key.isValid())
		{
			//处理接入的请求消息
			
			if(key.isAcceptable())
			{
				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
				SocketChannel sc = ssc.accept();
				sc.configureBlocking(false);//设置发送请求信息为非阻塞模式
				
				//add the new connection to the selector
				sc.register(selector, SelectionKey.OP_READ);
			}
			
			if(key.isReadable())
			{
				SocketChannel sc = (SocketChannel) key.channel();
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				
				if(readBytes > 0)
				{
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes, "UTF-8");
					System.out.println("the time server receive order:"+ body);
					String currentTime = "QUERY TIME ORDER"
							.equalsIgnoreCase(body) ? 
							new Date(System.currentTimeMillis()).toString() :
							"BAD ORDER";
					doWrite(sc, currentTime);
				}
				else if(readBytes < 0)
				{
					//对链路关闭
					key.cancel();
					sc.close();
				}
				else
				{
					;//读到0字节，忽略
				}
			}
		}
	}
	
	private void doWrite(SocketChannel sc, String response) throws IOException
	{
		if(response != null && response.trim().length() > 0)
		{
			byte[] bytes = response.getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			sc.write(writeBuffer);
		}
	}
}




















