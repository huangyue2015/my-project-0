package com.common.system.DBconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class ConnectionPool implements IConnectionPool
{
	private DatabaseParameter dbbean;
	
	private boolean isActive = false;//初始化连接池活动状态
	
	private int coutActive = 0;//记录总的连接数
	
	private List<Connection> freeConnection = new Vector<Connection>();//空闲连接
	
	private List<Connection> activeConnection = new Vector<Connection>();//活动连接
	
	private static ThreadLocal<Connection> threadlocal = new ThreadLocal<Connection>();// 将线程和连接绑定，保证事务能统一执行  
	
	public ConnectionPool(){}
	public ConnectionPool(DatabaseParameter dbbean)
	{
		this.dbbean = dbbean;
		init();
		checkPool();
	}
	
	public void init()
	{
		try
		{
			//获得初始化的连接数
			int initpool = dbbean.getInitConnection() == 0 ? 5 : dbbean.getInitConnection();
			for(int i = 0;i < initpool; i++)
			{
				Connection conn = newConn();
				if(conn != null)
				{
					freeConnection.add(conn);
					coutActive++;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得连接
	 */
	@Override
	public Connection getConnection()
	{
		Connection conn = null;
		try
		{
			//判断是否超过最大连接限制
			if(coutActive < dbbean.getMaxStatementPerConnection())
			{
				if(freeConnection.size() > 0)
				{
					conn = freeConnection.get(0);
					if(conn != null)threadlocal.set(conn);
					freeConnection.remove(0);
				}
				else conn = newConn();
			}
			else
			{
				//等待,直到获取新的连接
				wait(1000);
				conn = getConnection();
			}
			if(isValid(conn))
			{
				activeConnection.add(conn);
				coutActive++;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 获得当前连接
	 */
	public Connection getCurrentConnection()
	{
		Connection conn = null;
		try
		{
			conn = threadlocal.get();//从默认线程里面取
			if(!isValid(conn))conn = getConnection();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 释放连接
	 */
	public synchronized void releaseConnection(Connection conn)
	{
		try
		{
			if(isValid(conn) && dbbean.getMaxStatementPerConnection()<freeConnection.size())
			{
				freeConnection.add(conn);
				activeConnection.remove(conn);
				coutActive--;
				threadlocal.remove();
				//唤醒等待的线程去抢占连接
				notifyAll();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * 销毁连接池
	 * 
	 */
	public void destroy()
	{
		try
		{
			for(Connection conn :freeConnection)
			{
				if(isValid(conn))conn.close();
			}
			for(Connection conn :activeConnection)
			{
				if(isValid(conn))conn.close();
			}
			isActive = false;
			coutActive = 0;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 连接池状态
	 */
	public boolean isActive()
	{
		return isActive;
	}

	/**
	 * 定时检查连接池状况
	 */
	public Map<String,Integer> checkPool()
	{
	    final Map<String,Integer> resultMap = new HashMap<String,Integer>();
		try
		{
			new Timer().schedule(new TimerTask()
			{
				
				@Override
				public void run()
				{
					/**
					 * 1、线程池里面的连接状态
					 * 2、线程池最小，最大连接数
					 * 3、其他的连接状态进行检查
					 */
					resultMap.put("空线程池连接数",freeConnection.size());
					resultMap.put("活动连接数",activeConnection.size());
					resultMap.put("总的连接数",coutActive);
				}
			}, 1000*60*60,1000*60*60);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return resultMap;
	}

	/**
	 * 获取新的连接
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	private synchronized Connection newConn() throws ClassNotFoundException, SQLException
	{
		Connection conn = null;
		if(dbbean != null)
		{ 
			Class.forName(dbbean.getDriverClassName());
			conn = DriverManager.getConnection(dbbean.getConnectionUrl(), dbbean.getUsername(), dbbean.getPassword());
		}
		return conn;
	}
	
	/**
	 * 判断连接是否有效
	 * @param conn
	 * @return
	 * @throws SQLException
	 */
	private boolean isValid(Connection conn) throws SQLException
	{
		if(conn == null || conn.isClosed())return false;
		return true;
	}
}
