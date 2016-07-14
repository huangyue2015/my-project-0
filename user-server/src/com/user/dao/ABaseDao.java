package com.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.common.system.DBconnect.ConnectionPool;
import com.common.system.DBconnect.DatabaseParameter;
import com.common.system.DBconnect.IConnectionPool;

public abstract class ABaseDao<T>{
	protected IConnectionPool iConnectionPool = new ConnectionPool(new DatabaseParameter());
	
	/**
	 * 向数据库中更新信息
	 * @throws SQLException 
	 */
	public boolean update(String sql) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = iConnectionPool.getConnection();
			ps = con.prepareStatement(sql);
			int i = ps.executeUpdate();
			return i == 0 ? false : true;
		} finally {
			if(con != null)
				iConnectionPool.releaseConnection(con);
			if(ps != null)
				ps.close();
		}
	}
}
