package com.hadoopproject.collaborativefiltering.recommendation;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.hadoopproject.collaborativefiltering.mutualfriendcount.MutualFriendCount;




public class Recommendation extends Configured implements Tool{
	private static final String INPUT_PATH = MutualFriendCount.getOutputPath();
	private static String OUTPUT_PATH = null;
	
	public int run(String[] args) throws IllegalArgumentException, IOException, ClassNotFoundException, InterruptedException {
		
		Configuration conf = new Configuration();
        Job job = new Job(conf, "Recommendation");
        job.setJarByClass(Recommendation.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(TextIntPair.class);
        job.setNumReduceTasks(4);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);

        FileInputFormat.addInputPath(job,new Path(INPUT_PATH));
        FileOutputFormat.setOutputPath(job, new Path(OUTPUT_PATH));

        return job.waitForCompletion(true)?0:1;
	}
	
	public static void main(String[] args) throws Exception {
		Recommendation.OUTPUT_PATH=args[1];
    	int exitCode=ToolRunner.run(new MutualFriendCount(), args);
		int exitCode1 = ToolRunner.run(new Recommendation(), args);
        System.exit(exitCode+exitCode1);
    
    }
}
