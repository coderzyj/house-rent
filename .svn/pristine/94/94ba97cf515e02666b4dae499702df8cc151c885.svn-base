package com.dusto.mobile.common.sap.test;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

import com.dusto.mobile.common.sap.conn.SAPConn;

public class CheckSnFromSAP {
	public static void main(String[] args) {  
        JCoFunction function = null;  
        JCoDestination destination = SAPConn.connect();  
        String result="";//调用接口返回状态  
        String message="";//调用接口返回信息  
        try {  
            //调用ZRFC_GET_REMAIN_SUM函数  
            function = destination.getRepository().getFunction("ZFMPOS_SN_CHECK");  
            JCoParameterList input = function.getImportParameterList();  
            //发出扫码仓库  
            input.setValue("ZSNWERKS", "1000");  
            //发出扫码库位  
            input.setValue("ZSNLGORT", "0001");  
            //采购凭证号  
            input.setValue("EBELN", "1");  
              
            //获取传入表参数SN_ITEM  
            JCoTable SN_ITEM = function.getTableParameterList().getTable("SN_ITEM");  
            SN_ITEM.appendRow();//增加一行  
            //给表参数中的字段赋值，此处测试，就随便传两个值进去  
            //商品编码  
            SN_ITEM.setValue("MATNR", "1");  
            //商品序列号  
            SN_ITEM.setValue("ZSERIAL", "1");  
            function.execute(destination);  
            result= function.getExportParameterList().getString("RESULT");//调用接口返回状态  
            message= function.getExportParameterList().getString("MESSAGE");//调用接口返回信息  
            System.out.println("调用返回状态--->"+result+";调用返回信息--->"+message);  
            SN_ITEM.clear();//清空本次条件，如果要继续传入值去或者还要循环，那得将之前的条件清空  
        }catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

}
