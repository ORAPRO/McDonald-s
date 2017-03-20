package com.mcds.job;

import java.io.File;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import com.mcds.util.HDFSUtil;
import com.mcds.util.McdonaldsConstants;
import com.mcds.util.PropertyReader;





public class Cronjobs  extends Configured implements Tool {

	 public static PropertyReader propertyReader=null;
	
	public static void main(String args[])throws Exception {
		System.out.println("In Main Method");
		
		//Step-1 Validate input arguments
		if(args.length > 2){
			System.out.println("Java Usage" + Cronjobs.class.getName() + "In valid arguments lenth and Properties path");
	       return;
		}
		propertyReader = PropertyReader.getInstance();
		propertyReader.loadProperties();
		System.out.println(propertyReader.getAllProperties().toString());
		
		//step-2 Initialize configuration
		Configuration con = new Configuration(Boolean.TRUE);
		con.set("fs.defaultFS", "hdfs://localhost:8020");
		
		//step-3 Run ToolRunner.run method to set the arguments to config
		try{
			int i = ToolRunner.run(con, new Cronjobs(), args);
			if(i == 0){
				System.out.println(HDFSUtil.SUCCESS);
			}else{
				System.out.println(HDFSUtil.FAILED + "STATUS code:" + i);
			}
		}catch(Exception e){
			System.out.println(HDFSUtil.FAILED);
			e.printStackTrace();
		}
		
	}

	public int run(String[] arg0) throws Exception {
		
		System.out.println("In Run Method");
		
		final String fBaseLocation = propertyReader.getProperty(McdonaldsConstants.BASE_LOCATION);
		final String fFileSourceLocation = fBaseLocation + HDFSUtil.FILE_SEPERATOR_PROPERTY + propertyReader.getProperty(McdonaldsConstants.LANDING_ZONE);
		
		//create directory if does not exist
		FILEUtil.createDirectory(fFileSourceLocation);
		final String fArchiveLocation = fBaseLocation + HDFSUtil.FILE_SEPERATOR_PROPERTY + propertyReader.getProperty(McdonaldsConstants.ARCHIVE);
		
		FILEUtil.createDirectory(fArchiveLocation);
		final String fFailedLocation = fBaseLocation + HDFSUtil.FILE_SEPERATOR_PROPERTY + propertyReader.getProperty(McdonaldsConstants.FAILED);
		
		final String fHDFSBaseLocation = propertyReader.getProperty(McdonaldsConstants.HDFS_BASE_LOCATION);
		final String fDestinationPath = fHDFSBaseLocation + HDFSUtil.FILE_SEPERATOR_PROPERTY + propertyReader.getProperty(McdonaldsConstants.HDFS_LANDING_ZONE);
		
		// Load the configuration
		Configuration con = getConf();
		

		// Create a instance for File System object.
		FileSystem hdfs = FileSystem.get(con);
		
		// Create directory on HDFS File System if does not exist.
		HDFSUtil.createdHDFSDirectories(hdfs, fDestinationPath);
		
		
		while(true){
			File  fInboxDir = new File(fFileSourceLocation);
			if(fInboxDir.isDirectory()){
				File[] fListFiles = fInboxDir.listFiles();
				for(File fInputFile : fListFiles){
					String[] args = {fInputFile.getAbsolutePath().toString(), fDestinationPath};
					boolean isCopied = HDFSUtil.copyFromLocal(con, hdfs, args);
					if(isCopied){
						FILEUtil.moveFile(fInputFile, new File(fArchiveLocation));
					}
					else{
						FILEUtil.moveFile(fInputFile, new File(fFailedLocation));
					}
				}
			}
			Thread.sleep(1000 * 60 * 20);
		}
	}

}
