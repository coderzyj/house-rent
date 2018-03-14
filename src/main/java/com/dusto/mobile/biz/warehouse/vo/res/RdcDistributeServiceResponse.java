package com.dusto.mobile.biz.warehouse.vo.res;

import com.dusto.mobile.common.vo.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cwd on 6/9/2017.
 */

public class RdcDistributeServiceResponse extends BaseResponse implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -1455609565460514118L;
	private List<DtData> dtList;

    public List<DtData> getDtList() {
        return dtList;
    }

    public void setDtList(List<DtData> dtList) {
        this.dtList = dtList;
    }

    @Override
    public String toString() {
        return "RdcDistributeServiceResponse{" +
                "dtList=" + dtList +
                '}';
    }
}
