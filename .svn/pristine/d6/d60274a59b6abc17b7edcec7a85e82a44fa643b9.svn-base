package com.dusto.mobile.common;

import org.apache.log4j.Logger;

import com.dusto.mobile.biz.usercenter.entity.Warehouse;
import com.dusto.mobile.biz.usercenter.service.WarehouseService;
import com.dusto.mobile.common.sap.jco.RfcManager;

public class Utils {
	
	private static Logger logger = Logger.getLogger(Utils.class);

	public static String getClassesPath(Class clazz) {
		String path = clazz.getClassLoader().getResource("").getPath();
		try {
			return java.net.URLDecoder.decode(path, "UTF-8");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	public static int getIndexOfWarehouse(WarehouseService warehouseService, String warehouseId) {
		Warehouse wh = warehouseService.selectByPrimaryKey(warehouseId);
		if (wh == null) {
			return -1;
		}
		String warehouseType = wh.getWarehouseType();
		if ("AFS".equalsIgnoreCase(warehouseType)) {
			logger.info("current SAP sys is AFS");
			return RfcManager.SAP_SYS_AFS;
		} else if ("RETAIL".equalsIgnoreCase(warehouseType)) {
			logger.info("current SAP sys is RETAIL");
			return RfcManager.SAP_SYS_RETAIL;
		}
		logger.info("current SAP sys is AFS");
		return -1;
	}
}
