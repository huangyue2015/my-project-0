package com.control;

import javax.servlet.http.HttpServletRequest;

public interface UserInterface {
	
	/**
	 * 添加用户信息
	 * @param request
	 */
	public void addUser(HttpServletRequest request);
}
