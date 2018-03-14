package com.dusto.mobile.common.sap.conn;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

import com.dusto.mobile.common.sap.test.StepByStepClient;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class ConnectPooled {
	static String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";
	   static {
	      Properties connectProperties = loadProperties();
//	      Properties connectProperties = new Properties();
//	      connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,
//	            "192.168.111.137");
//	      connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "00");
//	      connectProperties
//	            .setProperty(DestinationDataProvider.JCO_CLIENT, "800");
//	      connectProperties.setProperty(DestinationDataProvider.JCO_USER,
//	            "SAPECC");
//	      // 注：密码是区分大小写的，要注意大小写
//	      connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,
//	            "sapecc60");
//	      connectProperties.setProperty(DestinationDataProvider.JCO_LANG, "en");
	 
//	      // *********连接池方式与直接不同的是设置了下面两个连接属性
//	      // JCO_PEAK_LIMIT - 同时可创建的最大活动连接数，0表示无限制，默认为JCO_POOL_CAPACITY的值
//	      // 如果小于JCO_POOL_CAPACITY的值，则自动设置为该值，在没有设置JCO_POOL_CAPACITY的情况下为0
//	      connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,
//	            "10");
//	      // JCO_POOL_CAPACITY - 空闲连接数，如果为0，则没有连接池效果，默认为1
//	      connectProperties.setProperty(
//	            DestinationDataProvider.JCO_POOL_CAPACITY, "3");
	 
	      createDataFile(ABAP_AS_POOLED, "jcoDestination", connectProperties);
	   }
	 
	   static void createDataFile(String name, String suffix, Properties properties) {
	      File cfg = new File(name + "." + suffix);
	      if (!cfg.exists()) {
	         try {
	            FileOutputStream fos = new FileOutputStream(cfg, false);
	            properties.store(fos, "for tests only !");
	            fos.close();
	         } catch (Exception e) {
	            e.printStackTrace();
	         }
	      }
	   }
	 
	   public static void connectWithPooled() throws JCoException {
	      JCoDestination destination = JCoDestinationManager
	            .getDestination(ABAP_AS_POOLED);
	      System.out.println("Attributes:");
	      System.out.println(destination.getAttributes());
	   }
	   private static Properties loadProperties() { 
	    	Properties prop = new Properties(); 
	    	try{
	    	String path = getClassesPath(ConnectPooled.class) + "conf/sap_afs.properties";
	    	InputStream in = new BufferedInputStream (new FileInputStream(path));
	    	prop.load(in);
	    	}catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	return prop;
	    }
	    private static String getClassesPath(Class clazz) {  
	        String path = clazz.getClassLoader().getResource("").getPath();  
	        try {  
	            return java.net.URLDecoder.decode(path, "UTF-8");  
	        } catch (java.io.UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }  
	        return "";  
	    }  
	   public static void main(String[] args) throws JCoException {
	      connectWithPooled();
	   }
}
