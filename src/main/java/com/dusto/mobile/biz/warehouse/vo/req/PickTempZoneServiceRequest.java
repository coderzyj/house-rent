package com.dusto.mobile.biz.warehouse.vo.req;

import java.io.Serializable;
import java.util.List;

import com.dusto.mobile.common.vo.BaseRequest;

public class PickTempZoneServiceRequest extends BaseRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5605280178194282298L;
	
	private String oldZtp; //原托盘号
	private String newZtp; //新托盘号
	private String lfimg; //扫描数量
	private String lgpla; //仓位
	private String zrw; //拣货任务号
	private String uName;
	private String  ztpye; //ZTPYE;
	private List<String> boxList;
	public String getOldZtp() {
		return oldZtp;
	}
	public void setOldZtp(String oldZtp) {
		this.oldZtp = oldZtp;
	}
	public String getNewZtp() {
		return newZtp;
	}
	public void setNewZtp(String newZtp) {
		this.newZtp = newZtp;
	}
	public String getLfimg() {
		return lfimg;
	}
	public void setLfimg(String lfimg) {
		this.lfimg = lfimg;
	}
	public String getLgpla() {
		return lgpla;
	}
	public void setLgpla(String lgpla) {
		this.lgpla = lgpla;
	}
	public String getZrw() {
		return zrw;
	}
	public void setZrw(String zrw) {
		this.zrw = zrw;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public List<String> getBoxList() {
		return boxList;
	}
	public void setBoxList(List<String> boxList) {
		this.boxList = boxList;
	}
	public String getZtpye() {
		return ztpye;
	}
	public void setZtpye(String ztpye) {
		this.ztpye = ztpye;
	}
	
}
