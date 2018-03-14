package com.dusto.mobile.biz.warehouse.controller;

import java.math.BigDecimal;
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
import com.dusto.mobile.biz.scheduler.service.MasterDataService;
import com.dusto.mobile.biz.usercenter.service.WarehouseService;
import com.dusto.mobile.biz.warehouse.vo.req.BcData;
import com.dusto.mobile.biz.warehouse.vo.req.PickDeliveryZoneServiceRequest;
import com.dusto.mobile.biz.warehouse.vo.req.PickServiceRequest;
import com.dusto.mobile.biz.warehouse.vo.req.PickTempZoneServiceRequest;
import com.dusto.mobile.biz.warehouse.vo.req.RdcDistributeServiceRequest;
import com.dusto.mobile.biz.warehouse.vo.req.RdcPickCommitServiceRequest;
import com.dusto.mobile.biz.warehouse.vo.req.RdcPickServiceRequest;
import com.dusto.mobile.biz.warehouse.vo.res.DtData;
import com.dusto.mobile.biz.warehouse.vo.res.PickData;
import com.dusto.mobile.biz.warehouse.vo.res.PickServiceResponse;
import com.dusto.mobile.biz.warehouse.vo.res.RdcDistributeServiceResponse;
import com.dusto.mobile.biz.warehouse.vo.res.RdcPickServiceResponse;
import com.dusto.mobile.common.Utils;
import com.dusto.mobile.common.constant.DustoConstant;
import com.dusto.mobile.common.constant.SAPContant;
import com.dusto.mobile.common.sap.jco.RfcManager;
import com.dusto.mobile.common.vo.BaseResponse;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

/**
 * 拣货
 * 
 * @author Kevin
 *
 */
@Controller
@RequestMapping("pick")
public class PickController {
	private Logger logger = Logger.getLogger(StockController.class);

	@Autowired
	private WarehouseService warehouseService;

	@Autowired
	private MasterDataService masterDataService;

	@ResponseBody
	@RequestMapping(value = "/submit", method = { RequestMethod.POST })
	public PickServiceResponse submit(@RequestBody PickServiceRequest req) {
		logger.info("entry PickController:submit,user:"+req.getuName()+"  input parameters:" + JSON.toJSONString(req));
		Long begin = new Date().getTime();
		PickServiceResponse res = new PickServiceResponse();
		int index = Utils.getIndexOfWarehouse(warehouseService, req.getWarehouseId());
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_CDC_PICK, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit PickController:submit,user:"+req.getuName()+"  output parameters:" + JSON.toJSONString(res));
			return res;
		}
		Long sapStart = new Date().getTime();
		JCoParameterList input = function.getImportParameterList();
		input.setValue("LGNUM", req.getWarehouseId());
		input.setValue("ZTP", req.getZtp());
		input.setValue("UNAME", req.getuName());
		RfcManager.execute(function, index);
		String result = function.getExportParameterList().getString("ZFLAG");
		String errMsg = function.getExportParameterList().getString("ZMESS");
		Long sapEnd = new Date().getTime();
		logger.info("exit PickController:submit,user:"+req.getuName()+"  output parameters:ZFLAG=" + result + ",ZMESS=" + errMsg + ",sap costs "
				+ (sapEnd - sapStart) + " ms");
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {
			String type = function.getExportParameterList().getString("ZTPYE");
			JCoTable jcoTable = function.getTableParameterList().getTable("T_OUPUT");
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
			res.setZtype(type);
			if (!jcoTable.isEmpty()) {
				do {
					PickData data = new PickData();
					BigDecimal lfimg = (BigDecimal) jcoTable.getValue("LFIMG");
					int lf = lfimg.intValue();
					// data.setLfimg((String) jcoTable.getString("LFIMG"));
					data.setLfimg(lf + "");
					data.setLgpla((String) jcoTable.getString("LGPLA"));
					data.setZrw((String) jcoTable.getString("ZRW"));
					logger.info("exit PickController:submit,user:"+req.getuName()+"  T_OUPUT :" + JSON.toJSONString(data));
					res.getPickDataList().add(data);
				} while (jcoTable.nextRow());
			}
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		Long end = new Date().getTime();
		logger.info("exit PickController:submit output,user:"+req.getuName()+"  parameters:" + JSON.toJSONString(res) + ", service costs "
				+ (end - begin) + " ms");
		return res;
	}

