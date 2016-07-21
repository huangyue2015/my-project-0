package com.common.system;

import javax.servlet.http.HttpServletRequest;

public interface ServletInterface {
	public Object registerService(String method,HttpServletRequest req) throws Exception;
}
