package com.hadoopproject.collaborativefiltering.recommendation;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

import com.hadoopproject.collaborativefiltering.mutualfriendcount.TextPair;

public class Reduce extends Reducer<Text, TextIntPair, Text, Text> {
	static int n = 10;

	@Override
	public void reduce(Text key, Iterable<TextIntPair> values, Context context) throws IOException, InterruptedException {
		
		Text user = new Text(key.toString());
		StringBuilder t = new StringBuilder();
		ArrayList<TextIntPair> topN = new ArrayList<TextIntPair>();
		
		for (TextIntPair val : values) {
			Text friend= new Text(val.getFirst());
            IntWritable mutualFriendCount= new IntWritable(val.getSecond().get());
			
			if (topN.size()<n) {
				topN.add(new TextIntPair(friend,mutualFriendCount));
				continue;
			}
		
			int  i = 0;

			for (TextIntPair val1 : topN) {
				if (mutualFriendCount.get() > val1.getSecond().get()) {
					topN.set(i, new TextIntPair(friend,mutualFriendCount));
					break;
				}
				i++;
			}

		}
		
		
		for (TextIntPair val1 : topN) {
			t.append(val1.getFirst().toString()+"|");
		}

		context.write(user,new Text(t.toString()));
	}
}
