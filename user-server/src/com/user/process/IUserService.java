package com.user.process;

import com.common.entity.PR;
import com.user.process.entity.User;

public interface IUserService {
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public PR userAdd(User user);
	
	public PR userLogin(User user);
}
