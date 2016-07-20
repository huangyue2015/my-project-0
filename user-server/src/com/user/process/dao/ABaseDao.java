package com.user.process.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.common.system.DBconnect.ConnectionPool;
import com.common.system.DBconnect.DatabaseParameter;
import com.common.system.DBconnect.IConnectionPool;

public abstract class ABaseDao<T>{
	protected IConnectionPool iConnectionPool = new ConnectionPool(new DatabaseParameter());
	protected static Logger logger = Logger.getLogger(ABaseDao.class);
	/**
	 * 向数据库中更新信息
	 * @throws SQLException 
	 */
	public boolean update(String sql) throws Exception {
		
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = iConnectionPool.getConnection();
			if(logger.isInfoEnabled())
				logger.info("执行更新操作的sql:" + sql);
			ps = con.prepareStatement(sql);
			int i = ps.executeUpdate();
			return i == 0 ? false : true;
		} 
		catch (Exception e){
			logger.error(e);
			throw e;
		}
		finally {
			if(con != null)
				iConnectionPool.releaseConnection(con);
			if(ps != null)
				ps.close();
		}
	}
}
