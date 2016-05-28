package netty;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer
{
	public static void main(String[] ages)
	{
		new TimeServer().bind(8099);
	}
	
	public void bind(int port)
	{
		//配置nio线程组
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workGroup = new NioEventLoopGroup();
		
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
									 .option(ChannelOption.SO_BACKLOG, 1024)
									 .childHandler(new ChildChannelHandler());
		try
		{
			//绑定端口，同步等待成功
			ChannelFuture f = b.bind(port).sync();
			
			//等待服务端监听端口关闭
			f.channel().closeFuture().sync();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			//释放线程池资源
			workGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
		
	}
	
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>
	{

		@Override
		protected void initChannel(SocketChannel arg0) throws Exception
		{
			arg0.pipeline().addLast(new TimeServerHandler());
		}
	}
}

class TimeServerHandler extends ChannelHandlerAdapter
{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
	{
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		try
		{
			String body = new String(req,"UTF-8");
			System.out.println(String.format("the time server receive order : %s", body));
			
			String currentTime = "query time order".equalsIgnoreCase(body) ?
					new Date(System.currentTimeMillis()).toString():
					"Bad order";
			ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
			ctx.write(resp);
		}
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)
	{
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e)
	{
		ctx.close();
		e.printStackTrace();
	}
}






















