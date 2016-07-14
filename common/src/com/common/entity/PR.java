package com.common.entity;

import java.io.Serializable;

public class PR implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int resultstate;//返回状态
	
	private Object result;//返回结果
	
	private String resultdesc;//返回结果描述

	public int getResultstate() {
		return resultstate;
	}

	public void setResultstate(int resultstate) {
		this.resultstate = resultstate;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getResultdesc() {
		return resultdesc;
	}

	public void setResultdesc(String resultdesc) {
		this.resultdesc = resultdesc;
	}

	public PR(int resultstate, Object result, String resultdesc) {
		super();
		this.resultstate = resultstate;
		this.result = result;
		this.resultdesc = resultdesc;
	}

	public PR() {
		super();
	}
	
	
}
