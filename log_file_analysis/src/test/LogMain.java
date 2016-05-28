package test;

import java.io.IOException;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class LogMain
{

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException
	{
		if(args.length != 2)
			System.exit(-1);
		
		Job job = new Job();
		
		job.setJarByClass(LogMain.class);
		job.setJobName("統計單詞平均長度");
		
		job.setMapperClass(LogMapper.class);
		job.setReducerClass(LogReducer.class);
		
		
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		boolean success = job.waitForCompletion(true);
		
		System.exit(success ? 0 : 1);
	}

	static class LogMapper extends Mapper<LongWritable,Text,Text,IntWritable>
	{
		@Override
		public void map(LongWritable key,Text  value,Context context) throws IOException, InterruptedException
		{
			String lineValue = value.toString();
			String[] words = lineValue.split("\\s+");
			for(String word : words)
			{
				if(word.startsWith("leap."))
				{
					context.write(new Text(word), new IntWritable(1));
				}
			}
		}
	}
	
	static class LogReducer extends Reducer<Text, IntWritable, Text, IntWritable>
	{
		@Override
		public void reduce(Text key , Iterable<IntWritable>  values, Context context) throws IOException, InterruptedException
		{
			int sum = 0;
			for(IntWritable i : values)
				sum += i.get();
			context.write(key, new IntWritable(sum));
		}
	}
}
