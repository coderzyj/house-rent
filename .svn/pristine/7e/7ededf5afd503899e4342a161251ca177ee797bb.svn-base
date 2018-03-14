package com.dusto.mobile.common.sap.test;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;

import com.dusto.mobile.common.sap.conn.SAPConn;

public class GetPoFromSAP {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JCoFunction function = null;  
        JCoDestination destination = SAPConn.connect();  
        String result="";//调用接口返回状态  
        String message="";//调用接口返回信息  
        try {  
            //调用ZRFC_GET_REMAIN_SUM函数  
            function = destination.getRepository().getFunction("ZFMPOS_PO_QUERY");  
            JCoParameterList input = function.getImportParameterList();  
            //采购凭证号  
            input.setValue("EBELN", "4500004135");  
            //单据类型  
            input.setValue("BSART", "TR");  
            //制单日期从  
            input.setValue("AEDATF", "");  
            //制单日期到  
            input.setValue("AEDATT", "");  
            //出入库标识  
            input.setValue("INOUT", "I");  
            //地点库位权限值  
            input.setValue("AUTHORITY", "");  
              
            function.execute(destination);  
            result= function.getExportParameterList().getString("RESULT");//调用接口返回状态  
            message= function.getExportParameterList().getString("MESSAGE");//调用接口返回信息  
              
            if(result.equals("E")){  
                System.out.println("调用返回状态--->"+result+";调用返回信息--->"+message);  
                return;  
            }else{  
                System.out.println("调用返回状态--->"+result+";调用返回信息--->"+message);  
                JCoParameterList tblexport = function.getTableParameterList();  
                //JCoParameterList tblexport = function.getTableParameterList().getTable("QUERY_H");  
                String msg = tblexport.toXML();  
                System.out.println("调用返回表XML--->"+msg);  
            }  
        }catch (Exception e) {  
            e.printStackTrace();  
        }  
	}

}
