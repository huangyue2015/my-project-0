package json;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import json.httpjson.HttpJsonRequest;
import json.httpjson.HttpJsonResponse;
import json.messageHandler.HttpJsonRequestEncoder;
import json.messageHandler.HttpJsonResponseDecoder;
import json.pojo.Order;
import json.pojo.OrderFactory;

public class HttpJsonClient
{
	public static void main(String[] args)
	{
		try
		{
			for(int i = 0; i <  1000; i++)
			{
				new Thread(new Runnable()
				{
					
					@Override
					public void run()
					{
						try
						{
							new HttpJsonClient().run(8080);
						}
						catch (InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}).start();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void run(final int port) throws InterruptedException
	{
		EventLoopGroup group = new NioEventLoopGroup();
		try
		{
			Bootstrap b= new Bootstrap();
			b.group(group)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>()
				{
					@Override
					protected void initChannel(SocketChannel ch) throws Exception
					{
						//添加请求解码器
						ch.pipeline().addLast("http-decoder", new HttpResponseDecoder());
						
						//接收到的http请求片段信息整合（Aggregator）到一起，最终得到一个FullHttpRequest。
						ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
						
						//添加json请求解码器
						ch.pipeline().addLast("json-decoder", new HttpJsonResponseDecoder(Order.class, true));
						
						//添加响应编码器
						ch.pipeline().addLast("http-encoder", new HttpRequestEncoder());
						
						//添加json响应编码器
						ch.pipeline().addLast("json-encoder",new HttpJsonRequestEncoder());
						
						//添加消息处理类
						ch.pipeline().addLast("jsonClientHandler",new HttpJsonClientHandler());
					}
				});
			//发起异步连接操作
			ChannelFuture future = b.connect(new InetSocketAddress("localhost", port)).sync();
			
			//等待客户端链路关闭
			future.channel().closeFuture().sync();
		}
		finally
		{
			group.shutdownGracefully();
		}
	}
	
	static class HttpJsonClientHandler extends SimpleChannelInboundHandler<HttpJsonResponse>
	{

		@Override
		public void channelActive(ChannelHandlerContext ctx)
		{
			
			HttpJsonRequest request = new HttpJsonRequest(null, OrderFactory.create(123));
			ctx.writeAndFlush(request);
		}
		
		@Override
		protected void messageReceived(ChannelHandlerContext ctx, HttpJsonResponse response) throws Exception
		{
			System.out.println(String.format("the client receive response of http header is : %s", 
					response.getFullHttpResponse().headers().names()));
			System.out.println(String.format("the client receive response of http body is : %s", 
					response.getResult()));
		}
		
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
		{
			cause.printStackTrace();
			ctx.close();
		}
	}
	
}
