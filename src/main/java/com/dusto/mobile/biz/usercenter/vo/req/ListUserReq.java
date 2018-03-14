package com.dusto.mobile.biz.usercenter.vo.req;

import java.io.Serializable;

import com.dusto.mobile.common.vo.BaseRequest;

public class ListUserReq extends BaseRequest implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = -4814984009521471905L;
	private int offset;
	private int rows;

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

}
