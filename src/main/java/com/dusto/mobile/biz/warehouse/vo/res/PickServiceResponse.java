package com.dusto.mobile.biz.warehouse.vo.res;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dusto.mobile.common.vo.BaseResponse;

public class PickServiceResponse extends BaseResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4846649441455125308L;
	private String ztype;
	private List<PickData> pickDataList = new ArrayList<PickData>();
	public String getZtype() {
		return ztype;
	}
	public void setZtype(String ztype) {
		this.ztype = ztype;
	}
	public List<PickData> getPickDataList() {
		return pickDataList;
	}
	public void setPickDataList(List<PickData> pickDataList) {
		this.pickDataList = pickDataList;
	}
	
}
