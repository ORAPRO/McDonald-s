package com.laboros.reducer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<IntWritable, Text, Text, LongWritable> {
	protected void setup(org.apache.hadoop.mapreduce.Reducer<IntWritable,Text,Text,LongWritable>.Context context) throws java.io.IOException ,InterruptedException {};
	protected void reduce(IntWritable arg0, java.lang.Iterable<Text> arg1, org.apache.hadoop.mapreduce.Reducer<IntWritable,Text,Text,LongWritable>.Context arg2) throws java.io.IOException ,InterruptedException {};
	protected void cleanup(org.apache.hadoop.mapreduce.Reducer<IntWritable,Text,Text,LongWritable>.Context context) throws java.io.IOException ,InterruptedException {};
	

}
