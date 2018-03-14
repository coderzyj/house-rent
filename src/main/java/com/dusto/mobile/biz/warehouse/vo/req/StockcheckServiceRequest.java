package com.dusto.mobile.biz.warehouse.vo.req;

import java.io.Serializable;

import com.dusto.mobile.common.vo.BaseRequest;

public class StockcheckServiceRequest extends BaseRequest implements Serializable {

	/**
	 * 库存查询请求
	 */
	private static final long serialVersionUID = -6583397841871792682L;
		private String ztp;
		private String lgpla;
		
		public String getZtp() {
			return ztp;
		}
		public void setZtp(String ztp) {
			this.ztp = ztp;
		}
		public String getLgpla() {
			return lgpla;
		}
		public void setLgpla(String lgpla) {
			this.lgpla = lgpla;
		}

}
