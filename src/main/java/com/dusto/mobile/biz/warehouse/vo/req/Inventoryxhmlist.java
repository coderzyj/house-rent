package com.dusto.mobile.biz.warehouse.vo.req;

import java.io.Serializable;

public class Inventoryxhmlist implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4600588814329763616L;
	
	private String matnr;
	private int	menge;
	public String getMatnr() {
		return matnr;
	}
	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}
	public int getMenge() {
		return menge;
	}
	public void setMenge(int menge) {
		this.menge = menge;
	}
	
}
