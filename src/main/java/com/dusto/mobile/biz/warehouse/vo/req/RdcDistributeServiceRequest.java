package com.dusto.mobile.biz.warehouse.vo.req;

import java.io.Serializable;

import com.dusto.mobile.common.vo.BaseRequest;

/**
 * Created by cwd on 06/09/2017.
 */

public class RdcDistributeServiceRequest extends BaseRequest implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = -82190644472480782L;
	private String zbc;
    private String uName;
    private String ztp;

    public String getZbc() {
        return zbc;
    }

    public void setZbc(String zbc) {
        this.zbc = zbc;
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

    @Override
    public String toString() {
        return "RdcDistributeServiceRequest{" +
                "zbc='" + zbc + '\'' +
                ", uName='" + uName + '\'' +
                ", ztp='" + ztp + '\'' +
                '}';
    }
}
