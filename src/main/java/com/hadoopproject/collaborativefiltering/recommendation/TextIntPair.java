package com.hadoopproject.collaborativefiltering.recommendation;
/**
 * A Custome WritableComparable class to represent a bigram
 */

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class TextIntPair implements WritableComparable<TextIntPair>{

    private Text first;
    private IntWritable second;

    public void set(Text first, IntWritable second){
        this.first=first;
        this.second=second;
    }

    public Text getFirst() {
        return first;
    }
    public IntWritable getSecond() {
        return second;
    }


    public TextIntPair(){
        set(new Text(),new IntWritable());
    }

    public TextIntPair(String first, String second){
        set(new Text(first),new IntWritable(Integer.parseInt(second)));
    }
    public void set(String first, String second){
        set(new Text(first),new IntWritable(Integer.parseInt(second)));
    }

    public TextIntPair(Text first, IntWritable second){
        set(first,second);
    }
    public TextIntPair(Text pair){
    	String[] bigramAr=pair.toString().split(" ");
        set(bigramAr[0],bigramAr[1]);
    }
  
    public void write(DataOutput out) throws IOException{
        first.write(out);
        second.write(out);
    }

    public void readFields(DataInput in) throws IOException{
        first.readFields(in);
        second.readFields(in);
    }

    public int compareTo(TextIntPair tp){
        int cmp = first.compareTo(tp.first);
        if (cmp!=0) {
            return cmp;
        }
        return second.compareTo(tp.second);
    }


    @Override
    public int hashCode(){
        return first.hashCode()*163 + second.hashCode();
    }

    @Override
    public boolean equals(Object o){
        if( o instanceof TextIntPair){
            TextIntPair tp = (TextIntPair) o;
            return first.equals(tp.first) && second.equals(tp.second);
        }
        return  false;
    }

    @Override
    public String toString(){
        return first.toString() +"\t" +second.toString();
    }


}
