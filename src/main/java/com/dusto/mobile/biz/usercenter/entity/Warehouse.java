package com.dusto.mobile.biz.usercenter.entity;

import java.io.Serializable;

public class Warehouse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3235544345183037589L;

	private String warehouseId;
	private String warehouseName;
	private String warehouseType;

	public String getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(String warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getWarehouseType() {
		return warehouseType;
	}

	public void setWarehouseType(String warehouseType) {
		this.warehouseType = warehouseType;
	}

}
