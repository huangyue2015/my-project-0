package json.messageHandler;

import java.net.InetAddress;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;
import json.httpjson.HttpJsonRequest;
/**
 * http + json  请求消息编码（序列化）
 */
public class HttpJsonRequestEncoder extends AbstractHttpJsonEncoder<HttpJsonRequest>
{

	@Override
	protected void encode(ChannelHandlerContext ctx, HttpJsonRequest request, List<Object> out) throws Exception
	{
		ByteBuf  buf = encode0(ctx, request.getBody());
		FullHttpRequest fullHttpRequest = request.getRequest();
		
		if(fullHttpRequest == null)
		{
			fullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, "/.do",buf);
			HttpHeaders headers = fullHttpRequest.headers();
			headers.set(HttpHeaders.Names.HOST, InetAddress.getLocalHost().getHostAddress());
			headers.set(HttpHeaders.Names.CONNECTION,HttpHeaders.Values.CLOSE);
			headers.set(HttpHeaders.Names.ACCEPT_ENCODING,
					HttpHeaders.Values.GZIP.toString()+","+
					HttpHeaders.Values.DEFLATE.toString());
			headers.set(HttpHeaders.Names.ACCEPT_CHARSET,"ISO-8859-1,utf-8;q=0.7,*;q=0.7");
			headers.set(HttpHeaders.Names.ACCEPT_LANGUAGE,"zh");
			headers.set(HttpHeaders.Names.USER_AGENT, "netty json Http Client side");
			headers.set(HttpHeaders.Names.ACCEPT, 
					"text/html,application/xhtml+xml,application/xml;q=0.9,*/* ; q=0.8");
			HttpHeaders.setContentLength(fullHttpRequest, buf.readableBytes());
			out.add(fullHttpRequest);
		}
	}
}






