package com.hbase.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTableFactory;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * hbase基本使用
 * @author HY
 *
 */
public class HbaseTest
{

	/**
	 * 初始化配置
	 */
	public static Configuration configuration = null;

	static
	{
		configuration = HBaseConfiguration.create();
		configuration.set("hbase.master", "192.168.153.131:60010");
		
	}

	public static void main(String[] args)
	{
		
	}

	public static void createTable(String tableName)
	{
		try
		{
			HBaseAdmin hBaseAdmin = new HBaseAdmin(configuration);
			//判断表是否存在
			if(hBaseAdmin.tableExists(tableName))
				System.out.println("table alreday exists");
			else
			{
				HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
				hTableDescriptor.addFamily(new HColumnDescriptor("default"));
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
	}

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

	public static void dropTable(String tableName)
	{
		try
		{
			HBaseAdmin admin = new HBaseAdmin(configuration);
			admin.disableTable(tableName);
			admin.deleteTable(tableName);
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

	}

	public static void deleteRow(String tablename, String rowkey)
	{
		try
		{
			HTable table = new HTable(configuration, tablename);
			List<Delete> list = new ArrayList<>();
			Delete d1 = new Delete(rowkey.getBytes());
			list.add(d1);

			table.delete(list);
			System.out.println("删除行成功!");

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

	}


	public static void QueryAll(String tableName)
	{
		HTablePool pool = new HTablePool(configuration, 1000);
		HTable table = (HTable) pool.getTable(tableName);
		try
		{
			ResultScanner rs = table.getScanner(new Scan());
			for (Result r : rs)
			{
				System.out.println("获得到rowkey:" + new String(r.getRow()));
				for (KeyValue keyValue : r.raw())
				{
					System.out.println("列：" + new String(keyValue.getFamily()) + "====值:" + new String(keyValue.getValue()));
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}