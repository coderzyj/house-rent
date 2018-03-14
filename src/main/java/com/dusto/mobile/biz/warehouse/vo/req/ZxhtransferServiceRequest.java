package com.dusto.mobile.biz.warehouse.vo.req;

import java.io.Serializable;
import java.util.List;

import com.dusto.mobile.common.vo.BaseRequest;

public class ZxhtransferServiceRequest extends BaseRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2718376029018510568L;
	private String ztp;
	private String ztpn;
	private String vlpla;
	private String nlpla;
	private String uname;
	private List<BindingBox> zxhlist;
	public String getZtp() {
		return ztp;
	}
	public void setZtp(String ztp) {
		this.ztp = ztp;
	}
	public String getZtpn() {
		return ztpn;
	}
	public void setZtpn(String ztpn) {
		this.ztpn = ztpn;
	}
	public String getVlpla() {
		return vlpla;
	}
	public void setVlpla(String vlpla) {
		this.vlpla = vlpla;
	}
	public String getNlpla() {
		return nlpla;
	}
	public void setNlpla(String nlpla) {
		this.nlpla = nlpla;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String umane) {
		this.uname = umane;
	}
	public List<BindingBox> getZxhlist() {
		return zxhlist;
	}
	public void setZxhlist(List<BindingBox> zxhlist) {
		this.zxhlist = zxhlist;
	}
	
}
