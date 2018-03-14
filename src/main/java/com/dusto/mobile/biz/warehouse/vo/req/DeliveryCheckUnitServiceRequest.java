package com.dusto.mobile.biz.warehouse.vo.req;

import com.dusto.mobile.biz.warehouse.vo.res.DeliveryUnit;
import com.dusto.mobile.common.vo.BaseRequest;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cwd on 8/6/2017.
 */

public class DeliveryCheckUnitServiceRequest extends BaseRequest implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1712518777459284220L;
	private String uName;
    private List<DeliveryUnit> unitList;

    public List<DeliveryUnit> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<DeliveryUnit> unitList) {
        this.unitList = unitList;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

}
