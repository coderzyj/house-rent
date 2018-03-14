package com.dusto.mobile.biz.warehouse.controller;

import java.math.BigDecimal;
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
import com.dusto.mobile.biz.usercenter.service.WarehouseService;
import com.dusto.mobile.biz.warehouse.vo.req.GroundingServiceRequest;
import com.dusto.mobile.biz.warehouse.vo.req.RdcStockCheckServiceRequest;
import com.dusto.mobile.biz.warehouse.vo.res.DeliveryUnit;
import com.dusto.mobile.biz.warehouse.vo.res.PositionServiceResponse;
import com.dusto.mobile.biz.warehouse.vo.res.StockcheckServiceResponse;
import com.dusto.mobile.common.Utils;
import com.dusto.mobile.common.constant.DustoConstant;
import com.dusto.mobile.common.constant.SAPContant;
import com.dusto.mobile.common.sap.jco.RfcManager;
import com.dusto.mobile.common.vo.BaseResponse;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

/**
 * 托盘上架
 * 
 * @author Kevin
 *
 */
@Controller
@RequestMapping("stack")
public class StockController {

	private Logger logger = Logger.getLogger(StockController.class);

	@Autowired
	private WarehouseService warehouseService;

	@Autowired
	private MasterDataService masterDataService;
	
	@ResponseBody
	@RequestMapping(value = "/submit", method = { RequestMethod.POST })
	public BaseResponse stacking(@RequestBody GroundingServiceRequest req) {
		logger.info("entry StockController:stacking, input parameters:" + JSON.toJSONString(req));
		Long begin = new Date().getTime();
		BaseResponse res = new BaseResponse();
		int index = Utils.getIndexOfWarehouse(warehouseService, req.getWarehouseId());
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_GROUNDING, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit StockController:stacking output parameters:" + JSON.toJSONString(res));
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
		logger.info("entry StockController:stacking output parameters:ZFLAG=" + result + ",ZMESS=" + errMsg
				+ ",sap costs " + (sapEnd - sapStart) + " ms");
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
			int ret = masterDataService.updateMasterdataFlagByZTPToUnbinding(req.getZtp());
			if (ret == 0) {
				logger.error("unbinding failed, please unbind manually, ztp =" + req.getZtp());
			}
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		Long end = new Date().getTime();
		logger.info("exit StockController:stacking output parameters:" + JSON.toJSONString(res) + ", service costs "
				+ (end - begin) + " ms");
		return res;
	}

	/**
	 * 获取推荐仓位
	 * 
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/positon/{warehouseId}/{ztp}", method = { RequestMethod.GET })
	public PositionServiceResponse getPosition(@PathVariable("warehouseId") String warehouseId,
			@PathVariable("ztp") String ztp) {
		logger.info("entry StockController:getPosition, input parameters: warehouseId=" + warehouseId + ",ztp=" + ztp);
		Long begin = new Date().getTime();
		PositionServiceResponse res = new PositionServiceResponse();
		int index = Utils.getIndexOfWarehouse(warehouseService, warehouseId);
		if (index == -1) {
			res.setReturnCode(DustoConstant.RESPONSE_SYS_ERROR);
			logger.info("exit StockController:getPosition output parameters:" + JSON.toJSONString(res));
			return res;
		}
		// logger.info("SAP system index is " + index);
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_POSITION, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit StockController:getPosition output parameters:" + JSON.toJSONString(res));
			return res;
		}
		Long sapStart = new Date().getTime();
		JCoParameterList input = function.getImportParameterList();
		input.setValue("LGNUM", warehouseId);
		input.setValue("ZTP", ztp);
		RfcManager.execute(function, index);
		String result = function.getExportParameterList().getString("ZFLAG");
		String errMsg = function.getExportParameterList().getString("ZMESS");
		Long sapEnd = new Date().getTime();
		logger.info("SAP StockController:getPosition output parameters:ZFLAG=" + result + ",ZMESS=" + errMsg
				+ ",sap costs " + (sapEnd - sapStart) + " ms");
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {
			String lgpla = function.getExportParameterList().getString("LGPLA");
			String matnr = function.getExportParameterList().getString("MATNR");
			int menge = function.getExportParameterList().getInt("MENGE");
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
			res.setLgpla(lgpla);
			res.setMatnr(matnr);
			res.setMenge(menge);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		Long end = new Date().getTime();
		logger.info("exit StockController:getPosition output parameters:" + JSON.toJSONString(res) + ", service costs "
				+ (end - begin) + " ms");
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value = "/getpositon", method = { RequestMethod.POST })
	public StockcheckServiceResponse getPositonByXH(@RequestBody RdcStockCheckServiceRequest req) {
		logger.info("entry  StockController:getPositonByXH,user:" + req.getuName() + "  input parameters:"
				+ JSON.toJSONString(req));
		Long begin = new Date().getTime();
		StockcheckServiceResponse res = new StockcheckServiceResponse();
		int index = Utils.getIndexOfWarehouse(warehouseService, req.getWarehouseId());
		if (index == -1) {
			res.setReturnCode(DustoConstant.RESPONSE_SYS_ERROR);
			logger.info("exit StockController:getPositonByXH,user:" + req.getuName() + " output parameters:"
					+ JSON.toJSONString(res));
			return res;
		}
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_STOCKCHECK, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit StockController:getPositonByXH,user:" + req.getuName() + " output parameters:" + JSON.toJSONString(res));
			return res;
		}
		Long sapStart = new Date().getTime();
		JCoParameterList input = function.getImportParameterList();
		input.setValue("LGNUM", req.getWarehouseId());
		input.setValue("ZXH", req.getZxh());
		input.setValue("MATNR", req.getMatnr());
		RfcManager.execute(function, index);
		String result = function.getExportParameterList().getString("ZFLAG");
		String errMsg = function.getExportParameterList().getString("ZMESS");
		Long sapEnd = new Date().getTime();
		logger.info("entry StockController:getPositonByXH,user:" + req.getuName() + " output parameters:ZFLAG=" + result + ",ZMESS=" + errMsg
				+ ",sap costs " + (sapEnd - sapStart) + " ms");
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {
			JCoParameterList outputParam = function.getTableParameterList();
			JCoTable jcoTable = outputParam.getTable("PTLQUA");
			List<StockcheckServiceResponse> Stocklist = new ArrayList<StockcheckServiceResponse>();
			if (!jcoTable.isEmpty()) {
				do {
					StockcheckServiceResponse stock = new StockcheckServiceResponse();
					stock.setLgpla((String) jcoTable.getValue("LGPLA"));
					stock.setZtp((String) jcoTable.getValue("ZTP"));
					stock.setMatnr((String) jcoTable.getValue("MATNR"));
					stock.setGesme((BigDecimal) jcoTable.getValue("GESME"));
					stock.setMeins((String) jcoTable.getValue("MEINS"));
					Stocklist.add(stock);
				} while (jcoTable.nextRow());
			}
			res.setStocklist(Stocklist);
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		Long end = new Date().getTime();
		logger.info("exit StockController:getPositonByXH,user:" + req.getuName() + " output parameters:" + JSON.toJSONString(res) + ", service costs "
				+ (end - begin) + " ms");
		return res;
	}
}
