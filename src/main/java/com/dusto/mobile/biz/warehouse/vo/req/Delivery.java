package com.dusto.mobile.biz.warehouse.vo.req;

import java.io.Serializable;

/**
 * Created by cwd on 6/6/2017.
 */

public class Delivery  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 3904147427719266166L;
	private String vbeln;

    public String getVbeln() {
        return vbeln;
    }

    public void setVbeln(String vbeln) {
        this.vbeln = vbeln;
    }
}
