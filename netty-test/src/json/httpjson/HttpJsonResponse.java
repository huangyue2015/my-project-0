package json.httpjson;

import io.netty.handler.codec.http.FullHttpResponse;

public class HttpJsonResponse
{
	private FullHttpResponse fullHttpResponse;
	private Object result;
	public final FullHttpResponse getFullHttpResponse()
	{
		return fullHttpResponse;
	}
	public final void setFullHttpResponse(FullHttpResponse fullHttpResponse)
	{
		this.fullHttpResponse = fullHttpResponse;
	}
	public final Object getResult()
	{
		return result;
	}
	public final void setResult(Object result)
	{
		this.result = result;
	}
	
	public HttpJsonResponse(FullHttpResponse fullHttpResponse, Object result)
	{
		this.fullHttpResponse = fullHttpResponse;
		this.result = result;
	}
	
}
