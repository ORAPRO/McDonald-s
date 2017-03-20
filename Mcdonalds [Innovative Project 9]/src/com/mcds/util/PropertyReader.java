package com.mcds.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

public class PropertyReader {
	
	private static PropertyReader McdonaldsPropertyReader;
	public  Properties properties;
	
	private PropertyReader(){
		
	}
	
	public static PropertyReader getHealthcarePropertyReader(){
		if(McdonaldsPropertyReader==null)
		{
			McdonaldsPropertyReader=new PropertyReader();
		}
		return McdonaldsPropertyReader;
	}
		public static void main(String args[]){
			PropertyReader propertyReader=new PropertyReader();
			String baselocation=propertyReader.getProperty(McdonaldsConstants.BASE_LOCATION);
			System.out.println("baselocation :"+baselocation);
			propertyReader.getAllProperties();
		}
	public  Properties loadProperties(){
	try {
		File file = new File("F:/RANADEEPWS/batch300117/eclipsews/Mcdonalds [Innovative Project 9]/src/com/mcds/util/McdonaldsConstants.java");
		//File file = new File("C:/Users/LENOVO/Desktop/TASK-SHEET/Hadoop_Resumes");
		FileInputStream fileInput = new FileInputStream(file);
		properties = new Properties();
		properties.load(fileInput);
		fileInput.close();
		printProperties();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return properties;
	}
	private  void printProperties(){
		Enumeration enuKeys = properties.keys();
		while (enuKeys.hasMoreElements()) {
			String key = (String) enuKeys.nextElement();
			String value = properties.getProperty(key);
			System.out.println(key + ": " + value);
		}
	}
	public String getProperty(String key){
			loadProperties();
			if(properties!=null)
			{
				return properties.getProperty(key);
			}
			return null;
			
	}
			public Properties getAllProperties(){
			System.out.println("properties:" +properties);
			return properties;
			
		}

			public static PropertyReader getInstance() {
				// TODO Auto-generated method stub
				return null;
			}

	
	
	



}
