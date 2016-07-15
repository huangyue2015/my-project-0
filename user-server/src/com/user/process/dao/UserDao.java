package com.user.process.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.common.entity.User;

public class UserDao extends ABaseDao<User> implements IUserDao{

	@Override
	public String selectUserByUsername(String username) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = iConnectionPool.getConnection();
			String sql = "select password from user where username='"+username+"' or mobile = '"+username+"'";
			ps = con.prepareStatement(sql);
			ResultSet resultSet = ps.executeQuery();
			while(resultSet.next())
			{
				return resultSet.getString("password");
			}
		} finally {
			if(con != null)
				iConnectionPool.releaseConnection(con);
			if(ps != null)
				ps.close();
		}
		return null;
	}

	
	
}
