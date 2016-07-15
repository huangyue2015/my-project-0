package com.user.process;

import com.common.entity.PR;
import com.common.entity.User;
import com.common.util.StringUtil;
import com.user.process.dao.UserDao;
import com.user.process.handler.DefaultUserHandler;

public class UserService implements IUserService{
	
	private UserDao userDao = new UserDao();
	
	/**
	 * 用户注册（必须包含手机号，用户名和密码）
	 */
	@Override
	public PR userAdd(User user) {
		try {
			boolean b = userDao.update(new DefaultUserHandler(user).getInsertSql());
			if(b)
				return new PR(1, "", "");
			else
				return new PR(0, "", "未知异常");
		} catch (Exception e) {
			e.printStackTrace();
			return new PR(0, "", e.getMessage());
		}
	}

	/**
	 * 用户登录
	 */
	@Override
	public PR userLogin(User user) {
		try {
			String username = new DefaultUserHandler(user).checkUserLogin();
			String s = userDao.selectUserByUsername(username);
			if(StringUtil.isNullOrEmpty(s))
				return new PR(0, "","用户名不存在");
			if(s.equals(user.getPassword()))
				return new PR(1, "", "");
			else
				return new PR(0, "", "密码错误");
		} catch (Exception e) {
			e.printStackTrace();
			return new PR(0, "", e.getMessage());
		}
	}
	
}
