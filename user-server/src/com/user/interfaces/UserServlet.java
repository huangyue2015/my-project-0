package com.user.interfaces;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.common.entity.PR;
import com.common.server.SMSHandler;
import com.common.util.Cache;
import com.common.util.Jackson;
import com.common.util.StringUtil;
import com.user.process.UserService;
import com.user.process.entity.User;



@WebServlet("/user")
public class UserServlet extends HttpServlet implements UserInterface
{
	/*****************接口枚举*****************/
	
	//用户注册
	private static final String USERREGISTER = "userRegister";
	
	//发送短信
	private static final String SENDSHOTMESSAGE = "sendShotMessage";
	
	//用户登录
	private static final String USERLOGIN = "userLogin";
	
	
	
	
	
	
	
	
	
	
	/*****************接口枚举*****************/
	
	
	
	private static final long serialVersionUID = 1L;
	
	private UserService userService = new UserService();
	private User user;
	
	/**********************************************************************私有方法**************************************************************************************/
	
	
	/**
	 * 将返回结果序列化为JSON字符串
	 * @param resp
	 * @param pr
	 */
	private void serializePR(HttpServletResponse resp, PR pr)
	{
		try {
			String spr = Jackson.getDefaultObjectMapper().writeValueAsString(pr);
			System.out.println(spr);
			PrintWriter out = resp.getWriter();
			out.print(spr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
	/**
	 * 用来验证是否是登录用户
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
	private void initTokenCheck(HttpServletRequest req, HttpServletResponse resp) throws Exception
	{
		String token = req.getParameter("token");
		if(StringUtil.isNullOrEmpty(token))
			throw new Exception("token未在参数中传入");
		if(!Cache.tokenMap.containsKey(token))
			throw new Exception("会话已失效，请重新登录");
		
	}
	
	/**********************************************************************私有方法**************************************************************************************/
	
	
	
	
	
	
	
	
	
	
	
	
	
	/********************************************************************** 接 口 **************************************************************************************/
	
	/**
	 * 接口调用控制器
	 */
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp)
	{
		PR pr = new PR();
		try {
			String method = req.getParameter("method");
			if(StringUtil.isNullOrEmpty(method))
				throw new Exception("传入的参数有误，参数方法不能为空");
			else
			{
				switch (method) {
				case USERREGISTER:
					pr = userRegister(req.getParameter("mobile"), req.getParameter("password"));
					break;
				case SENDSHOTMESSAGE:
					pr = sendShotMessage(req.getParameter("mobile"));
					break;
				case USERLOGIN:
					pr = userLogin(req.getParameter("username"), req.getParameter("password"));
					break;
				default:
					throw new Exception("服务未找到");
				}
			}
			serializePR(resp, pr);
		} catch (Exception e) {
			e.printStackTrace();
			pr = new PR(0, "", e.getMessage());
			serializePR(resp, pr);
		}
	}

	/**
	 * 添加用户信息(用户注册)
	 */
	@Override
	public PR userRegister(String mobile, String password){
		user = new User();
		user.setUsername(mobile);
		user.setPassword(password);
		user.setId(StringUtil.getUUID());
		user.setMobile(mobile);
		PR pr = userService.userAdd(user);
		return pr;
	}

	/**
	 * 发送短信接口(用户注册)
	 * @throws Exception 
	 */
	@Override
	public PR sendShotMessage(String mobile) throws Exception{
		if(StringUtil.isNullOrEmpty(mobile))
			throw new Exception("手机号码不能为空");
		String randomNum = StringUtil.getRandomNum4();
		PR pr = new SMSHandler().sendOneSMS(mobile, String.format("您的手机验证码为：%s", randomNum));
		if(pr.getResultstate() == 0)
			return pr;
		else
		{
			pr.setResult(randomNum);
			return pr;
		}
	}

	/**
	 * 用户登录
	 */
	@Override
	public PR userLogin(String username, String password){
		user = new User();
		user.setUsername(username);
		user.setPassword(password);
		PR pr = userService.userLogin(user);
		if(pr!= null && pr.getResultstate() == 1)
		{
			String token = StringUtil.getUUID();
			Cache.tokenMap.put(token, user);
			pr.setResult(token);
		}
		return pr;
	}
	
	
}
