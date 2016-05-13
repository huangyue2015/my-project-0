package com.hbase.canal.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hbase.canal.pool.db.ConnectionPool;
import com.hbase.canal.pool.db.Inface.IConnectionPool;
import com.hbase.canal.pool.db.bo.DBBean;

public class DataStream
{
	
	public static void main(String[] args)
	{
		List<Map<String,String>> lists = new DataStream().getDataFromDB("Select * from qualification");
		for(Map<String,String> map :lists)
		{
			for(Iterator<String> i = map.keySet().iterator(); i.hasNext();)
			{
				String k = i.next();
				String value = map.get(k);
				System.out.println("key:"+k+"           value:"+value);
			}
			System.out.println("\n");
		}
		System.out.println(lists.size());
	}

	public List<Map<String,String>> getDataFromDB(String sql)
	{
		IConnectionPool connectionPool = new ConnectionPool(new DBBean("hytest"));
		Connection connection = connectionPool.getConnection();
		List<Map<String,String>> lists = new ArrayList<>();
		try
		{
			Statement statment = connection.createStatement();
			ResultSet resultSet = statment.executeQuery(sql);
			String[] metadata = Meta.Qualification.METADATA;
			while(resultSet.next())
			{
				Map<String,String> map = new HashMap<>();
				for(String meta : metadata)
				{
					map.put(meta, resultSet.getString(meta));
				}
				lists.add(map);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return lists;
	}
	
	
}
