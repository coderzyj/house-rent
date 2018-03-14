package com.dusto.mobile.biz.warehouse.vo.req;

import java.io.Serializable;

import com.dusto.mobile.common.vo.BaseRequest;

public class GroundingServiceRequest extends BaseRequest implements Serializable {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2512986791720963617L;
	private String ztp;
	private String lgpla;
	private String uName;
	
	public String getZtp() {
		return ztp;
	}
	public void setZtp(String ztp) {
		this.ztp = ztp;
	}
	public String getLgpla() {
		return lgpla;
	}
	public void setLgpla(String lgpla) {
		this.lgpla = lgpla;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}

	
}
