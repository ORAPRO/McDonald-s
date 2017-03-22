package com.laboros.job;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.laboros.mapper.WordCountMapper;
import com.laboros.reducer.WordCountReducer;

public class WordCountJob extends Configured implements Tool {
	
	public static void main(String[] args) {
System.out.println("IN MAIN METHOD");
		
		//Step-1 Validation
		if(args.length<2)
		{
			System.out.println("JAVA Usage "+WordCountJob.class.getName()+" [configuration] /path/to/local/file /path/hdfs/dest/location");
			return;
		}
		
		//step -2 Loading Configuration
		Configuration conf=new Configuration(Boolean.TRUE);
		conf.set("fs.defaultFS", "hdfs://localhost:8020");
		try {
			//step-3 : INvoking ToolRunner.run --> It is a generic option parser 
			//parse command line arguments and set to the configuration
			int i=ToolRunner.run(conf, new WordCountJob(), args);
			if(i==0)
			{
				System.out.println("SUCCESS");
			}
		} catch (Exception e) {
			System.out.println("FAILURE");
			e.printStackTrace();
		}
	}

	public int run(String[] args) throws Exception {
		// step-1 : get the configuration object that was parsed by toolrunner.run
		Configuration conf=super.getConf();
		// you have to set any parameter to configuration object here
		
		//step-2 : Job instance is useful to access the cluster
		Job wordCountJob=Job.getInstance(conf, WordCountJob.class.getName());
		
		//step-3 : setting classpath for the mapper program
		wordCountJob.setJarByClass(WordCountJob.class);
		
		//setting input and output
		
		//step-4 : setting input
		final String input=args[0];
		//convert ito URI, bcuz URL always represent in URI
		final Path inputPath=new Path(input);
		TextInputFormat.addInputPath(wordCountJob, inputPath);
		wordCountJob.setInputFormatClass(TextInputFormat.class);
		
		//step-5 : setting output
		final String output=args[1];
		//convert ito URI, bcuz URL always represent in URI
		final Path outputPath=new Path(output);
		TextOutputFormat.setOutputPath(wordCountJob, outputPath);
		wordCountJob.setOutputFormatClass(TextOutputFormat.class);
		
		//step-6 : setting mapper
		wordCountJob.setMapperClass(WordCountMapper.class);
		//step-7 : setting mapper output key and value class
		
		
		//step-8 : setting reducer
		wordCountJob.setReducerClass(WordCountReducer.class);
		//step-9 : setting reducer output key and value class
		
		//step-10 : Initiating Trigger method
		wordCountJob.waitForCompletion(Boolean.TRUE);
		return 0;
	}

	/**
	 * @param args
	 */
	

}
