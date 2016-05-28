package example;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class AvgWordLength
{

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException
	{
		if(args.length != 2)
			System.exit(-1);
		
		Job job = new Job();
		
		job.setJarByClass(AvgWordLength.class);
		job.setJobName("統計單詞平均長度");
		
		job.setMapperClass(AvgWordLengthMapper.class);
		job.setReducerClass(AvgWordLengthReducer.class);
		
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		boolean success = job.waitForCompletion(true);
		
		System.exit(success ? 0 : 1);
	}

	static class AvgWordLengthMapper extends Mapper<LongWritable,Text,Text,IntWritable>
	{
		@Override
		public void map(LongWritable key ,Text value,Context context) throws IOException, InterruptedException
		{
			String lineValue = value.toString();//輸入的一行字符
			String[]  words = lineValue.split("\\W+");
			for(String word : words)
			{
				if(word.length() > 0)
				{
					String l  = word.substring(0, 1);
					context.write(new Text(l), new IntWritable(word.length()));
				}
			}
		}
	}
	
	static class AvgWordLengthReducer extends Reducer<Text,IntWritable,Text,DoubleWritable>
	{
		@Override
		public void reduce(Text key,Iterable<IntWritable> values,Context  context) throws IOException, InterruptedException
		{
			long sum = 0,count = 0;
			for(IntWritable v : values)
			{
				sum += v.get();
				count++;
			}
			if(count != 0)
			{
				double avg = (double)sum/(double)count;
				context.write(key, new DoubleWritable(avg));
			}
		}
	}
}
