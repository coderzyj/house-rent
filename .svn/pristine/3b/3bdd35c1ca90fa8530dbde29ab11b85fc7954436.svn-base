package com.dusto.mobile.biz.usercenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dusto.mobile.biz.usercenter.dao.WarehouseMapper;
import com.dusto.mobile.biz.usercenter.entity.Warehouse;
import com.dusto.mobile.biz.usercenter.service.WarehouseService;

@Service("warehouseService")
public class WarehouseServiceImpl implements WarehouseService {

	@Resource
	private WarehouseMapper warehouseMapper;

	@Override
	@Transactional
	public int insert(Warehouse warehouse) {
		if (warehouse == null) {
			return 0;
		}
		return warehouseMapper.addWarehouse(warehouse);
	}

	@Override
	public List<Warehouse> findAllWarehouse() {
		return warehouseMapper.loadWarehouse();
	}

	@Override
	public Warehouse selectByPrimaryKey(String warehouseId) {
		if (warehouseId == null) {
			return null;
		}
		return warehouseMapper.getWarehouse(warehouseId);
	}

	@Override
	public Warehouse selectByWarehouseName(String warehouseName) {
		if (warehouseName == null) {
			return null;
		}
		return warehouseMapper.getWarehouseByName(warehouseName);
	}
	
	@Override
	@Transactional
	public int deleteByPrimaryKey(String warehouseId) {
		if (warehouseId == null) {
			return 0;
		}
		return warehouseMapper.deleteWarehouseByPrimaryKey(warehouseId);
	}

}
