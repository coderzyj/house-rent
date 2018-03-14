package com.dusto.mobile.biz.warehouse.vo.req;

import java.io.Serializable;

import com.dusto.mobile.common.vo.BaseRequest;

/**
 * Created by cwd on 2017/7/20.
 */

public class RdcStockCheckServiceRequest extends BaseRequest implements Serializable  {
	private static final long serialVersionUID = 209982334707784011L;
		private String matnr;
        private String zxh;
        private String uName;

        public String getZxh() { return zxh;}

        public void setZxh(String zxh) { this.zxh = zxh; }

        public String getuName() {
                return uName;
        }

        public void setuName(String uName) {
                this.uName = uName;
        }

        public String getMatnr() {
                return matnr;
        }

        public void setMatnr(String matnr) {
                this.matnr = matnr;
        }
}
