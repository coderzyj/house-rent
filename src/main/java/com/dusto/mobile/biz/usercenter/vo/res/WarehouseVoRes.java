package com.dusto.mobile.biz.usercenter.vo.res;

import java.io.Serializable;

import com.dusto.mobile.biz.usercenter.entity.Warehouse;
import com.dusto.mobile.common.vo.BaseResponse;

public class WarehouseVoRes extends BaseResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4389288395704759555L;
	private Warehouse result;

	public Warehouse getResult() {
		return result;
	}

	public void setResult(Warehouse result) {
		this.result = result;
	}

}
