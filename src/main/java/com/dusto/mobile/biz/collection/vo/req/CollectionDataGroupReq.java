package com.dusto.mobile.biz.collection.vo.req;

import java.util.List;

import com.dusto.mobile.common.vo.BaseRequest;

/**
 * @author lan
 * @date   2017年4月20日
 */
public class CollectionDataGroupReq extends BaseRequest {

	/**
	 * 双数据采集
	 */
	private static final long serialVersionUID = -7129344729384571357L;
	
	private String uname = "";
	private List<String> ztm1list;
	private List<String> ztm2list;
	
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public List<String> getZtm1list() {
		return ztm1list;
	}
	public void setZtm1list(List<String> ztm1list) {
		this.ztm1list = ztm1list;
	}
	public List<String> getZtm2list() {
		return ztm2list;
	}
	public void setZtm2list(List<String> ztm2list) {
		this.ztm2list = ztm2list;
	}

}
