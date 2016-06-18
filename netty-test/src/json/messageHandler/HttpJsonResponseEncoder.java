package json.messageHandler;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import json.httpjson.HttpJsonResponse;

/**
 * http +json 响应消息的编码（序列化）
 * @author HY
 *
 */
public class HttpJsonResponseEncoder extends AbstractHttpJsonEncoder<HttpJsonResponse>
{

	@Override
	protected void encode(ChannelHandlerContext ctx, HttpJsonResponse httpJsonResponse, List<Object> list) throws Exception
	{
		ByteBuf buf = encode0(ctx, httpJsonResponse.getResult());
		FullHttpResponse response = httpJsonResponse.getFullHttpResponse();
		
		if(response == null)
		{
			response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
		}
		else
		{
			response = new DefaultFullHttpResponse(httpJsonResponse.getFullHttpResponse().getProtocolVersion(),
					httpJsonResponse.getFullHttpResponse().getStatus(),buf);
		}
		response.headers().set(HttpHeaders.Names.CONTENT_TYPE,"text/json");
		HttpHeaders.setContentLength(response, buf.readableBytes());
		list.add(response);
	}

}
