package netty;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TimeClient
{

	public static void main(String[] args)
	{
		try
		{
			new TimeClient().connect("localhost", 8099);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public void connect(String host, int port) throws InterruptedException
	{
		//配置客户端NIO线程组
		NioEventLoopGroup group = new NioEventLoopGroup();
		try
		{
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
						  .option(ChannelOption.TCP_NODELAY, true)
						  .handler(new ChannelInitializer<SocketChannel>()
						{

							@Override
							protected void initChannel(SocketChannel arg0) throws Exception
							{
								arg0.pipeline().addLast(new TimeClientHandler());
							}
						});
			ChannelFuture f = b.connect(host, port).sync();//发起异步请求
			
			f.channel().closeFuture().sync();//等待链路关闭
		}
		finally
		{
			group.shutdownGracefully();
		}
	}
}

class TimeClientHandler extends ChannelHandlerAdapter
{
	private static final Logger loggger = Logger.getLogger(ChannelHandlerAdapter.class.getName());
	
	private final ByteBuf firstMessage;
	
	public TimeClientHandler()
	{
		byte[] req = "query time order".getBytes();
		this.firstMessage = Unpooled.buffer(req.length);
		this.firstMessage.writeBytes(req);
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx)
	{
		ctx.writeAndFlush(firstMessage);
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException
	{
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"UTF-8");
		System.out.println("Now is :" + body);
	}
	
	@Override 
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e)
	{
		//释放资源
		loggger.warning("释放资源" + e.getMessage());
		ctx.close();
	}
}








