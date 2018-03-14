package com.dusto.mobile.biz.warehouse.vo.res;

import java.io.Serializable;

import com.dusto.mobile.common.vo.BaseResponse;

public class ZxhListRes extends BaseResponse implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5956796887705274791L;
	private String result = "0";
	
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
}
