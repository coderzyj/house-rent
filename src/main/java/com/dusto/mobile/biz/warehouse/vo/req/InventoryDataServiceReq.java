package com.dusto.mobile.biz.warehouse.vo.req;

import java.io.Serializable;
import java.util.List;

import com.dusto.mobile.common.vo.BaseRequest;

public class InventoryDataServiceReq extends BaseRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5229215833013825059L;
		private String lgpla;
		private String zpdrwh;
		private String uname;
		private List<Inventorytplist> zpdtphlist;
		private List<BindingBox> zpdxhlist;
		private List<Inventoryxhmlist> zpdxhmlist;
		public String getLgpla() {
			return lgpla;
		}
		public void setLgpla(String lgpla) {
			this.lgpla = lgpla;
		}
		public String getZpdrwh() {
			return zpdrwh;
		}
		public void setZpdrwh(String zpdrwh) {
			this.zpdrwh = zpdrwh;
		}
		public String getUname() {
			return uname;
		}
		public void setUname(String uname) {
			this.uname = uname;
		}
		public List<Inventorytplist> getZpdtphlist() {
			return zpdtphlist;
		}
		public void setZpdtphlist(List<Inventorytplist> zpdtphlist) {
			this.zpdtphlist = zpdtphlist;
		}
		public List<BindingBox> getZpdxhlist() {
			return zpdxhlist;
		}
		public void setZpdxhlist(List<BindingBox> zpdxhlist) {
			this.zpdxhlist = zpdxhlist;
		}
		public List<Inventoryxhmlist> getZpdxhmlist() {
			return zpdxhmlist;
		}
		public void setZpdxhmlist(List<Inventoryxhmlist> zpdxhmlist) {
			this.zpdxhmlist = zpdxhmlist;
		}
		

}
