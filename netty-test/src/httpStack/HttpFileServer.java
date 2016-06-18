package httpStack;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpMessage;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpObjectDecoder;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer
{

	public void run(int port, final String url)
	{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
									.childHandler(new ChildHandler(url));
		ChannelFuture future = b.bind("172.20.138.75", port);
		System.out.println("HTTP文件目录服务器启动，网址是 :172.20.138.75 " + "http://:"
			    + port + url);
		try
		{
			future.channel().closeFuture().sync();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
		
	}
	
	static class ChildHandler extends ChannelInitializer<SocketChannel>
	{

		private String url;
		
		public ChildHandler(String url)
		{
			this.url = url;
		}
		
		@Override
		protected void initChannel(SocketChannel socketChannel) throws Exception
		{
			socketChannel.pipeline().addLast("http-decoder", new HttpRequestDecoder());
			socketChannel.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
			socketChannel.pipeline().addLast("http-encoder", new HttpResponseEncoder());
			socketChannel.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
			socketChannel.pipeline().addLast("fileServerHandler", new HttpFileServerHandler());
		}
		
	}
	
	public static void main(String[] args)
	{
		new HttpFileServer().run(8080, "/src/httpStack/");
	}
}
