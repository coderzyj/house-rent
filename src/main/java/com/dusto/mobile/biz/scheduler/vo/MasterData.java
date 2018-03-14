package com.dusto.mobile.biz.scheduler.vo;

import java.io.Serializable;

import com.dusto.mobile.common.constant.DustoConstant;

public class MasterData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4993339635816163969L;

	private String zxh;
	private String matnr;
	private String color;
	private String zpeim;
	private String sapsys;
	private String flg = DustoConstant.ZXH_STATUS_NO_BINDING; // 0
																// 表示已经绑定,1表示没有绑定。
	private String ztp;

	public String getZxh() {
		return zxh;
	}

	public void setZxh(String zxh) {
		this.zxh = zxh;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getZpeim() {
		return zpeim;
	}

	public void setZpeim(String zpeim) {
		this.zpeim = zpeim;
	}

	public String getSapsys() {
		return sapsys;
	}

	public void setSapsys(String sapsys) {
		this.sapsys = sapsys;
	}

	public String getFlg() {
		return flg;
	}

	public void setFlg(String flg) {
		this.flg = flg;
	}

	public String getZtp() {
		return ztp;
	}

	public void setZtp(String ztp) {
		this.ztp = ztp;
	}

	public String getSKUCode() {
		return this.matnr + this.color + this.zpeim;
	}

}
