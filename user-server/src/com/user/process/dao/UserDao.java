package com.user.process.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.user.process.entity.User;

public class UserDao extends ABaseDao<User> implements IUserDao{

	@Override
	public String selectUserByUsername(String username) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = iConnectionPool.getConnection();
			String sql = "select password from user where username='"+username+"' or mobile = '"+username+"'";
			if(logger.isInfoEnabled())
				logger.info("selectUserByUsername ---> sql : " + sql);
			ps = con.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next())
			{
				return resultSet.getString("password");
			}
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
		return null;
	}

	
	
}
