package com.hbase.canal.analysis;

import java.io.IOException;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

public class HBaseMapReducer
{

	static class Mapper1 extends TableMapper<Text,IntWritable>
	{
		/**
		 * 第一个参数key为Hbase表的rowkey主键，第二个参数value为key主键对应的记录集合，
		 * 此处的map核心实现是遍历key主键对应的记录集合value，将其组合成一条记录通过contentx.write(key,value)填充到< key,value>键值对中
		 */
		@Override
		protected void map(ImmutableBytesWritable key,Result value,Context context) throws IOException,InterruptedException
		{
			
		}
	}
	
	static class Reducer1 extends TableReducer<Text, IntWritable, ImmutableBytesWritable>
	{
		@Override
		protected void reduce(Text key,Iterable<IntWritable> values,Context context) throws IOException,InterruptedException
		{
			
		}
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

}
