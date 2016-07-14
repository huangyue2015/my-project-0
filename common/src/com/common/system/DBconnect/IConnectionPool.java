package com.common.system.DBconnect;

import java.sql.Connection;
import java.util.Map;

public interface IConnectionPool
{
	//获得连接
	public Connection getConnection();
	
	//得到当前的连接
	public Connection getCurrentConnection();
	
	//回收连接
	public void releaseConnection(Connection conn);
	
	//销毁清空
	public void destroy();
	
	//连接池活动状态
	public boolean isActive();
	
	//定时器检查连接池
	public Map<String,Integer> checkPool();
}
