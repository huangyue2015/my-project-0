package com.user.service;

import com.common.entity.PR;
import com.user.pojo.User;

public interface IUserService {
	/**
	 * 用户注册
	 * @param user
	 * @return
	 */
	public PR userAdd(User user);
	
	public PR userLogin(User user);
}