	/**
	 * 移动到暂存区
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/tempzone", method = { RequestMethod.POST })
	public BaseResponse moveToTempZone(@RequestBody PickTempZoneServiceRequest req) {
		logger.info("entry PickController:moveToTempZone,user:"+req.getuName()+"  input parameters:" + JSON.toJSONString(req));
		Long begin = new Date().getTime();
		BaseResponse res = new BaseResponse();
		int index = Utils.getIndexOfWarehouse(warehouseService, req.getWarehouseId());
		if (index == -1) {
			res.setReturnCode(DustoConstant.RESPONSE_SYS_ERROR);
			logger.info("exit PickController:moveToTempZone,user:"+req.getuName()+"  output parameters:" + JSON.toJSONString(res));
			return res;
		}
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_CDC_PICK_TO_TEMP, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit PickController:moveToTempZone,user:"+req.getuName()+"  output parameters:" + JSON.toJSONString(res));
			return res;
		}
		Long sapStart = new Date().getTime();
		JCoParameterList input = function.getImportParameterList();
		input.setValue("LGNUM", req.getWarehouseId());
		input.setValue("ZTP_OLD", req.getOldZtp());
		input.setValue("ZTP_NEW", req.getNewZtp());
		input.setValue("LFIMG", req.getLfimg());
		input.setValue("LGPLA", req.getLgpla());
		input.setValue("ZRW", req.getZrw());
		input.setValue("UNAME", req.getuName());
		input.setValue("ZTPYE", req.getZtpye());
		JCoParameterList inputParam = function.getTableParameterList();
		if (req.getBoxList() != null && !req.getBoxList().isEmpty()) {
			JCoTable jcoTable = inputParam.getTable("T_INPUT");
			for (String zxh : req.getBoxList()) {
				jcoTable.appendRow();
				jcoTable.setValue("ZXH", zxh);
			}
		}
		RfcManager.execute(function, index);
		String result = function.getExportParameterList().getString("ZFLAG");
		String errMsg = function.getExportParameterList().getString("ZMESS");
		Long sapEnd = new Date().getTime();
		logger.info("exit PickController:moveToTempZone,user:"+req.getuName()+"  output parameters:ZFLAG=" + result + ",ZMESS=" + errMsg
				+ ",sap costs " + (sapEnd - sapStart) + " ms");
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
			// 解除绑定关系
			masterDataService.updateMasterdataFlagByZTPToUnbinding(req.getOldZtp());
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		Long end = new Date().getTime();
		logger.info("exit PickController:moveToTempZone,user:"+req.getuName()+"  output parameters:" + JSON.toJSONString(res)
				+ ", service costs " + (end - begin) + " ms");
		return res;
	}

	/**
	 * 暂存区移动到发货区
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delivery", method = { RequestMethod.POST })
	public BaseResponse moveToDeliveryZone(@RequestBody PickDeliveryZoneServiceRequest req) {
		logger.info("entry PickController:moveToDeliveryZone,user:"+req.getuName()+"  input parameters:" + JSON.toJSONString(req));
		Long begin = new Date().getTime();
		BaseResponse res = new BaseResponse();
		int index = Utils.getIndexOfWarehouse(warehouseService, req.getWarehouseId());
		if (index == -1) {
			res.setReturnCode(DustoConstant.RESPONSE_SYS_ERROR);
			logger.info("exit PickController:moveToDeliveryZone,user:"+req.getuName()+"  output parameters:" + JSON.toJSONString(res));
			return res;
		}
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_CDC_PICK_TO_DELIVERY, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit PickController:moveToDeliveryZone,user:"+req.getuName()+"  output parameters:" + JSON.toJSONString(res));
			return res;
		}
		Long sapStart = new Date().getTime();
		JCoParameterList input = function.getImportParameterList();
		input.setValue("LGNUM", req.getWarehouseId());
		input.setValue("ZTP", req.getZtp());
		input.setValue("UNAME", req.getuName());
		input.setValue("LGPLA", req.getLgpla());

		RfcManager.execute(function, index);
		String result = function.getExportParameterList().getString("ZFLAG");
		String errMsg = function.getExportParameterList().getString("ZMESS");
		Long sapEnd = new Date().getTime();
		logger.info("entry PickController:moveToDeliveryZone,user:"+req.getuName()+"  output parameters:ZFLAG=" + result + ",ZMESS=" + errMsg
				+ ",sap costs " + (sapEnd - sapStart) + " ms");
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		Long end = new Date().getTime();
		logger.info("exit PickController:moveToDeliveryZone,user:"+req.getuName()+"  output parameters:" + JSON.toJSONString(res)
				+ ", service costs " + (end - begin) + " ms");
		return res;
	}

	@ResponseBody
	@RequestMapping(value = "/rdcGet", method = { RequestMethod.POST })
	public RdcPickServiceResponse rdcGet(@RequestBody RdcPickServiceRequest req) {
		logger.info("entry PickController:rdcGet,user:"+req.getuName()+"  input parameters:" + JSON.toJSONString(req));
		Long begin = new Date().getTime();
		RdcPickServiceResponse res = new RdcPickServiceResponse();
		int index = Utils.getIndexOfWarehouse(warehouseService, req.getWarehouseId());
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_RDC_PICK_GET, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit PickController:rdcGet,user:"+req.getuName()+"  output parameters:" + JSON.toJSONString(res));
			return res;
		}
		Long sapStart = new Date().getTime();
		JCoParameterList input = function.getImportParameterList();
		input.setValue("LGNUM", req.getWarehouseId());
		input.setValue("ZBC", req.getZbc());
		input.setValue("UNAME", req.getuName());
		RfcManager.execute(function, index);
		String result = function.getExportParameterList().getString("ZFLAG");
		String errMsg = function.getExportParameterList().getString("ZMESS");
		Long sapEnd = new Date().getTime();
		logger.info("exit PickController:rdcGet,user:"+req.getuName()+"  output parameters:ZFLAG=" + result + ",ZMESS=" + errMsg + ",sap costs "
				+ (sapEnd - sapStart) + " ms");
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {
			JCoTable jcoTable = function.getTableParameterList().getTable("PTZBC1");
			List<BcData> bcList = new ArrayList<BcData>();
			if (!jcoTable.isEmpty()) {
				do {
					BcData data = new BcData();
//					BigDecimal lfimg = (BigDecimal) jcoTable.getValue("LFIMG");
//					int lf = lfimg.intValue();	
					data.setLgpla((String) jcoTable.getString("LGPLA"));
					data.setMatnr((String) jcoTable.getString("MATNR"));
					data.setLgmng(jcoTable.getInt("LGMNG"));
					data.setAllLgmng(jcoTable.getInt("ALLGMNG"));
					logger.info("exit PickController:rdcGet,user:"+req.getuName()+"  PTZBC1 :" + JSON.toJSONString(data));
					bcList.add(data);
				} while (jcoTable.nextRow());
			}
			res.setBcList(bcList);
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		Long end = new Date().getTime();
		logger.info("exit PickController:rdcGet output,user:"+req.getuName()+"  parameters:" + JSON.toJSONString(res) + ", service costs "
				+ (end - begin) + " ms");
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value = "/rdcCommit", method = { RequestMethod.POST })
	public BaseResponse rdcCommit(@RequestBody RdcPickCommitServiceRequest req) {
		logger.info("entry PickController:rdcCommit,user:"+req.getuName()+"  input parameters:" + JSON.toJSONString(req));
		Long begin = new Date().getTime();
		BaseResponse res = new BaseResponse();
		int index = Utils.getIndexOfWarehouse(warehouseService, req.getWarehouseId());
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_RDC_PICK_COMMIT, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit PickController:rdcCommit,user:"+req.getuName()+"  output parameters:" + JSON.toJSONString(res));
			return res;
		}
		Long sapStart = new Date().getTime();
		
		JCoParameterList input = function.getImportParameterList();
		input.setValue("LGNUM", req.getWarehouseId());
		input.setValue("UNAME", req.getuName());
		input.setValue("ZBC", req.getZbc());
		input.setValue("ZTP", req.getZtp());
		input.setValue("ZEND", req.getZend());
		JCoParameterList inputParam = function.getTableParameterList();
		List<BcData>bcdatalist = req.getBcList();
		JCoTable jcoTable = inputParam.getTable("PTZBC1");
		for (BcData bd : bcdatalist) {
			jcoTable.appendRow();
			jcoTable.setValue("ALLGMNG", bd.getAllLgmng());
			jcoTable.setValue("LGPLA", bd.getLgpla());
			jcoTable.setValue("MATNR", bd.getMatnr());
		}
		
		RfcManager.execute(function, index);
		String result = function.getExportParameterList().getString("ZFLAG");
		String errMsg = function.getExportParameterList().getString("ZMESS");
		Long sapEnd = new Date().getTime();
		logger.info("exit PickController:rdcCommit,user:"+req.getuName()+"  output parameters:ZFLAG=" + result + ",ZMESS=" + errMsg + ",sap costs "
				+ (sapEnd - sapStart) + " ms");
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {		
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		Long end = new Date().getTime();
		logger.info("exit PickController:rdcCommit output,user:"+req.getuName()+"  parameters:" + JSON.toJSONString(res) + ", service costs "
				+ (end - begin) + " ms");
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value = "/distribute", method = { RequestMethod.POST })
	public RdcDistributeServiceResponse distribute(@RequestBody RdcDistributeServiceRequest req) {
		logger.info("entry PickController:distribute,user:"+req.getuName()+"  input parameters:" + JSON.toJSONString(req));
		Long begin = new Date().getTime();
		RdcDistributeServiceResponse res = new RdcDistributeServiceResponse();
		int index = Utils.getIndexOfWarehouse(warehouseService, req.getWarehouseId());
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_RDC_DISTRIBUTE, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit PickController:distribute,user:"+req.getuName()+"  output parameters:" + JSON.toJSONString(res));
			return res;
		}
		Long sapStart = new Date().getTime();
		JCoParameterList input = function.getImportParameterList();
		input.setValue("LGNUM", req.getWarehouseId());
		input.setValue("ZBC", req.getZbc());
		input.setValue("ZTP", req.getZtp());
		input.setValue("UNAME", req.getuName());
		RfcManager.execute(function, index);
		String result = function.getExportParameterList().getString("ZFLAG");
		String errMsg = function.getExportParameterList().getString("ZMESS");
		Long sapEnd = new Date().getTime();
		logger.info("exit PickController:distribute,user:"+req.getuName()+"  output parameters:ZFLAG=" + result + ",ZMESS=" + errMsg + ",sap costs "
				+ (sapEnd - sapStart) + " ms");
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {
			JCoTable jcoTable = function.getTableParameterList().getTable("PTZBC2");
			List<DtData> dtList = new ArrayList<DtData>();
			if (!jcoTable.isEmpty()) {
				do {
					DtData data = new DtData();
//					BigDecimal lfimg = (BigDecimal) jcoTable.getValue("LFIMG");
//					int lf = lfimg.intValue();	
					data.setWerks((String) jcoTable.getString("WERKS"));
					data.setMatnr((String) jcoTable.getString("MATNR"));
					data.setZkhls((String) jcoTable.getString("ZKHLS"));
					data.setMenge(jcoTable.getInt("MENGE"));
					logger.info("exit PickController:distribute,user:"+req.getuName()+"  PTZBC2 :" + JSON.toJSONString(data));
					dtList.add(data);
				} while (jcoTable.nextRow());
			}
			res.setDtList(dtList);
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		Long end = new Date().getTime();
		logger.info("exit PickController:distribute output,user:"+req.getuName()+"  parameters:" + JSON.toJSONString(res) + ", service costs "
				+ (end - begin) + " ms");
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value = "/distributeCommit", method = { RequestMethod.POST })
	public BaseResponse distributeCommit(@RequestBody RdcDistributeServiceRequest req) {
		logger.info("entry PickController:distributeCommit,user:"+req.getuName()+"  input parameters:" + JSON.toJSONString(req));
		Long begin = new Date().getTime();
		BaseResponse res = new BaseResponse();
		int index = Utils.getIndexOfWarehouse(warehouseService, req.getWarehouseId());
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_RDC_DISTRIBUTE_COMMIT, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit PickController:distributeCommit,user:"+req.getuName()+"  output parameters:" + JSON.toJSONString(res));
			return res;
		}
		Long sapStart = new Date().getTime();
		JCoParameterList input = function.getImportParameterList();
		input.setValue("LGNUM", req.getWarehouseId());
		input.setValue("ZBC", req.getZbc());
		input.setValue("ZTP", req.getZtp());
		input.setValue("UNAME", req.getuName());
		RfcManager.execute(function, index);
		String result = function.getExportParameterList().getString("ZFLAG");
		String errMsg = function.getExportParameterList().getString("ZMESS");
		Long sapEnd = new Date().getTime();
		logger.info("exit PickController:distributeCommit,user:"+req.getuName()+"  output parameters:ZFLAG=" + result + ",ZMESS=" + errMsg + ",sap costs "
				+ (sapEnd - sapStart) + " ms");
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		Long end = new Date().getTime();
		logger.info("exit PickController:distributeCommit output,user:"+req.getuName()+"  parameters:" + JSON.toJSONString(res) + ", service costs "
				+ (end - begin) + " ms");
		return res;
	}
	
}
