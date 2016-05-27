/**
 * 
 */
/**
 * @author HY
 *
 */
package nio;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

public class Example
{

	public static void main(String[] args) throws IOException
	{
		//1,打开serverSocketChannel 监听客户端连接，所有客户端连接的父管道
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		
		//2,绑定监听端口，设置为非阻塞模式
		serverSocketChannel.bind(new InetSocketAddress(InetAddress.getByName("localhost"), 8099));
		serverSocketChannel.configureBlocking(false);
		
		//3,创建reactor线程，创建多路复用器，并启动线程
		Selector selector = Selector.open();
		/*启动多路reactor线程*/
		
		//4,将serverSocketChannel注册到reactoy的多路复用器上
		/*SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT, att);*/
		
		//5,多路复用器在run方法里轮询准备就绪的key值
		
		//6,多路复用器监听到有新的客户端接入，处理新的连接请求，完成TCP三次握手，建立物理连接
		
		//7,设置客户端连接为非阻塞模式
		
		//8,将接入的客户端连接注册到Reactor线程的多路复用器上去，监听读操作
		
		//9,异步读取客户端请求到缓冲区
		
		//10,对buffer进行解码，如果有半包消息指针reset，继续读取并将消息封装成Task，投递到业务线程池中。
		
		//11,将POJO对象encode成ByteBuffer,调用SocketChannel的异步write接口将消息发送到客户端
	}
}

















