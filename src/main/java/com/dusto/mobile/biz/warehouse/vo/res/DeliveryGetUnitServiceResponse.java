package com.dusto.mobile.biz.warehouse.vo.res;
import com.dusto.mobile.common.vo.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Kevin on 6/6/2017.
 */

public class DeliveryGetUnitServiceResponse extends BaseResponse implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 5826873698001484870L;
	private List<DeliveryUnit> unitList;

    public List<DeliveryUnit> getUnitList() {
        return unitList;
    }

    public void setUnitList(List<DeliveryUnit> unitList) {
        this.unitList = unitList;
    }

    
}
