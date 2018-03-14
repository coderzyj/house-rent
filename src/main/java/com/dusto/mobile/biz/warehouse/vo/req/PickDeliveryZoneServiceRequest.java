package com.dusto.mobile.biz.warehouse.vo.req;

import java.io.Serializable;

import com.dusto.mobile.common.vo.BaseRequest;

public class PickDeliveryZoneServiceRequest extends BaseRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -297236852013456040L;
	private String ztp; 
	private String uName;
	private String lgpla;
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
	public String getLgpla() {
		return lgpla;
	}
	public void setLgpla(String lgpla) {
		this.lgpla = lgpla;
	}
	
}
