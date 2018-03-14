package com.dusto.mobile.biz.collection.vo.req;

import java.util.List;

import com.dusto.mobile.common.vo.BaseRequest;

/**
 * @author lan
 * @date   2017年4月20日
 */
public class CollectionDataSingleReq extends BaseRequest{

	/**
	 * 单数据采集
	 */
	private static final long serialVersionUID = -8603682045542682274L;
	
	
	private String uname = "";
	private List<String> ztmlist;

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public List<String> getZtmlist() {
		return ztmlist;
	}

	public void setZtmlist(List<String> ztmlist) {
		this.ztmlist = ztmlist;
	}

}
