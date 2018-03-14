package com.dusto.mobile.common.sap.jco;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dusto.mobile.common.Utils;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;

public final class RfcManager {
	private static JCOProvider provider;
	public static final int SAP_SYS_RETAIL = 0; // 对应下面两个数组的index;
	public static final int SAP_SYS_AFS = 1;// 对应下面两个数组的index;
	private static final String[] ABAP_AS_POOLED = { "ABAP_AS_WITH_POOL_RETAIL", "ABAP_AS_WITH_POOL_AFS" };
	public static final String[] PROPERTIES_FILE = { "sap_retail.properties", "sap_afs.properties" };
	// public static final String PREFIX_DEV = "DEV"+"."; //开发
	// public static final String PREFIX_SIT = "SIT"+"."; //测试
	// public static final String PREFIX_PRO = "PRO"+"."; //生成
	// public static final String PREFIX = PREFIX_PRO; //环境设置
	public static final String PREFIX_KEY = "env.config";
	// private static final String[] ABAP_AS_POOLED = {"ABAP_AS_WITH_POOL_AFS"
	// };
	// public static final String[] PROPERTIES_FILE = {"sap_afs.properties" };

	private static HashMap<String, JCoDestination> destinations = new HashMap<String, JCoDestination>();;
	private static Logger logger = Logger.getLogger(RfcManager.class);
	static {
		// catch IllegalStateException if an instance is already registered
		try {
			provider = new JCOProvider();

			Environment.registerDestinationDataProvider(provider);
			for (int i = 0; i < ABAP_AS_POOLED.length; i++) {
				Properties properties = loadProperties(PROPERTIES_FILE[i]);
				Properties connectProperties = new Properties();
				String prefix = properties.getProperty(PREFIX_KEY);
				logger.info("SAP ENV = " + prefix);
				prefix = prefix + ".";
				connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,
						properties.getProperty(prefix + DestinationDataProvider.JCO_ASHOST));
				connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,
						properties.getProperty(prefix + DestinationDataProvider.JCO_SYSNR));
				connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT,
						properties.getProperty(prefix + DestinationDataProvider.JCO_CLIENT));
				connectProperties.setProperty(DestinationDataProvider.JCO_USER,
						properties.getProperty(prefix + DestinationDataProvider.JCO_USER));
				connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,
						properties.getProperty(prefix + DestinationDataProvider.JCO_PASSWD));
				connectProperties.setProperty(DestinationDataProvider.JCO_LANG,
						properties.getProperty(DestinationDataProvider.JCO_LANG));
				connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY,
						properties.getProperty(DestinationDataProvider.JCO_POOL_CAPACITY));
				connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,
						properties.getProperty(DestinationDataProvider.JCO_PEAK_LIMIT));

				provider.changePropertiesForABAP_AS(ABAP_AS_POOLED[i], connectProperties);
			}
		} catch (IllegalStateException e) {
			logger.error(e.getMessage());
		}
	}

	private static Properties loadProperties(String fileName) {
		Properties prop = new Properties();
		try {
			String path = Utils.getClassesPath(RfcManager.class) + "conf/" + fileName;
			InputStream in = new BufferedInputStream(new FileInputStream(path));
			prop.load(in);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return prop;
	}

	public static JCoDestination getDestination(int sapSystemNo) throws JCoException {
		if (sapSystemNo >= ABAP_AS_POOLED.length) {
			sapSystemNo = SAP_SYS_RETAIL;
		}
		String key = ABAP_AS_POOLED[sapSystemNo];
		if (destinations.get(key) == null) {
			JCoDestination destination = JCoDestinationManager.getDestination(key);
			destinations.put(key, destination);
		}
		return destinations.get(key);
	}

	public static void execute(JCoFunction function, int sapSystemNo) {
		// System.out.println("SAP Function Name : " + function.getName());
		logger.info("SAP Function Name : " + function.getName());
		try {
			function.execute(getDestination(sapSystemNo));
		} catch (JCoException e) {
			e.printStackTrace();
		}
	}

	public static JCoFunction getFunction(String functionName, int sapSystemNo) {
		logger.info("SAP Function Name : " + functionName);
		JCoFunction function = null;
		try {
			function = getDestination(sapSystemNo).getRepository().getFunctionTemplate(functionName).getFunction();
		} catch (JCoException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		return function;
	}
}
