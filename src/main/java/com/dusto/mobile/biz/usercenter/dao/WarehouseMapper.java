package com.dusto.mobile.biz.usercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dusto.mobile.biz.usercenter.entity.Warehouse;

public interface WarehouseMapper {

	public int addWarehouse(Warehouse warehouse);

	public List<Warehouse> loadWarehouse();

	public Warehouse getWarehouse(@Param(value = "warehouseId") String warehouseId);

	public int deleteWarehouseByPrimaryKey(@Param(value = "warehouseId") String warehouseId);

	public Warehouse getWarehouseByName(@Param(value = "warehouseName") String warehouseName);
}
