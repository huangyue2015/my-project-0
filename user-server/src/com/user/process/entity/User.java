package com.user.process.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.common.entity.POJO;
import com.common.util.StringUtil;

public class User implements Serializable,POJO{

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String username;
	
	private String password;
	
	private String nickname;
	
	private String mobile;
	
	private String headPortrait;
	
	private String signature;
	
	private String token;
	
	private String qq;
	
	private String weixing;
	
	private String weibo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getHeadPortrait() {
		return headPortrait;
	}

	public void setHeadPortrait(String headPortrait) {
		this.headPortrait = headPortrait;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getWeixing() {
		return weixing;
	}

	public void setWeixing(String weixing) {
		this.weixing = weixing;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}
	
	@Override
	public Map<String, String> getMap()
	{
		Map<String, String> map = new HashMap<>();
		if(StringUtil.isNotNullOrEmpty(id))
			map.put("id", id);
		if(StringUtil.isNotNullOrEmpty(username))
			map.put("username", username);
		if(StringUtil.isNotNullOrEmpty(password))
			map.put("password", password);
		if(StringUtil.isNotNullOrEmpty(nickname))
			map.put("nickname", nickname);
		if(StringUtil.isNotNullOrEmpty(mobile))
			map.put("mobile", mobile);
		if(StringUtil.isNotNullOrEmpty(headPortrait))
			map.put("headPortrait", headPortrait);
		if(StringUtil.isNotNullOrEmpty(signature))
			map.put("signature", signature);
		if(StringUtil.isNotNullOrEmpty(token))
			map.put("token", token);
		if(StringUtil.isNotNullOrEmpty(qq))
			map.put("qq", qq);
		if(StringUtil.isNotNullOrEmpty(weixing))
			map.put("weixing", weixing);
		if(StringUtil.isNotNullOrEmpty(weibo))
			map.put("weibo", weibo);
		return map;
	}
}
