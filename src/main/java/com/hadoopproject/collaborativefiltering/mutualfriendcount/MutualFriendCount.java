package com.hadoopproject.collaborativefiltering.mutualfriendcount;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class MutualFriendCount extends Configured implements Tool{
	private static final String OUTPUT_PATH = "temp";
	public static String getOutputPath()
	{
		return OUTPUT_PATH;
	}
	public int run(String[] args) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException {
		
		Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(conf);
        if(fs.exists(new Path(OUTPUT_PATH))){
            fs.delete(new Path(OUTPUT_PATH),true);
        }

        Job job = new Job(conf, "MutualFriendCount");
        job.setJarByClass(MutualFriendCount.class);
        job.setOutputKeyClass(TextPair.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapOutputKeyClass(TextPair.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setNumReduceTasks(4);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));

        return job.waitForCompletion(true)?0:1;
	}
	

}
