package com.user.process.handler;

import com.common.util.SqlFactory;
import com.common.util.StringUtil;
import com.user.process.entity.User;

public class DefaultUserHandler {
	private User user;
	
	
	public DefaultUserHandler(User user) {
		this.user = user;
	}
	
	/**
	 * 获取注册的sql(并判断注册信息是否合法)
	 * @return
	 * @throws Exception 
	 */
	public String getInsertSql() throws Exception
	{
		if(user == null)
			throw new Exception("用户信息未初始化");
		String id = user.getId();
		String username = user.getUsername();
		String password = user.getPassword();
		String mobile = user.getMobile();
		if(StringUtil.isNullOrEmpty(id))
			id = StringUtil.getUUID();
		if(StringUtil.isNullOrEmpty(username))
			throw new Exception("用户名不能为空");
		if(StringUtil.isNullOrEmpty(password))
			throw new Exception("密码不能为空");
		if(StringUtil.isNullOrEmpty(mobile))
			throw new Exception("手机号不能为空");
		return new SqlFactory<User>().getInsertSql(user);
	}
	
	public String checkUserLogin() throws Exception
	{
		if(StringUtil.isNullOrEmpty(user.getUsername()))
			throw new Exception("用户名不能为空");
		if(StringUtil.isNullOrEmpty(user.getPassword()))
			throw new Exception("密码不能为空");
		return user.getUsername();
	}
}
