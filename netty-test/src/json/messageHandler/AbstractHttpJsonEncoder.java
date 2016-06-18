package json.messageHandler;

import java.nio.charset.Charset;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;


public abstract class AbstractHttpJsonEncoder<T> extends MessageToMessageEncoder<T>
{
	private ObjectMapper objectMapper;
	
	private static final String CHARSET_NAME = "utf-8";
	private static final Charset UTF_8 = Charset.forName(CHARSET_NAME);
	
	/**
	 * 将java对象序列化成json
	 * @param ctx
	 * @param body
	 * @return
	 * @throws JsonProcessingException 
	 */
	protected ByteBuf encode0(ChannelHandlerContext ctx, Object body) throws JsonProcessingException
	{
		objectMapper = new ObjectMapper();
		String jsonStr = objectMapper.writeValueAsString(body);
		ByteBuf buf = Unpooled.copiedBuffer(jsonStr, UTF_8);
		return buf;
	}
}
