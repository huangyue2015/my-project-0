package netty.adhereSplitPackage;

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
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class EchoClient
{

	public void connect(String host, int port) throws InterruptedException
	{
		NioEventLoopGroup group = new NioEventLoopGroup();
		
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class)
					  .option(ChannelOption.TCP_NODELAY, true)
					  .handler(new ChannelInitializer<SocketChannel>()
					{

						@Override
						protected void initChannel(SocketChannel ch) throws Exception
						{
							ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
							
							ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
							ch.pipeline().addLast(new StringDecoder());
							ch.pipeline().addLast(new EchoClientHandler());
						}
					});
		//发起异步操作
		ChannelFuture f  = b.connect(host, port).sync();
		
		//等待服务端 关闭连接 
		f.channel().closeFuture().sync();
		
		group.shutdownGracefully();
	}
	
	static class EchoClientHandler extends ChannelHandlerAdapter
	{
		private int count;
		
		private final String ECHO_QRY = "Hello Netty.$_";
		
		@Override
		public void channelActive(ChannelHandlerContext ctx)
		{
			for(int i = 0; i < 10; i++)
				ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_QRY.getBytes()));
		}
		
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg)
		{
			System.out.println("服务端返回消息："+ msg + "总共返回次数："+ ++count);
		}
		
		@Override
		public void channelReadComplete(ChannelHandlerContext ctx)
		{
			ctx.flush();
		}
	}
	
	public static void main(String[] args)
	{
		try
		{
			new EchoClient().connect("localhost", 8099);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
