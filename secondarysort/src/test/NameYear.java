package test;

import java.io.IOException;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;

import example.StringPairWritable;

public class NameYear
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}

	static class NameYearMapper extends Mapper<LongWritable, Text, StringPairWritable, Text>
	{
		@Override
		public void map(LongWritable k , Text v,Context context) throws IOException, InterruptedException
		{
			String[] words = v.toString().split("\\W+");
			
			if(words.length > 2)
				context.write(new StringPairWritable(words[0], words[2]), v);
		}
		
	}
	
	static class NameYearRedecer extends Reducer<StringPairWritable, Text, Text, Text>
	{
		@Override
		public void reduce(StringPairWritable k, Iterable<Text> values, Context context) throws IOException, InterruptedException
		{
			context.write(new Text(""),values.iterator().next());
		}
	}
}
