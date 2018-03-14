package com.dusto.mobile.biz.warehouse.vo.res;

import java.io.Serializable;
import java.util.List;

import com.dusto.mobile.biz.warehouse.vo.req.BindingBox;
import com.dusto.mobile.common.vo.BaseResponse;

public class CdcStockcheckServiceResponse extends BaseResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6588918986919175055L;
	
	private String ztp;      //托盘号
	private int num;         //总计数量
	private String lgpla;    //仓位
	private String zlatxt;   //仓位描述
	private List<BindingBox> zxhlist;
	
	public List<BindingBox> getZxhlist() {
		return zxhlist;
	}
	public void setZxhlist(List<BindingBox> zxhlist) {
		this.zxhlist = zxhlist;
	}
	public String getZtp() {
		return ztp;
	}
	public void setZtp(String ztp) {
		this.ztp = ztp;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getLgpla() {
		return lgpla;
	}
	public void setLgpla(String lgpla) {
		this.lgpla = lgpla;
	}
	public String getZlatxt() {
		return zlatxt;
	}
	public void setZlatxt(String zlatxt) {
		this.zlatxt = zlatxt;
	}

}




