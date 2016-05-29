package netty.adhereSplitPackage;

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
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer
{
	public void bind(int port) throws InterruptedException
	{
		//配置服务端的nio线程组
		NioEventLoopGroup boosGroup = new NioEventLoopGroup();
		NioEventLoopGroup workGroup = new NioEventLoopGroup();
		
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap.group(boosGroup, workGroup)
					   .channel(NioServerSocketChannel.class)
					   .option(ChannelOption.SO_BACKLOG, 100)
					   .handler(new LoggingHandler(LogLevel.INFO))
					   .childHandler(new ChannelInitializer<SocketChannel>()
					{

						@Override
						protected void initChannel(SocketChannel ch) throws Exception
						{
//							ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
//							ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
							ch.pipeline().addLast(new StringDecoder());
							ch.pipeline().addLast(new EchoServerHandler());
						}
					});
		//绑定端口等待客户端请求
		ChannelFuture f = serverBootstrap.bind(port).sync();
		
		//监听客户端是否关闭
		f.channel().closeFuture().sync();
		
		boosGroup.shutdownGracefully();
		workGroup.shutdownGracefully();
	}
	
	static class EchoServerHandler extends ChannelHandlerAdapter
	{
		int count = 0;
		
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
		{
			String body = (String)msg;
			System.out.println("客户端发送内容："+ body + ";总共发送次数："+ ++count);
			
			body += "$_";
			ByteBuf writeEcho = Unpooled.copiedBuffer(body.getBytes());
			
			ctx.writeAndFlush(writeEcho);
		}
	}
	
	public static void main(String[] args)
	{
		try
		{
			new EchoServer().bind(8099);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
