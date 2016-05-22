package com.hbase.canal.analysis;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class TableMapperReducer extends Configured implements Tool
{

	public static void main(String[] args) throws Exception
	{
		int exitCode = ToolRunner.run(new Configuration(), new TableMapperReducer(),args);
		System.out.println(exitCode);
		System.exit(exitCode);
	}

	
	static class MapperClass1 extends  TableMapper<Text, Text>
	{
		@Override
		public void map(ImmutableBytesWritable rowkey, Result value,Context context) throws IOException, InterruptedException
		{
			Map<byte[],byte[]> m = value.getFamilyMap("cert".getBytes());
			byte[] key = m.get("areaid".getBytes());
			byte[] v = m.get("companyno".getBytes());
//			System.out.println(new Text(key) + ":  " + new Text(v));
			if(key != null &&  v != null)
				context.write(new Text(new String(key)),new Text(new String(v)));
		}
	}
	
	static class RedecerClass1 extends Reducer<Text, Text, Text, Text>
	{
		@Override
		public void reduce(Text key, Iterable<Text> values,Context context) throws IOException, InterruptedException
		{
			StringBuffer stringBuffer = new StringBuffer();
			for(Text value : values)
			{
					stringBuffer.append(value.toString());
					stringBuffer.append(",");
			}
			System.out.println(key.toString() + ":" + stringBuffer.toString());
			context.write(key, new Text(stringBuffer.toString()));
		}
	}

	@Override
	public int run(String[] args) throws Exception
	{
		  Configuration conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum.", "localhost");  
        
        Job job = new Job(conf,"Hbase");
        job.setJarByClass(TableMapperReducer.class);

        FileOutputFormat.setOutputPath(job, new Path("hdfs://master:9000/output/160519/02"+ new Date().getTime()));
        
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(Text.class);
        
        
        job.setReducerClass(RedecerClass1.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        	
        Scan scan = new Scan();
        TableMapReduceUtil.initTableMapperJob("qualification", scan, MapperClass1.class, Text.class, Text.class, job);
        return job.waitForCompletion(true) ? 0 : 1;
	}
}
