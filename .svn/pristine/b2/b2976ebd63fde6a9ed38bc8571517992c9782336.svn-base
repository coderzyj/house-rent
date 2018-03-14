package com.dusto.mobile.biz.warehouse.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dusto.mobile.biz.usercenter.service.WarehouseService;
import com.dusto.mobile.biz.warehouse.vo.req.Delivery;
import com.dusto.mobile.biz.warehouse.vo.req.DeliveryCheckUnitServiceRequest;
import com.dusto.mobile.biz.warehouse.vo.req.DeliveryGetUnitServiceRequest;
import com.dusto.mobile.biz.warehouse.vo.req.PackCheckServiceRequest;
import com.dusto.mobile.biz.warehouse.vo.req.PackServiceRequest;
import com.dusto.mobile.biz.warehouse.vo.req.TannumVo;
import com.dusto.mobile.biz.warehouse.vo.res.DeliveryGetUnitServiceResponse;
import com.dusto.mobile.biz.warehouse.vo.res.DeliveryUnit;
import com.dusto.mobile.common.Utils;
import com.dusto.mobile.common.constant.DustoConstant;
import com.dusto.mobile.common.constant.SAPContant;
import com.dusto.mobile.common.sap.jco.RfcManager;
import com.dusto.mobile.common.vo.BaseResponse;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

/**
 * RDC 整箱复核/单双复核
 * 
 * @author Kevin
 *
 */
@Controller
@RequestMapping("pack")
public class PackedController {
	private Logger logger = Logger.getLogger(PackedController.class);

	@Autowired
	private WarehouseService warehouseService;

