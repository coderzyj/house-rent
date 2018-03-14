package com.dusto.mobile.common.sap.test;

import java.util.ArrayList;
import java.util.List;

import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

import com.dusto.mobile.common.sap.jco.RfcManager;

public class JCOTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		getUser();
	}
	public static List<User> getUser() {  
		  
        JCoFunction function = RfcManager.getFunction("FUNCION_USER",0);  
  
        RfcManager.execute(function,0);  
        JCoParameterList outputParam = function.getTableParameterList();  
        JCoTable bt = outputParam.getTable("TABLEOUT");  
        List<User> list = new ArrayList<User>();  
        for (int i = 0; i < bt.getNumRows(); i++) {  
            bt.setRow(i);  
  
            User user = new User();  
            user.setUserName(bt.getString("USER_NAME"));  
            list.add(user);  
        }  
        return list;  
    }  
	
	public static void createVoucherFromSAP(){
		JCoFunction function = RfcManager.getFunction("ZRFC_OA_ACC_DOCUMENT",0);
		JCoTable T_ACCDOCUMENT = function.getTableParameterList().getTable("T_ACCDOCUMENT");  
        T_ACCDOCUMENT.appendRow();//增加一行  
        //给表参数中的字段赋值，此处测试，就随便传两个值进去  
        T_ACCDOCUMENT.setValue("BUKRS", "1000");  
        T_ACCDOCUMENT.setValue("BLART", "SA");  
        RfcManager.execute(function,0);  
        String state= function.getExportParameterList().getString("E_STATUS");//调用接口返回状态  
        String message= function.getExportParameterList().getString("E_MESSAGE");//调用接口返回信息  
        System.out.println("调用返回状态--->"+state+";调用返回信息--->"+message);  
        T_ACCDOCUMENT.firstRow();//获取第一行的对象(此处看sap端如何写的，如果返回的可能有多行，那得循环)  
        String belnr=T_ACCDOCUMENT.getString("BELNR");  
        String buzei=T_ACCDOCUMENT.getString("BUZEI");  
        System.out.println("会计凭证号--->"+belnr+";会计凭证行项目--->"+buzei);  
        T_ACCDOCUMENT.clear();//清空本次条件，如果要继续传入值去或者还要循环，那得将之前的条件清空  
		
	}

}
