package com.dusto.mobile.biz.warehouse.vo.res;

import com.dusto.mobile.biz.warehouse.vo.req.BcData;
import com.dusto.mobile.common.vo.BaseResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cwd on 5/9/2017.
 */

public class RdcPickServiceResponse extends BaseResponse implements Serializable {

	private static final long serialVersionUID = -354045154230644691L;
	private List<BcData> bcList;

    public List<BcData> getBcList() {
        return bcList;
    }

    public void setBcList(List<BcData> bcList) {
        this.bcList = bcList;
    }

    @Override
    public String toString() {
        return "RdcPickServiceResponse{" +
                "bcList=" + bcList +
                '}';
    }
}
