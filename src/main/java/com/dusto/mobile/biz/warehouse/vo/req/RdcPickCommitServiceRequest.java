package com.dusto.mobile.biz.warehouse.vo.req;

import com.dusto.mobile.common.vo.BaseRequest;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cwd on 05/09/2017.
 */

public class RdcPickCommitServiceRequest extends BaseRequest implements Serializable {

	private static final long serialVersionUID = -7684584476459276072L;
	private List<BcData> bcList;
	    private String uName;
	    private String ztp;
	    private String zbc;
	    private String zend;
	    
	    public List<BcData> getBcList() {
	        return bcList;
	    }

	    public void setBcList(List<BcData> bcList) {
	        this.bcList = bcList;
	    }

	    public String getuName() {
	        return uName;
	    }

	    public void setuName(String uName) {
	        this.uName = uName;
	    }

	    public String getZtp() {
	        return ztp;
	    }

	    public void setZtp(String ztp) {
	        this.ztp = ztp;
	    }

	    public String getZbc() {
	        return zbc;
	    }

	    public void setZbc(String zbc) {
	        this.zbc = zbc;
	    }

	    public String getZend() {
	        return zend;
	    }

	    public void setZend(String zend) {
	        this.zend = zend;
	    }

	    @Override
	    public String toString() {
	        return "RdcPickCommitServiceRequest{" +
	                "bcList=" + bcList +
	                ", uName='" + uName + '\'' +
	                ", ztp='" + ztp + '\'' +
	                ", zbc='" + zbc + '\'' +
	                ", zend='" + zend + '\'' +
	                '}';
	    }
}
