package com.user.interfaces;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.common.entity.PR;
import com.common.server.SMSHandler;
import com.common.system.DefaultServletInterlayer;
import com.common.util.Cache;
import com.common.util.StringUtil;
import com.user.process.UserService;
import com.user.process.entity.User;



@WebServlet("/user")
public class UserServlet extends HttpServlet implements UserInterface
{
	private static final long serialVersionUID = 1L;
	private static final Class<?> UserServletClass = UserServlet.class;
	private static final Logger logger = Logger.getLogger(UserServletClass);
	/**********************************************************************私有方法**************************************************************************************/
	
	/**
	 * 用来验证是否是登录用户
	 * @param req
	 * @param resp
	 * @throws Exception
	 */
//	private void initTokenCheck(HttpServletRequest req, HttpServletResponse resp) throws Exception
//	{
//		String token = req.getParameter("token");
//		if(StringUtil.isNullOrEmpty(token))
//			throw new Exception("token未在参数中传入");
//		if(!Cache.tokenMap.containsKey(token))
//			throw new Exception("会话已失效，请重新登录");
//		
//	}
	
	/**
	 *  注册服务
	 * @throws Exception 
	 */
	@Override
	public Object registerService(String method,HttpServletRequest req) throws Exception
	{
		Object obj = null;
		switch (method)
		{
			case "userRegister":
				obj = userRegister(req.getParameter("mobile"), req.getParameter("password"));
				break;
			case "sendShotMessage":
				obj = sendShotMessage(req.getParameter("mobile"));
				break;
			case "userLogin":
				obj = userLogin(req.getParameter("username"), req.getParameter("password"));
				break;
				
			default:
				throw new Exception("服务未找到");
		}
		return obj;
	}
	
	
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp)
	{
		DefaultServletInterlayer defaultServletInterlayer = new DefaultServletInterlayer(this, req, resp);
		try {
			defaultServletInterlayer.common(); 
		} catch (Exception e) {
			logger.error(e);
			defaultServletInterlayer.serializeRessult(resp, new PR(0, "", e.getMessage()));
		}
	}
	
	

	/**********************************************************************服务接口*************************************************/
	
	/**
	 * 添加用户信息(用户注册)
	 */
	@Override
	public PR userRegister(String mobile, String password){
		User user = new User();
		UserService userService = new UserService();
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
		User user = new User();
		UserService userService = new UserService();
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