	@ResponseBody
	@RequestMapping(value = "/checkbox", method = { RequestMethod.POST })
	public BaseResponse checkBox(@RequestBody PackCheckServiceRequest req) {
		logger.info("entry PackedController:checkBox, input parameters:" + JSON.toJSONString(req));
		Long begin = new Date().getTime();
		BaseResponse res = new BaseResponse();
		int index = Utils.getIndexOfWarehouse(warehouseService, req.getWarehouseId());
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_PACKED, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit PackedController:checkBox output parameters:" + JSON.toJSONString(res));
			return res;
		}
		Long sapStart = new Date().getTime();
		JCoParameterList input = function.getImportParameterList();
		input.setValue("LGNUM", req.getWarehouseId());
		input.setValue("ZXH", req.getZxh());
		input.setValue("UNAME", req.getuName());
		input.setValue("TANUM", req.getTanum());
		RfcManager.execute(function, index);
		String result = function.getExportParameterList().getString("ZFLAG");
		String errMsg = function.getExportParameterList().getString("ZMESS");
		Long sapEnd = new Date().getTime();
		logger.info("entry PackedController:checkBox output parameters:ZFLAG=" + result + ",ZMESS=" + errMsg
				+ ",sap costs " + (sapEnd - sapStart) + " ms");
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		Long end = new Date().getTime();
		logger.info("exit PackedController:checkBox output parameters:" + JSON.toJSONString(res) + ",service costs "
				+ (end - begin) + " ms");
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/checkunit", method = { RequestMethod.POST })
	public BaseResponse checkUnit(@RequestBody PackCheckServiceRequest req) {
		logger.info("entry PackedController:checkUnit, input parameters:" + JSON.toJSONString(req));
		Long begin = new Date().getTime();
		BaseResponse res = new BaseResponse();
		int index = Utils.getIndexOfWarehouse(warehouseService, req.getWarehouseId());
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_CHECK_PACKED, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit PackedController:checkUnit output parameters:" + JSON.toJSONString(res));
			return res;
		}
		Long sapStart = new Date().getTime();
		JCoParameterList input = function.getImportParameterList();
		input.setValue("LGNUM", req.getWarehouseId());
		input.setValue("ZXH", req.getZxh());
		input.setValue("UNAME", req.getuName());
		input.setValue("TANUM", req.getTanum());
		RfcManager.execute(function, index);
		String result = function.getExportParameterList().getString("ZFLAG");
		String errMsg = function.getExportParameterList().getString("ZMESS");
		Long sapEnd = new Date().getTime();
		logger.info("entry PackedController:checkUnit output parameters:ZFLAG=" + result + ",ZMESS=" + errMsg
				+ ",sap costs " + (sapEnd - sapStart) + " ms");
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		Long end = new Date().getTime();
		logger.info("exit PackedController:checkUnit output parameters:" + JSON.toJSONString(res) + ",service costs "
				+ (end - begin) + " ms");
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/submit", method = { RequestMethod.POST })
	public BaseResponse submit(@RequestBody PackServiceRequest req) {
		logger.info("entry PackedController:submit, input parameters:" + JSON.toJSONString(req));
		Long begin = new Date().getTime();
		BaseResponse res = new BaseResponse();
		int index = Utils.getIndexOfWarehouse(warehouseService, req.getWarehouseId());
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_CONFIRM_PACKED, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit PackedController:submit output parameters:" + JSON.toJSONString(res));
			return res;
		}
		Long sapStart = new Date().getTime();
		JCoParameterList input = function.getImportParameterList();
		input.setValue("LGNUM", req.getWarehouseId());
		input.setValue("ZXH", req.getZxh());
		input.setValue("UNAME", req.getuName());
		JCoParameterList inputParam = function.getTableParameterList();
		if (req.getTanlist() != null && req.getTanlist().size() > 0) {
			JCoTable jcoTable = inputParam.getTable("T_INPUT");
			for (TannumVo tan : req.getTanlist()) {
				jcoTable.appendRow();
				jcoTable.setValue("TANUM", tan.getTanum());
				jcoTable.setValue("VSOLA", tan.getVsola());
				jcoTable.setValue("ZTM", tan.getZtm());
			}
		}
		RfcManager.execute(function, index);
		String result = function.getExportParameterList().getString("ZFLAG");
		String errMsg = function.getExportParameterList().getString("ZMESS");
		Long sapEnd = new Date().getTime();
		logger.info("entry PackedController:submit output parameters:ZFLAG=" + result + ",ZMESS=" + errMsg
				+ ",sap costs " + (sapEnd - sapStart) + " ms");
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		Long end = new Date().getTime();
		logger.info("exit PackedController:submit output parameters:" + JSON.toJSONString(res) + ",service costs "
				+ (end - begin) + " ms");
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/getunit", method = { RequestMethod.POST })
	public DeliveryGetUnitServiceResponse getUnit(@RequestBody DeliveryGetUnitServiceRequest req) {
		logger.info("entry  PackedController:getunit,user:" + req.getuName() + "  input parameters:"
				+ JSON.toJSONString(req));
		Long begin = new Date().getTime();
		DeliveryGetUnitServiceResponse res = new DeliveryGetUnitServiceResponse();
		int index = Utils.getIndexOfWarehouse(warehouseService, req.getWarehouseId());
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_CHECK_DELIVERY, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit PackedController:getunit,user:" + req.getuName() + " output parameters:"
					+ JSON.toJSONString(res));
			return res;
		}
		Long sapStart = new Date().getTime();
		JCoParameterList input = function.getImportParameterList();
		input.setValue("LGNUM", req.getWarehouseId());
		input.setValue("UNAME", req.getuName());
		JCoParameterList inputParam = function.getTableParameterList();
		List<Delivery> ptvbelnlist = req.getPtvbelnlist();
		JCoTable jcoTable = inputParam.getTable("PTVBELN");
		for (Delivery delivery : ptvbelnlist) {
			jcoTable.appendRow();
			jcoTable.setValue("VBELN", delivery.getVbeln());
		}

		RfcManager.execute(function, index);
		String result = function.getExportParameterList().getString("ZFLAG");
		String errMsg = function.getExportParameterList().getString("ZMESS");
		Long sapEnd = new Date().getTime();
		logger.info("entry PackedController:getunit,user:" + req.getuName() + "output parameters:ZFLAG=" + result
				+ ",ZMESS=" + errMsg + ",sap costs " + (sapEnd - sapStart) + " ms");
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {
			JCoParameterList outputParam = function.getTableParameterList();
			JCoTable jcoTable_to = outputParam.getTable("PTVBELN_TO");
			List<DeliveryUnit> unitList = new ArrayList<DeliveryUnit>();
			if (!jcoTable_to.isEmpty()) {
				do {
					DeliveryUnit deliveryUnit = new DeliveryUnit();
					deliveryUnit.setTanum((String) jcoTable_to.getValue("TANUM"));
					deliveryUnit.setMenge(jcoTable_to.getInt("MENGE"));
					deliveryUnit.setVbeln((String) jcoTable_to.getValue("VBELN"));
					deliveryUnit.setMatnr((String) jcoTable_to.getValue("MATNR"));
					unitList.add(deliveryUnit);
				} while (jcoTable_to.nextRow());
				res.setUnitList(unitList);
				res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
			}
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		Long end = new Date().getTime();
		logger.info("exit PackedController:getunit,user:" + req.getuName() + "output parameters:"
				+ JSON.toJSONString(res) + ",service costs " + (end - begin) + " ms");
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/newcheckunit", method = { RequestMethod.POST })
	public BaseResponse checkUnit(@RequestBody DeliveryCheckUnitServiceRequest req) {
		logger.info("entry  PackedController:newcheckunit,user:" + req.getuName() + "  input parameters:"
				+ JSON.toJSONString(req));
		Long begin = new Date().getTime();
		BaseResponse res = new BaseResponse();
		int index = Utils.getIndexOfWarehouse(warehouseService, req.getWarehouseId());
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_CONFIRM_DELIVERY, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit PackedController:newcheckunit,user:" + req.getuName() + " output parameters:"
					+ JSON.toJSONString(res));
			return res;
		}
		Long sapStart = new Date().getTime();
		JCoParameterList input = function.getImportParameterList();
		input.setValue("LGNUM", req.getWarehouseId());
		input.setValue("UNAME", req.getuName());
		JCoParameterList inputParam = function.getTableParameterList();
		List<DeliveryUnit> unitList = req.getUnitList();
		JCoTable jcoTable = inputParam.getTable("PTVBELN_TO");
		for (DeliveryUnit deliveryUnit : unitList) {
			jcoTable.appendRow();
			jcoTable.setValue("VBELN", deliveryUnit.getVbeln());
			jcoTable.setValue("TANUM", deliveryUnit.getTanum());
			jcoTable.setValue("MATNR", deliveryUnit.getMatnr());
			jcoTable.setValue("MENGE", deliveryUnit.getAmount());
		}

		RfcManager.execute(function, index);
		String result = function.getExportParameterList().getString("ZFLAG");
		String errMsg = function.getExportParameterList().getString("ZMESS");
		Long sapEnd = new Date().getTime();
		logger.info("entry PackedController:newcheckunit,user:" + req.getuName() + "output parameters:ZFLAG=" + result
				+ ",ZMESS=" + errMsg + ",sap costs " + (sapEnd - sapStart) + " ms");
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		Long end = new Date().getTime();
		logger.info("exit PackedController:newcheckunit,user:" + req.getuName() + "output parameters:"
				+ JSON.toJSONString(res) + ",service costs " + (end - begin) + " ms");
		return res;
	}
	
}
