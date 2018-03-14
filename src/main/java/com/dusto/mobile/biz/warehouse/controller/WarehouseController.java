package com.dusto.mobile.biz.warehouse.controller;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dusto.mobile.biz.usercenter.entity.Warehouse;
import com.dusto.mobile.biz.usercenter.service.WarehouseService;
import com.dusto.mobile.biz.usercenter.vo.res.WarehouseListRes;
import com.dusto.mobile.biz.usercenter.vo.res.WarehouseVoRes;
import com.dusto.mobile.common.constant.DustoConstant;
import com.dusto.mobile.common.vo.BaseResponse;

@Controller
@RequestMapping("warehouse")
public class WarehouseController {

	@Autowired
	private WarehouseService warehouseService;

	private Logger logger = Logger.getLogger(WarehouseController.class);

	@ResponseBody
	@RequestMapping("/list")
	public WarehouseListRes findAllWarehouse() {
		logger.info("entry WarehouseController:findAllWarehouse");
		Long begin = new Date().getTime();
		List<Warehouse> list = warehouseService.findAllWarehouse();
		WarehouseListRes warehouseList = new WarehouseListRes();
		warehouseList.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		warehouseList.setResult(list);
		Long end = new Date().getTime();
		logger.info("exit WarehouseController:findAllWarehouse output parameters:" + JSON.toJSONString(warehouseList)
				+ ", service costs " + (end - begin) + " ms");
		return warehouseList;
	}

	@RequestMapping(value = "/get/{warehouseId}")
	@ResponseBody
	public WarehouseVoRes selectByPrimaryKey(@PathVariable("warehouseId") String warehouseId) {
		logger.info("entry WarehouseController:selectByPrimaryKey input parameters: warehouseId:" + warehouseId);
		Long begin = new Date().getTime();
		Warehouse warehouse = warehouseService.selectByPrimaryKey(warehouseId);
		WarehouseVoRes res = new WarehouseVoRes();
		res.setResult(warehouse);
		res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		Long end = new Date().getTime();
		logger.info("exit WarehouseController:selectByPrimaryKey  output parameters:" + JSON.toJSONString(res)
				+ ", service costs " + (end - begin) + " ms");
		return res;
	}

	@RequestMapping(value = "/delete/{warehouseId}")
	@ResponseBody
	public BaseResponse deleteByPrimaryKey(@PathVariable("warehouseId") String warehouseId) {
		logger.info("entry WarehouseController:deleteByPrimaryKey input parameters: warehouseId:" + warehouseId);
		Long begin = new Date().getTime();
		int ret = warehouseService.deleteByPrimaryKey(warehouseId);
		BaseResponse res = new BaseResponse();
		if (ret == 1) {
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SQL_NODATA);
		}
		Long end = new Date().getTime();
		logger.info("exit WarehouseController:deleteByPrimaryKey output parameters:" + JSON.toJSONString(res)
				+ ", service costs " + (end - begin) + " ms");
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/insert", method = { RequestMethod.POST })
	public BaseResponse insertWarehouse(@RequestBody Warehouse warehouse) {
		logger.info("entry WarehouseController:insertWarehouse input parameters:" + JSON.toJSONString(warehouse));
		Long begin = new Date().getTime();
		BaseResponse res = new BaseResponse();
		Warehouse wh = warehouseService.selectByPrimaryKey(warehouse.getWarehouseId());
		if (wh != null) {
			res.setReturnCode(DustoConstant.RESPONSE_DUPLICATE_KEY);
			return res;
		}
		int ret = warehouseService.insert(warehouse);

		if (ret == 1) {
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SQL_NODATA);
		}
		Long end = new Date().getTime();
		logger.info("exit WarehouseController:insertWarehouse output parameters:" + JSON.toJSONString(res)
				+ ", service costs " + (end - begin) + " ms");
		return res;
	}
}
