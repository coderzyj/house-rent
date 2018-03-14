package com.dusto.mobile.biz.warehouse.vo.req;

import java.io.Serializable;

import com.dusto.mobile.common.vo.BaseRequest;


/**
 * @author lan
 * @date   2017年4月12日
 */


public class TrayTotalServiceRequest extends BaseRequest implements  Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8102842769665135669L;
	/**
	 * 托盘区理货 整托
	 */
	private String ztpn = ""; //目的托盘号
	private String uname = "";//用户名
	private String ytpn="";  //源托盘号
	
	public String getZtpn() {
		return ztpn;
	}
	public void setZtpn(String ztpn) {
		this.ztpn = ztpn;
	}	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getYtpn() {
		return ytpn;
	}
	public void setYtpn(String ytpn) {
		this.ytpn = ytpn;
	}
	
	
}
