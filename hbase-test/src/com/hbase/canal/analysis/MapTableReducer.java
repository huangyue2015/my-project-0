package com.hbase.canal.analysis;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;



public class MapTableReducer extends Configured implements Tool
{

	public static void main(String[] args) throws Exception
	{
		int exitCode = ToolRunner.run(new Configuration(), new MapTableReducer(),args);
		System.out.println(exitCode);
		System.exit(exitCode);
	}
	
	@Override
	public int run(String[] args) throws Exception
	{
		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum.", "localhost");
		
		Job job = new Job(conf);
		job.setJarByClass(MapTableReducer.class);
		
		FileInputFormat.setInputPaths(job, new Path("hdfs://master:9000/data/text.txt"));
		
		
		job.setMapperClass(MapperClass.class);
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		
		TableMapReduceUtil.initTableReducerJob("qualification", ReducerClass.class, job);
		
		boolean success = job.waitForCompletion(true);
		return success ? 0 : 1;
	}
	
	static class MapperClass extends Mapper<LongWritable,Text,Text,Text>
	{
		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
		{
			String[] item = value.toString().split("\\W+");
			String k  = item[0];
			String v  = item[1];
			context.write(new Text(k), new Text(v));
		}
	}
	
	static class ReducerClass extends TableReducer<Text, Text, ImmutableBytesWritable>
	{
		@Override
		public void reduce(Text key, Iterable<Text>  values, Context context)
		{
			StringBuffer str = new StringBuffer();
			for(Text s : values)
				str.append(s.toString());
			String value = new String(str);
			/*Put putrow = new Put(key.getBytes());
			putrow.add("cardno".getBytes(), "name".getBytes(), value.getBytes());*/
			System.out.println(value);
		}
	}
}
