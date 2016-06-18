package json.messageHandler;

import java.io.IOException;
import java.nio.charset.Charset;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public abstract class AbstractHttpJsonDecoder<T> extends MessageToMessageDecoder<T>
{
	private ObjectMapper objectMapper;
	private Class<?> clazz;
	private boolean isPrint;
	private static final String CHARSET_NAME = "utf-8";
	private static final Charset UTF_8 = Charset.forName(CHARSET_NAME);
	
	
	
	protected AbstractHttpJsonDecoder(Class<?> clazz)
	{
		this(clazz, false);
	}

	protected AbstractHttpJsonDecoder(Class<?> clazz, boolean isPrint)
	{
		this.clazz = clazz;
		this.isPrint = isPrint;
	}
	
	protected Object decode0(ChannelHandlerContext ctx, ByteBuf body) throws JsonParseException, JsonMappingException, IOException
	{
		objectMapper = new ObjectMapper();
		String content = body.toString(UTF_8);
		if(isPrint)
			System.out.println("the body is :" + content);
		Object result = objectMapper.readValue(content, this.clazz);
		return  result;
	}
	
}











