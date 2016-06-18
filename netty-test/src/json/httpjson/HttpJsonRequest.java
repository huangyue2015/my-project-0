package json.httpjson;

import io.netty.handler.codec.http.FullHttpRequest;

/**
 * Http + json请求消息包
 * @author HY
 *
 */
public class HttpJsonRequest
{
	private FullHttpRequest request;
	private Object body;
	
	public HttpJsonRequest(FullHttpRequest request, Object body)
	{
		this.request = request;
		this.body = body;
	}

	public final FullHttpRequest getRequest()
	{
		return request;
	}

	public final void setRequest(FullHttpRequest request)
	{
		this.request = request;
	}

	public final Object getBody()
	{
		return body;
	}

	public final void setBody(Object body)
	{
		this.body = body;
	}
}
