package com.dusto.mobile.biz.warehouse.vo.req;

import java.io.Serializable;

import com.dusto.mobile.common.vo.BaseRequest;

/**
 * Created by cwd on 04/09/2017.
 */

public class RdcPickServiceRequest extends BaseRequest implements Serializable {

	private static final long serialVersionUID = 2797540315601688848L;
	private String zbc;
    private String uName;

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
}
