package json.messageHandler;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import json.httpjson.HttpJsonResponse;

/**
 * 响应消息的解码（反序列化）
 * @author HY
 *
 */
public class HttpJsonResponseDecoder extends AbstractHttpJsonDecoder<DefaultFullHttpResponse>
{

	public HttpJsonResponseDecoder(Class<?> clazz)
	{
		this(clazz, false);
	}

	public HttpJsonResponseDecoder(Class<?> clazz, boolean isPrint)
	{
		super(clazz,isPrint);
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, DefaultFullHttpResponse msg, List<Object> out) throws Exception
	{
		HttpJsonResponse response = new HttpJsonResponse(msg, decode0(ctx, msg.content()));
		out.add(response);
	}

}
