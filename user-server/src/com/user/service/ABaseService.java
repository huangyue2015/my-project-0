package com.user.service;

import com.user.dao.ABaseDao;
import com.user.dao.IUserDao;
import com.user.dao.UserDao;
import com.user.pojo.User;

public abstract class ABaseService {
	protected ABaseDao<User> auserDao = new UserDao();
	protected IUserDao userDao = new UserDao();
}
