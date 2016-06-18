package json.messageHandler;

import java.util.List;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import json.httpjson.HttpJsonRequest;

/**
 * http + json 请求消息解码类(反序列化)
 * @author HY
 *
 */
public class HttpJsonRequestDecoder extends AbstractHttpJsonDecoder<FullHttpRequest>
{

	public HttpJsonRequestDecoder(Class<?> clazz)
	{
		this(clazz, false);
	}

	protected HttpJsonRequestDecoder(Class<?> clazz, boolean isPrint)
	{
		super(clazz, isPrint);
	}
	
	
	@Override
	protected void decode(ChannelHandlerContext ctx, FullHttpRequest request, List<Object> list) throws Exception
	{
		if(!request.getDecoderResult().isSuccess())
		{
			sendError(ctx, HttpResponseStatus.BAD_REQUEST);
			return;
		}
		
		HttpJsonRequest request2 = new HttpJsonRequest(request, decode0(ctx, request.content()));
		list.add(request2);
	}

	public static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status)
	{
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status,
				Unpooled.copiedBuffer("failure :" + status.toString() + "\r\n", CharsetUtil.UTF_8));
		response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/plain; charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
}
