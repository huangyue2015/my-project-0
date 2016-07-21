package com.common.system;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.common.util.Jackson;
import com.common.util.StringUtil;

public class DefaultServletInterlayer {
	
	private static final Logger logger = Logger.getLogger(DefaultServletInterlayer.class);
	private ServletInterface servletInterface;
	private HttpServletRequest req;
	private HttpServletResponse resp;
	
	public DefaultServletInterlayer(ServletInterface servletInterface,HttpServletRequest req, HttpServletResponse resp)
	{
		this.servletInterface = servletInterface;
		this.req = req;
		this.resp = resp;
	}
	
	public void common() throws Exception
	{
		end(resp, init(req));
	}
	
	
	/**
	 * 扩展校验
	 * @param req
	 * @param resp
	 * @throws Exception 
	 */
	private Object init(HttpServletRequest req) throws Exception
	{
		try {
			String method = req.getParameter("method");
			if(logger.isInfoEnabled())
				logger.info("当前请求的服务："+method);
			if(StringUtil.isNullOrEmpty(method))
				throw new Exception("传入的参数有误，参数方法不能为空");
			else
				return servletInterface.registerService(method,req);//服务注册
		} catch (Exception e) {
			logger.error(e);
			throw e;
		}
	}
	
	
	private void end(HttpServletResponse resp,Object obj)
	{
		serializeRessult(resp, obj);
	}
	
	/**
	 * 将返回结果序列化为JSON字符串
	 * @param resp
	 * @param pr
	 */
	public void serializeRessult(HttpServletResponse resp, Object obj)
	{
		try {
			String spr = obj == null ? "" : Jackson.getDefaultObjectMapper().writeValueAsString(obj);
			if(logger.isInfoEnabled())
				logger.info("web服务调用返回结果："+ spr);
			PrintWriter out = resp.getWriter();
			out.print(spr);
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error(e);
		}
    }
	
}
