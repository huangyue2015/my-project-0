package test;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
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
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class ToolMain extends Configured implements Tool
{

	public static void main(String[] args) throws Exception
	{
		Configuration conf = new Configuration();
		conf.setBoolean("bool", false);
		int exitCode = ToolRunner.run(conf, new ToolMain(),args);
		System.exit(exitCode);
	}

	static class TMapper extends Mapper<LongWritable, Text, Text, IntWritable>
	{
		private boolean bool = false;

		@Override
		public void setup(Context context)
		{
			Configuration configuration = context.getConfiguration();
			this.bool = configuration.getBoolean("bool", false);
		}

		@Override
		public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
		{
			String line = value.toString();

			for (String word : line.split("\\W+"))
			{
				if (word.length() > 0)
				{
					String letter;

					if (bool)
						letter = word.substring(0, 1);
					else
						letter = word.substring(0, 1).toLowerCase();

					context.write(new Text(letter), new IntWritable(word.length()));
				}
			}
		}
	}

	static class TReducer extends Reducer<Text, IntWritable, Text, DoubleWritable>
	{
		@Override
		public void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
		{

			long sum = 0, count = 0;

			for (IntWritable value : values)
			{
				sum += value.get();
				count++;
			}
			if (count != 0)
			{

				double result = (double) sum / (double) count;

				context.write(key, new DoubleWritable(result));
			}
		}
	}

	@Override
	public int run(String[] args) throws Exception
	{
		if (args.length != 2) {
			System.out
					.printf("Usage: AvgWordLength <input dir> <output dir>\n");
			System.exit(-1);
		}
		
		@SuppressWarnings("deprecation")
		Job job = new Job(getConf());
		
		job.setJarByClass(ToolMain.class);
		
		job.setJobName("Average Word Length");
		
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		job.setMapperClass(TMapper.class);
		job.setReducerClass(TReducer.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(DoubleWritable.class);

		boolean success = job.waitForCompletion(true);
		return(success ? 0 : 1);
	}
}
