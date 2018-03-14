package com.dusto.mobile.biz.warehouse.vo.req;

import java.io.Serializable;

import com.dusto.mobile.common.vo.BaseRequest;

public class PickServiceRequest extends BaseRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2527560512618653260L;
	
	private String ztp;
	private String uName;
	public String getZtp() {
		return ztp;
	}
	public void setZtp(String ztp) {
		this.ztp = ztp;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	

}
