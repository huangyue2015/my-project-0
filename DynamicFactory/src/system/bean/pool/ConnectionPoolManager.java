package system.bean.pool;

import java.sql.Connection;
import java.util.Hashtable;


public class ConnectionPoolManager
{

	// 连接池存放
	public static Hashtable<String,ConnectionPool> pools = new Hashtable<String, ConnectionPool>();
	
	public static DatabaseParameter bean = new DatabaseParameter();
	
	private final static String initPoolName = "p";
	// 初始化
	private ConnectionPoolManager()
	{
		init();
	}

	// 单例实现
	public static ConnectionPoolManager getInstance()
	{
		return Singtonle.instance;
	}

	private static class Singtonle
	{
		private static ConnectionPoolManager instance = new ConnectionPoolManager();
	}

	// 初始化所有的连接池
	public void init()
	{
		ConnectionPool pool = new ConnectionPool(bean);
		if (pool != null)
		{
			pools.put(bean.getPoolName(), pool);
			System.out.println("Info:Init connection successed ->" + bean.getPoolName());
		}
	}

	// 获得连接,根据连接池名字 获得连接
	public Connection getConnection(String poolName)
	{
		Connection conn = null;
		if (pools.size() > 0 && pools.containsKey(poolName))
		{
			conn = getPool(poolName).getConnection();
		}
		else
		{
			System.out.println("Error:Can't find this connecion pool ->" + poolName);
		}
		return conn;
	}

	public Connection getConnection()
	{
		return getConnection(initPoolName);
	}
	
	// 关闭，回收连接
	public void close(String poolName, Connection conn)
	{
		IConnectionPool pool = getPool(poolName);
		try
		{
			if (pool != null)
			{
				pool.releaseConnection(conn);
			}
		}
		catch (Exception e)
		{
			System.out.println("连接池已经销毁");
			e.printStackTrace();
		}
	}

	public void close(Connection conn)
	{
		close(initPoolName, conn);
	}
	
	
	// 清空连接池
	public void destroy()
	{
		destroy(initPoolName);
	}
	
	// 清空连接池
	public void destroy(String poolName)
	{
		IConnectionPool pool = getPool(poolName);
		if (pool != null)
		{
			pool.destroy();
		}
	}


	// 获得连接池
	public ConnectionPool getPool()
	{
		return getPool(initPoolName);
	}
	
	// 获得连接池
	public ConnectionPool getPool(String poolName)
	{
		ConnectionPool pool = null;
		if (pools.size() > 0)
		{
			pool = pools.get(poolName);
		}
		return pool;
	}
}