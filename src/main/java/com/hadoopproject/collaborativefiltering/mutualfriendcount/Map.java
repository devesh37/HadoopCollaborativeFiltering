package com.hadoopproject.collaborativefiltering.mutualfriendcount;
/**
 * The Mapper class - it just reverses the key value pair
 */

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Map extends Mapper<LongWritable,Text,TextPair,IntWritable> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
    {

    	String[] data = value.toString().split("-");
    	if(data.length!=2)
    		return;
        String user=data[0];
        String[] friendList=data[1].split(",");
        int lenFriendList=friendList.length;
        for(int i=0;i<lenFriendList;i++)
        {
        	String f1=friendList[i];
        	for(int j=i+1;j<lenFriendList;j++)
        	{
        		String f2=friendList[j];
        		context.write(new TextPair(f1,f2), new IntWritable(1));
        		context.write(new TextPair(f2,f1), new IntWritable(1));
        	}
        }
        for(String friend:friendList)
        {
        	context.write(new TextPair(user,friend), new IntWritable(0));
        }
    }
}