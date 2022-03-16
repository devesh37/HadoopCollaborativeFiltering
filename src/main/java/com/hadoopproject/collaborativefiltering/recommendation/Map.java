package com.hadoopproject.collaborativefiltering.recommendation;
/**
 * The Mapper class - it just reverses the key value pair
 */

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Map extends Mapper<LongWritable,Text,Text,TextIntPair> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException
    {

    	String[] data = value.toString().split("\t");
    	Text u1=new Text(data[0]);
    	TextIntPair userCountPair=new TextIntPair(data[1],data[2]);
    	context.write(u1, userCountPair); 
    	
    }
}