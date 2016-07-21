package com.user.interfaces;

import com.common.entity.PR;
import com.common.system.ServletInterface;

/**
 * 用户服务接口
 * @author Administrator
 *
 */
public interface UserInterface extends ServletInterface{
	
	/**
	 * 添加用户信息(url="http://192.168.1.109:8080/client/user?method=userRegister")
	 * @param  mobile 手机号
	 * @param  password 密码
	 */
	public PR userRegister(String mobile, String password);
	
	/**
	 * 发送短信接口(url="http://192.168.1.109:8080/client/user?method=sendShotMessage")
	 * @param mobile 手机号码
	 */
	public PR sendShotMessage(String mobile) throws Exception;
	
	/**
	 * 用户登录(url="http://192.168.1.109:8080/client/user?method=userLogin")
	 * @param username 用户名
	 * @param password 密码
	 *
	 */
	public PR userLogin(String username, String password);
}
