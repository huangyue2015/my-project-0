package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class TimeClient
{

	public static void main(String[] args)
	{
		for(int i = 0; i < 100; i++)
			new Thread(new TimeClientHandler("localhost", 8099)).start();
	}

}

class TimeClientHandler implements Runnable
{

	private String host;
	private int port;
	private Selector selector;
	private SocketChannel socketChannel;
	private boolean stop;
	
	public TimeClientHandler(String host, int port)
	{
		this.host = host;
		this.port = port;
		
		try
		{
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
	}
	
	private void doConnect() throws IOException
	{
		//如果连接成功。则注册到多路复用器上，发送消息，读应答
		if(socketChannel.connect(new InetSocketAddress(host, port)))
		{
			socketChannel.register(selector, SelectionKey.OP_READ);
			doWrite(socketChannel);
		}
		else
		{
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
	}
	
	private void doWrite(SocketChannel sc) throws IOException
	{
		byte[] request = "QUERY TIME ORDER".getBytes();
		ByteBuffer writeBuffer = ByteBuffer.allocate(request.length);
		writeBuffer.put(request);
		writeBuffer.flip();
		sc.write(writeBuffer);
		if(!writeBuffer.hasRemaining())
			System.out.println("send order server succeed");
	}
	
	/**
	 * @throws IOException 
	 */
	private void handleInput(SelectionKey key) throws IOException
	{
		if(key.isValid())
		{
			SocketChannel sc = (SocketChannel) key.channel();
			if(key.isConnectable())
			{
				if(sc.finishConnect())
				{
					sc.register(selector, SelectionKey.OP_READ);
					doWrite(sc);
				}
				else
					System.exit(1);
			}
			
			if(key.isReadable())
			{
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if(readBytes > 0)
				{
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					String body = new String(bytes, "UTF-8");
					System.out.println("Now is :"+ body);
					this.stop = true;
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
	
	@Override
	public void run()
	{
		try
		{
			doConnect();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		while (!stop)
		{
			try
			{
				selector.select(1000);
				Set<SelectionKey> selectorKeys = selector.selectedKeys();
				SelectionKey key = null;
				for(Iterator<SelectionKey> i = selectorKeys.iterator(); i.hasNext();)
				{
					key = i.next();
					i.remove();
					handleInput(key);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
}