package com.dusto.mobile.biz.warehouse.controller;

import java.util.ArrayList;
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
import com.dusto.mobile.biz.scheduler.service.MasterDataService;
import com.dusto.mobile.biz.scheduler.vo.MasterData;
import com.dusto.mobile.biz.usercenter.entity.Warehouse;
import com.dusto.mobile.biz.usercenter.service.WarehouseService;
import com.dusto.mobile.biz.warehouse.vo.req.CheckZXHReq;
import com.dusto.mobile.biz.warehouse.vo.req.InboundServiceRequest;
import com.dusto.mobile.biz.warehouse.vo.res.CheckZXHRes;
import com.dusto.mobile.biz.warehouse.vo.res.ZxhListRes;
import com.dusto.mobile.common.constant.DustoConstant;
import com.dusto.mobile.common.constant.SAPContant;
import com.dusto.mobile.common.sap.jco.RfcManager;
import com.dusto.mobile.common.vo.BaseResponse;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

/**
 * 入库
 * 
 * @author Kevin
 *
 */
@Controller
@RequestMapping("inbound")

public class InboundController {
	private Logger logger = Logger.getLogger(InboundController.class);

	@Autowired
	private MasterDataService masterDataService;

	@Autowired
	private WarehouseService warehouseService;

	@ResponseBody
	@RequestMapping(value = "/binding", method = { RequestMethod.POST })
	public BaseResponse binding(@RequestBody InboundServiceRequest req) {
		Long begin = new Date().getTime();
		logger.info("entry InboundController:binding,user:"+req.getuName()+"  input parameters:" + JSON.toJSONString(req));
		BaseResponse res = new BaseResponse();

		Warehouse wh = warehouseService.selectByPrimaryKey(req.getWarehouseId());
		if (wh == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SYS_ERROR);
			logger.info("exit InboundController:binding,user:"+req.getuName()+"  output parameters:" + JSON.toJSONString(res));
			return res;
		}
		int index = getIndexOfWarehouse(wh.getWarehouseType());
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_BINDING, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit InboundController:binding,user:"+req.getuName()+"  output parameters:" + JSON.toJSONString(res));
			return res;
		}
		JCoParameterList input = function.getImportParameterList();
		input.setValue("LGNUM", req.getWarehouseId());
		input.setValue("ZTP", req.getZtp());
		input.setValue("UNAME", req.getuName());
		JCoParameterList inputParam = function.getTableParameterList();
		List<String> zxhList = new ArrayList<String>();
		if (!req.getZxhList().isEmpty()) {
			JCoTable jcoTable = inputParam.getTable("T_INPUT");
			for (String zxh : req.getZxhList()) {
				jcoTable.appendRow();
				jcoTable.setValue("ZXH", zxh);
				zxhList.add(zxh);
			}
		}
		Long sapStart = new Date().getTime();
		RfcManager.execute(function, index);
		String result = function.getExportParameterList().getString("ZFLAG");
		String errMsg = function.getExportParameterList().getString("ZMESS");
		Long sapEnd = new Date().getTime();
		logger.info("entry InboundController:binding,user:"+req.getuName()+"  output parameters:ZFLAG=" + result + ",ZMESS=" + errMsg
				+ ",zxhList.size =" + zxhList.size() + ",sap costs " + (sapEnd - sapStart) + " ms");
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
			masterDataService.updateMasterdataFlag(DustoConstant.ZXH_STATUS_BINDING, req.getZtp(), req.getWarehouseId(),
					zxhList);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		Long end = new Date().getTime();
		logger.info("exit InboundController:binding,user:"+req.getuName()+"  output parameters:" + JSON.toJSONString(res) + ",service costs "
				+ (end - begin) + " ms");

		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/bindingcheck", method = { RequestMethod.POST })
	public CheckZXHRes bindingCheck(@RequestBody CheckZXHReq req) {
		Long begin = new Date().getTime();
		logger.info("entry InboundController:bindingCheck input parameters:" + JSON.toJSONString(req));
		CheckZXHRes res = new CheckZXHRes();
		MasterData data = masterDataService.getMasterdata(req.getZxh());
		if (data == null) {
			res.setReturnCode(DustoConstant.RESPONSE_INBOUND_NODATA);
			res.setErrorMsg("箱码不存在");
		} else if (DustoConstant.ZXH_STATUS_BINDING.equals(data.getFlg())) {
			// && req.getWarehouseId().equals(data.getSapsys())) {
			res.setReturnCode(DustoConstant.RESPONSE_INBOUND_BINDED);
			res.setErrorMsg("已绑定到托盘");
		} else {
			List<MasterData> list = masterDataService.loadBindMasterdataByZtp(req.getZtp());
			if (!list.isEmpty()) {
				for (int i = 0; i < list.size(); i++) {
					MasterData masterData = list.get(i);
					if (!data.getSKUCode().equals(masterData.getSKUCode())) {
						// && req.getWarehouseId().equals(data.getSapsys())) {
						res.setReturnCode(DustoConstant.RESPONSE_INBOUND_BINDED);
						res.setErrorMsg("此项SKU与已绑定SKU不同，不允许绑定");
						logger.info("exit InboundController:bindingCheck output parameters:" + JSON.toJSONString(res));
						return res;
					}
				}

			}
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
			res.setResult(data);
		}
		Long end = new Date().getTime();
		logger.info("exit InboundController:bindingCheck output parameters:" + JSON.toJSONString(res)
				+ ",service costs " + (end - begin) + " ms");
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/unbindingcheck", method = { RequestMethod.POST })
	public BaseResponse unbindingCheck(@RequestBody CheckZXHReq req) {
		Long begin = new Date().getTime();
		logger.info("entry InboundController:unbindingCheck input parameters:" + JSON.toJSONString(req));
		BaseResponse res = new BaseResponse();
		if (req.getZtp() == null) {
			res.setReturnCode(DustoConstant.RESPONSE_PARAM_ERROR);
			res.setErrorMsg("托盘为空");
			logger.info("exit InboundController:unbindingCheck output parameters:" + JSON.toJSONString(res));
			return res;
		}
		MasterData data = masterDataService.getMasterdata(req.getZxh());
		if (data == null) {
			res.setReturnCode(DustoConstant.RESPONSE_INBOUND_NODATA);
			res.setErrorMsg("箱码不存在");
		} else if (DustoConstant.ZXH_STATUS_NO_BINDING.equals(data.getFlg())
				&& req.getWarehouseId().equals(data.getSapsys())) {
			res.setReturnCode(DustoConstant.RESPONSE_INBOUND_UNBINDING);
			res.setErrorMsg("未绑定到托盘");
		} else if (!req.getZtp().equals(data.getZtp()) && req.getWarehouseId().equals(data.getSapsys())) {
			res.setReturnCode(DustoConstant.RESPONSE_INBOUND_UNBINDING);
			res.setErrorMsg("已绑定在" + data.getZtp() + "托盘上");
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		}
		Long end = new Date().getTime();
		logger.info("exit InboundController:unbindingCheck output parameters:" + JSON.toJSONString(res)
				+ ",service costs " + (end - begin) + " ms");
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/getzxhlist/{ztp}", method = { RequestMethod.GET })
	public ZxhListRes getZxhList(@PathVariable("ztp") String ztp) {
		Long begin = new Date().getTime();
		logger.info("entry InboundController:getZxhList input parameters:ztp=" + ztp);
		ZxhListRes res = new ZxhListRes();
		if (ztp == null) {
			res.setReturnCode(DustoConstant.RESPONSE_PARAM_ERROR);
			res.setErrorMsg("托盘为空");
			logger.info("exit InboundController:getZxhList output parameters:" + JSON.toJSONString(res));
			return res;
		}

		List<MasterData> masterdatalist = masterDataService.loadBindMasterdataByZtp(ztp);
		res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		if (masterdatalist == null) {
			res.setResult("" + 0);
		} else {
			res.setResult("" + masterdatalist.size());

		}
		Long end = new Date().getTime();
		logger.info("exit InboundController:getZxhList output parameters:" + JSON.toJSONString(res) + ",cost "
				+ (end - begin) / 1000 + " ms");
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/unbinding", method = { RequestMethod.POST })
	public BaseResponse unbinding(@RequestBody InboundServiceRequest req) {
		Long begin = new Date().getTime();
		logger.info("entry InboundController:unbinding,user:"+req.getuName()+"  input parameters:" + JSON.toJSONString(req));
		BaseResponse res = new BaseResponse();

		Warehouse wh = warehouseService.selectByPrimaryKey(req.getWarehouseId());
		if (wh == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SYS_ERROR);
			logger.info("exit InboundController:unbinding,user:"+req.getuName()+"   output parameters:" + JSON.toJSONString(res));
			return res;
		}
		int index = getIndexOfWarehouse(wh.getWarehouseType());
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_UNBINDING, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit InboundController:unbinding,user:"+req.getuName()+"  output parameters:" + JSON.toJSONString(res));
			return res;
		}
		Long sapStart = new Date().getTime();
		JCoParameterList input = function.getImportParameterList();
		input.setValue("LGNUM", req.getWarehouseId());
		input.setValue("ZTP", req.getZtp());
		input.setValue("UNAME", req.getuName());
		JCoParameterList inputParam = function.getTableParameterList();
		List<String> zxhList = new ArrayList<String>();
		if (req.getZxhList() == null || req.getZxhList().size() == 0) {
			List<MasterData> masterdatalist = masterDataService.loadBindMasterdataByZtp(req.getZtp());
			JCoTable jcoTable = inputParam.getTable("T_INPUT");
			for (MasterData masterdata : masterdatalist) {
				jcoTable.appendRow();
				jcoTable.setValue("ZXH", masterdata.getZxh());
				zxhList.add(masterdata.getZxh());
			}
		} else {
			JCoTable jcoTable = inputParam.getTable("T_INPUT");
			for (String zxh : req.getZxhList()) {
				jcoTable.appendRow();
				jcoTable.setValue("ZXH", zxh);
				zxhList.add(zxh);
			}
		}
		RfcManager.execute(function, index);
		String result = function.getExportParameterList().getString("ZFLAG");
		String errMsg = function.getExportParameterList().getString("ZMESS");
		Long sapEnd = new Date().getTime();
		logger.info("entry InboundController:unbinding,user:"+req.getuName()+"  output parameters:ZFLAG=" + result + ",ZMESS=" + errMsg
				+ ",sap costs " + (sapEnd - sapStart) + " ms");
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
			masterDataService.updateMasterdataFlag(DustoConstant.ZXH_STATUS_NO_BINDING, "", req.getWarehouseId(),
					zxhList);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		Long end = new Date().getTime();
		logger.info("exit InboundController:unbinding,user:"+req.getuName()+"  output parameters:" + JSON.toJSONString(res) + ",service costs "
				+ (end - begin) + " ms");
		return res;
	}

	private int getIndexOfWarehouse(String warehouseType) {
		if ("AFS".equalsIgnoreCase(warehouseType)) {
			logger.info("current SAP sys is AFS");
			return RfcManager.SAP_SYS_AFS;
		} else if ("RETAIL".equalsIgnoreCase(warehouseType)) {
			logger.info("current SAP sys is RETAIL");
			return RfcManager.SAP_SYS_RETAIL;
		}
		logger.info("current SAP sys is AFS");
		return RfcManager.SAP_SYS_AFS;
	}
}
