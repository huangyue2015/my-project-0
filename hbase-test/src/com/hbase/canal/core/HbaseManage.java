package com.hbase.canal.core;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;

public class HbaseManage
{

	/**
	 * 初始化配置
	 */
	public static Configuration configuration = null;
	public final static String configuration_hbase_master = "hbase.master";
	public final static String configuration_hbase_master_uri = "192.168.153.131:60010";

	static
	{
		configuration = HBaseConfiguration.create();
		configuration.set(configuration_hbase_master, configuration_hbase_master_uri);
	}
	
	/**
	 * 新建HBASE表
	 * @param m
	 */
	public void creatTable(Meta m)
	{
		creatTable(m.getTableName(),m.getMetaData());
	}
	
	/**
	 * 新建HBASE表
	 * @param tableName
	 * @param familys
	 */
	public void creatTable(String tableName,String[] familys)
	{
		try
		{
			HBaseAdmin hBaseAdmin = new HBaseAdmin(configuration);
			//判断表是否存在
			if(hBaseAdmin.tableExists(tableName))
				throw new Exception("数据表已经存在，无法覆盖");
			else
			{
				HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
				for(String family:familys)
					hTableDescriptor.addFamily(new HColumnDescriptor(family));
				hBaseAdmin.createTable(hTableDescriptor);
				System.out.println("creat table "+tableName+" is ok");
			}
		}
		catch (MasterNotRunningException e)
		{
			e.printStackTrace();
		}
		catch (ZooKeeperConnectionException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 向HBase表中添加数据
	 * @param tableName 表名
	 * @param map 数据集合
	 * @param rowkey 主键
	 */
	public static void insertData(String tableName,Map<String,String> map,String rowkey)
	{
		System.out.println("insert data start");
		HTablePool pool = new HTablePool(configuration, 1000);
		HTable table = (HTable) pool.getTable(tableName);
		// 一个PUT代表一行数据，再NEW一个PUT表示第二行数据,每行一个唯一的ROWKEY，此处rowkey为put构造方法中传入的值 
		Put put = new Put(rowkey.getBytes());
		
		Iterator<String> keyIterable = map.keySet().iterator();
		while (keyIterable.hasNext())
		{
			String key = keyIterable.next();
			String val = map.get(key);
			put.add(key.getBytes(), null, val.getBytes());
		}
		try
		{
			table.put(put);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		System.out.println("insert data is ok \n\n\n");
	}
	
	public static void main(String[] args)
	{
		Meta meta = new Meta.Qualification();
		
		//1创建Hbase表
		new HbaseManage().creatTable(meta);
		
		//2查询PostgreSQL 中的数据,并插入到Hbase中
		List<Map<String,String>> lists = new DataStream().getDataFromDB("Select * from qualification");
		for(Map<String,String> map :lists)
		{
			StringBuffer stringBuffer = new StringBuffer(map.get("areaid"));
			stringBuffer.append(map.get("providetime").substring(0, 4));
			stringBuffer.append(map.get("providetime").substring(5, 7));
			stringBuffer.append(map.get("providetime").substring(8, 10));
			String rowKey = stringBuffer.toString();
			insertData(meta.getTableName(), map, rowKey);
		}
	}
}
