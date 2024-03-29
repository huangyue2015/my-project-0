package httpStack;

import java.io.File;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.regex.Pattern;
import javax.activation.MimetypesFileTypeMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelProgressiveFuture;
import io.netty.channel.ChannelProgressiveFutureListener;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.stream.ChunkedFile;
import io.netty.util.CharsetUtil;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;

public class HttpFileServerHandler extends SimpleChannelInboundHandler<FullHttpRequest>
{
	private static final HttpMethod GET = HttpMethod.GET;
	
	private static final HttpResponseStatus BAD_REQUEST = HttpResponseStatus.BAD_REQUEST;
	private static final HttpResponseStatus METHOD_NOT_ALLOWED = HttpResponseStatus.METHOD_NOT_ALLOWED;
	private static final HttpResponseStatus FORBIDDEN = HttpResponseStatus.FORBIDDEN;
	private static final HttpResponseStatus NOT_FOUND = HttpResponseStatus.NOT_FOUND;
	private static final HttpResponseStatus OK = HttpResponseStatus.OK;
	private static final HttpResponseStatus FOUND = HttpResponseStatus.FOUND;
	
	private static final HttpVersion HTTP_1_1 =  HttpVersion.HTTP_1_1;
	
	private static final Charset UTF_8 = CharsetUtil.UTF_8;

	private static final CharSequence CONTENT_TYPE = HttpHeaders.Names.CONTENT_TYPE;
	private static final CharSequence LOCATION = HttpHeaders.Names.LOCATION;
	
	private static final Pattern ALLOWED_FILE_NAME = Pattern.compile("[A-Za-z0-9][-_A-Za-z0-9\\.]*");
	private static final Pattern INSECURE_URI = Pattern.compile(".*[<>&\"].*");

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception
	{
		if(!request.getDecoderResult().isSuccess())
		{
			sendError(ctx, BAD_REQUEST);
			return;
		}
		
		if(request.getMethod() != GET)
		{
			sendError(ctx, METHOD_NOT_ALLOWED);
			return;
		}
		
		final String uri = request.getUri();
		final String path = sanitizeUri(uri);
		if(path == null)
		{
			sendError(ctx, FORBIDDEN);
			return;
		}
		
		File file = new File(path);
		if(file.isHidden() || !file.exists())
		{
			sendError(ctx, NOT_FOUND);
			return;
		}
		
		if(file.isDirectory())
		{
			if(uri.endsWith("/"))
			{
				//发送文件夹清单
				sendListing(ctx, file);
			}
			else
			{
				sendRedirect(ctx, uri + "/");
			}
			return;
		}
		
		if(!file.isFile())
		{
			sendError(ctx, FORBIDDEN);
			return;
		}
		
		//以只读的方式打开文件
		RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
		long fileLength = randomAccessFile.length();
		HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);
		setContentLength(response, fileLength);
		setContentTypeHeader(response, file);
		
		if(isKeepAlive(request))
			response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
		ctx.write(response);
		ChannelFuture sendFuture = ctx.write(new ChunkedFile(randomAccessFile,0,fileLength,8192), ctx.newProgressivePromise());
		sendFuture.addListener(new ChannelProgressiveFutureListener()
		{
			@Override
			public void operationProgressed(ChannelProgressiveFuture future, long progress, long total)
			{
				if (total < 0)
				{ // total unknown
					System.err.println("Transfer progress: " + progress);
				}
				else
				{
					System.err.println("Transfer progress: " + progress + " / " + total);
				}
			}

			@Override
			public void operationComplete(ChannelProgressiveFuture future) throws Exception
			{
				System.out.println("Transfer complete.");
			}
		});
		ChannelFuture lastContentFuture = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
		if (!isKeepAlive(request))
		{
			lastContentFuture.addListener(ChannelFutureListener.CLOSE);
		}
	}


	private void setContentTypeHeader(HttpResponse response, File file)
	{
		MimetypesFileTypeMap miFileTypeMap = new MimetypesFileTypeMap();
		response.headers().set(CONTENT_TYPE, miFileTypeMap.getContentType(file.getPath()));
	}

	private void sendRedirect(ChannelHandlerContext ctx, String newUri)
	{
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, FOUND);
		response.headers().set(LOCATION, newUri);
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	private void sendListing(ChannelHandlerContext ctx, File file)
	{
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK);
		response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
		StringBuilder buf = new StringBuilder();
		String filePath = file.getPath();
		String context = "<!DOCTYPE html>"
				+ "<html><head><meta charset=UTF-8>"
				+ "<title>%s目录：</title></head>"
				+ "<body><h3>%s目录：</h3>"
				+ "<ul><li>链接：<a href=\"../\">..</a></li>\r\n";
		context = String.format(context, filePath,filePath);
		buf.append(context);
		for(File f : file.listFiles())
		{
			if(file.isHidden() || !file.canRead())
				continue;
			String fileName = f.getName();
			if(!ALLOWED_FILE_NAME.matcher(fileName).matches())
				continue;
			context = String.format("<li>链接：<a href=\"%s\">%s</a></li>\r\n", fileName,fileName);
			buf.append(context);
		}
		buf.append("</ul></body></html>");
		ByteBuf byteBuf = Unpooled.copiedBuffer(buf,UTF_8);
		response.content().writeBytes(byteBuf);
		byteBuf.release();
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}

	private String sanitizeUri(String uri) throws UnsupportedEncodingException
	{
		try
		{
			uri = URLDecoder.decode(uri, "UTF-8");
			
		}
		catch (UnsupportedEncodingException e)
		{
			try
			{
				uri = URLDecoder.decode(uri, "ISO-8859-1");
			}
			catch (UnsupportedEncodingException e1)
			{
				throw e1;
			}
		}
		uri = uri.replace('/', File.separatorChar);
		if (uri.contains(File.separator + '.') || 
				uri.contains('.' + File.separator) || 
				uri.startsWith(".") || 
				uri.endsWith(".") || 
				INSECURE_URI.matcher(uri).matches())
		{
			return null;
		}
		return System.getProperty("user.dir") + File.separator + uri;
	}
	
	private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status)
	{
		FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, 
				status,Unpooled.copiedBuffer("Failure :"+ status.toString()+ "\r\n",UTF_8));
		response.headers().set(CONTENT_TYPE, "text/plain charset=UTF-8");
		ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
	}
}
