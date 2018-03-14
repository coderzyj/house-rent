package com.dusto.mobile.core.interceptors;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

public class DustoContextLoaderListener extends ContextLoaderListener {

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
		// mysql线程池泄露，关闭线程
		try {
			com.mysql.jdbc.AbandonedConnectionCleanupThread.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		// 注销database驱动
		Enumeration<Driver> enumeration = DriverManager.getDrivers();
		while (enumeration.hasMoreElements()) {
			try {
				DriverManager.deregisterDriver(enumeration.nextElement());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
