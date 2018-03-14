package com.dusto.mobile.biz.collection.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dusto.mobile.biz.collection.vo.req.CollectionDataGroupReq;
import com.dusto.mobile.biz.collection.vo.req.CollectionDataSingleReq;
import com.dusto.mobile.biz.usercenter.service.WarehouseService;
import com.dusto.mobile.biz.warehouse.controller.TallyController;
import com.dusto.mobile.common.Utils;
import com.dusto.mobile.common.constant.DustoConstant;
import com.dusto.mobile.common.constant.SAPContant;
import com.dusto.mobile.common.sap.jco.RfcManager;
import com.dusto.mobile.common.vo.BaseResponse;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

/**
 * @author lan
 * @date   2017年4月20日
 */

@Controller
@RequestMapping("collection")
public class CollectionDataController {
	
	
	@Autowired
	private WarehouseService warehouseService;

	private Logger logger = Logger.getLogger(TallyController.class);

	
	/**
	 * 单数据采集
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/single", method = { RequestMethod.POST })
	public BaseResponse singleDataCollection(@RequestBody CollectionDataSingleReq req) {
		logger.info("entry singleDataCollection, input parameters:" + JSON.toJSONString(req));
		BaseResponse res = new BaseResponse();
		
		int index = Utils.getIndexOfWarehouse(warehouseService, req.getWarehouseId());
		if(index == -1){
			res.setReturnCode(DustoConstant.RESPONSE_SYS_ERROR);
			logger.info("exit singleDataCollection output parameters:" + JSON.toJSONString(res));
			return res;
		}
		
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_COLLECTION_SINGLE, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit singleDataCollection output parameters:" + JSON.toJSONString(res));
			return res;
		}
		
		JCoParameterList input = function.getImportParameterList();
		input.setValue("LGNUM", req.getWarehouseId());
		input.setValue("UNAME", req.getUname());
		
		JCoParameterList inputParam = function.getTableParameterList();
		List<String> ztmList = new ArrayList<String>();
		if (!req.getZtmlist().isEmpty()) {
			JCoTable jcoTable = inputParam.getTable("T_ZTM");
			for (String ztm1 : req.getZtmlist()) {
				jcoTable.appendRow();
				jcoTable.setValue("ZTM1", ztm1);
				ztmList.add(ztm1);
			}
		}
		
		int size = ztmList.size();
		logger.info("inner singleDataCollection output parameters:" + "ztmsize:" + size );
		
		
		RfcManager.execute(function, index);
		String result = function.getExportParameterList().getString("ZFLAG");
		String errMsg = function.getExportParameterList().getString("ZMESS");
		logger.info("entry singleDataCollection output parameters:ZFLAG=" + result + ",ZMESS=" + errMsg);
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		logger.info("exit singleDataCollection output parameters:" + JSON.toJSONString(res));
		return res;
	}
	
	
	/**
	 * 双数据采集
	 * @param req
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/group", method = { RequestMethod.POST })
	public BaseResponse groupDataCollection(@RequestBody CollectionDataGroupReq req) {
		logger.info("entry GroupDataCollection, input parameters:" + JSON.toJSONString(req));
		BaseResponse res = new BaseResponse();
		
		int index = Utils.getIndexOfWarehouse(warehouseService, req.getWarehouseId());
		if(index == -1){
			res.setReturnCode(DustoConstant.RESPONSE_SYS_ERROR);
			logger.info("exit GroupDataCollection output parameters:" + JSON.toJSONString(res));
			return res;
		}
		
		JCoFunction function = RfcManager.getFunction(SAPContant.SAP_FUNCTION_COLLECTION_GROUP, index);
		if (function == null) {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_NO_FUNCTION);
			logger.info("exit GroupDataCollection output parameters:" + JSON.toJSONString(res));
			return res;
		}
		
		JCoParameterList input = function.getImportParameterList();
		input.setValue("LGNUM", req.getWarehouseId());
		input.setValue("UNAME", req.getUname());
		
		JCoParameterList inputParam = function.getTableParameterList();
		//List<String> ztm1List = new ArrayList<String>();
		//List<String> ztm2List = new ArrayList<String>();
		
		int size1 = 0;
		int size2 = 0;
		if(!req.getZtm1list().isEmpty()){
			size1  = req.getZtm1list().size();
		}
		
		if(!req.getZtm2list().isEmpty()){
			size2  = req.getZtm2list().size();
		}
		
		JCoTable jcoTable = inputParam.getTable("T_ZTM");
		if (size1 >= size2){
			//列表1个数肯定比列表2多，所以先以列表2的个数进行取值。
			for(int i=0 ; i< size2; i++){
				jcoTable.appendRow();
				String ztm1 = req.getZtm1list().get(i);
				jcoTable.setValue("ZTM1", ztm1);
				//ztm1List.add(ztm1);
				String ztm2 = req.getZtm2list().get(i);
				jcoTable.setValue("ZTM2", ztm2);
				//ztm2List.add(ztm2);	
			}
			
			//循环后，判断列表1是否存在最后一个单数据,如果存在则再加一行
			if(size1 > size2){
				jcoTable.appendRow();
				String ztm1 = req.getZtm1list().get(size1-1);
				jcoTable.setValue("ZTM1", ztm1);
				//ztm1List.add(ztm1);
			}
		}else{ //第一列肯定是有数据，并且大于第二列的，否则应该是客户端传输时，出现问题。
			res.setReturnCode(DustoConstant.RESPONSE_PARAM_ERROR);
			logger.info("exit GroupDataCollection output parameters:" + JSON.toJSONString(res));
			return res;
		}
		
		logger.info("inner GroupDataCollection output parameters:" + "ztm1size:" + size1 + "; ztm2size:" + size2);
		
		RfcManager.execute(function, index);
		String result = function.getExportParameterList().getString("ZFLAG");
		String errMsg = function.getExportParameterList().getString("ZMESS");
		logger.info("entry GroupDataCollection output parameters:ZFLAG=" + result + ",ZMESS=" + errMsg);
		if (SAPContant.SAP_FUNCTION_RES_SUCCESS.equalsIgnoreCase(result)) {
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SAP_ERROR);
			res.setErrorMsg(errMsg);
		}
		logger.info("exit GroupDataCollection output parameters:" + JSON.toJSONString(res));
		return res;
	}

}
