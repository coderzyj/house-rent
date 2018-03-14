package com.dusto.mobile.biz.warehouse.vo.res;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.dusto.mobile.common.vo.BaseResponse;

public class StockcheckServiceResponse extends BaseResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5694622555726103663L;
	private String lgpla;    //仓位
	private String ztp;      //托盘号
	private String matnr;    //物料号
	private BigDecimal gesme;       //总计数量
	private String meins;    //基本计量单位
	private List<StockcheckServiceResponse> Stocklist;
	
	
	public List<StockcheckServiceResponse> getStocklist() {
		return Stocklist;
	}
	public void setStocklist(List<StockcheckServiceResponse> stocklist) {
		Stocklist = stocklist;
	}
	public String getLgpla() {
		return lgpla;
	}
	public void setLgpla(String lgpla) {
		this.lgpla = lgpla;
	}
	public String getZtp() {
		return ztp;
	}
	public void setZtp(String ztp) {
		this.ztp = ztp;
	}
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public BigDecimal getGesme() {
		return gesme;
	}
	public void setGesme(BigDecimal gesme) {
		this.gesme = gesme;
	}
	public String getMeins() {
		return meins;
	}
	public void setMeins(String meins) {
		this.meins = meins;
	}
}
