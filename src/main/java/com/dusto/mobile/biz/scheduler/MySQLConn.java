package com.dusto.mobile.biz.scheduler;

import java.beans.PropertyVetoException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.dusto.mobile.biz.scheduler.job.CustomScheduledJob;
import com.dusto.mobile.common.Utils;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class MySQLConn {
	private static Properties props = new Properties();
	static {
		try {
			props = loadProperties();
			// Class.forName(props.getProperty("driverClassName"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Connection getConnection() {
		try {
			Class.forName(props.getProperty("jdbc.driver"));
			ComboPooledDataSource ds = new ComboPooledDataSource();
			ds.setJdbcUrl(props.getProperty("jdbc.url"));
			ds.setUser(props.getProperty("jdbc.username"));
			ds.setPassword(props.getProperty("jdbc.password"));
			ds.setDriverClass(props.getProperty("jdbc.driver"));
			ds.setAcquireIncrement(5);
			ds.setInitialPoolSize(20);
			ds.setMinPoolSize(2);
			ds.setMaxPoolSize(50);

			Connection con = ds.getConnection();

			return con;
		} catch (PropertyVetoException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
		// con.close();
	}

	private static Properties loadProperties() throws IOException {
		Properties prop = new Properties();

		String path = Utils.getClassesPath(CustomScheduledJob.class) + "conf/jdbc.properties";
		InputStream in = new BufferedInputStream(new FileInputStream(path));
		prop.load(in);

		return prop;
	}

}
