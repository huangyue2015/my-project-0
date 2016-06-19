package json;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import json.httpjson.HttpJsonRequest;
import json.httpjson.HttpJsonResponse;
import json.messageHandler.HttpJsonRequestDecoder;
import json.messageHandler.HttpJsonResponseEncoder;
import json.pojo.Address;
import json.pojo.Order;

public class HttpJsonServer
{

	public void run(final int port) throws InterruptedException
	{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try
		{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>()
				{

					@Override
					protected void initChannel(SocketChannel ch) throws Exception
					{
						//添加请求解码器
						ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
						
						//接收到的http请求片段信息整合（Aggregator）到一起，最终得到一个FullHttpRequest。
						ch.pipeline().addLast("http-aggregator",new HttpObjectAggregator(65536));
						
						//添加json请求解码器
						ch.pipeline().addLast("json-decoder", new HttpJsonRequestDecoder(Order.class,true));
						
						//添加响应编码器
						ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
						
						//添加json响应编码器
						ch.pipeline().addLast("json-encoder",new HttpJsonResponseEncoder());
						
						//添加消息处理类
						ch.pipeline().addLast("jsonServerHandler",new HttpJsonServerHandler());
					}
				});
			ChannelFuture future = b.bind(new InetSocketAddress(port)).sync();
			System.out.println("http订购服务器已经启动，网址是："+ "http://localhost:"+port);
			future.channel().closeFuture().sync();
		}
		finally
		{
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
		
	}
	
	static class HttpJsonServerHandler extends SimpleChannelInboundHandler<HttpJsonRequest>
	{

		@Override
		protected void messageReceived(ChannelHandlerContext ctx, HttpJsonRequest httpJsonRequest) throws Exception
		{
			HttpRequest request = httpJsonRequest.getRequest();
			Order order = (Order) httpJsonRequest.getBody();
			System.out.println("http server receive request :" + order);
			
			dobusiness(order);//处理业务
			
			ChannelFuture future = ctx.writeAndFlush(new HttpJsonResponse(null, order));
			
			if(!HttpHeaders.isKeepAlive(request))
			{
				future.addListener(new GenericFutureListener<Future<? super Void>>()
				{

					@Override
					public void operationComplete(Future<? super Void> future) throws Exception
					{
						ctx.close();
					}
				});
			}
		}
		
		private void dobusiness(Order order)
		{
			order.getCustomer().setFirstName("狄");
			order.getCustomer().setLaseName("仁杰");
			List<String> midNames = new ArrayList<String>();
			midNames.add("李元芳");
			order.getCustomer().setMiddleNames(midNames);
			Address address = order.getBillTo();
			address.setCity("洛阳");
			address.setCountry("大唐");
			address.setState("河南道");
			address.setPostCode("123456");
			order.setBillTo(address);
			order.setShipTo(address);
		}
		
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
		{
			if(ctx.channel().isActive())
				sendError(ctx, HttpResponseStatus.INTERNAL_SERVER_ERROR);
		}
		
		private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status)
		{
			ByteBuf buf = Unpooled.copiedBuffer("失败："+ status.toString()+"\r\n",CharsetUtil.UTF_8);
			FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, buf);
			response.headers().set(HttpHeaders.Names.CONTENT_TYPE,"text/plain; charset=utf-8");
			ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
		}
	}
	
	
	public static void main(String[] args)
	{
		try
		{
			new HttpJsonServer().run(8080);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}

}
