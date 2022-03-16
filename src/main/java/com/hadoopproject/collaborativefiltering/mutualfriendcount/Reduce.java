package com.hadoopproject.collaborativefiltering.mutualfriendcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

import com.hadoopproject.collaborativefiltering.mutualfriendcount.TextPair;

public class Reduce extends Reducer<TextPair, IntWritable, TextPair, IntWritable>{
    @Override
    public void reduce(TextPair key,  Iterable<IntWritable> values, Context context) throws IOException, InterruptedException
    {
        int mutualFriendCount=0;
        
        for(IntWritable value: values)
        {
        	if(value.get()==0)
        		return;
            mutualFriendCount += value.get();
     
        }
        context.write(key, new IntWritable(mutualFriendCount));
    }
}
