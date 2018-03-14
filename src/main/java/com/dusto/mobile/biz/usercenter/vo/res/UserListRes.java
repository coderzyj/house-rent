package com.dusto.mobile.biz.usercenter.vo.res;

import java.io.Serializable;
import java.util.List;

import com.dusto.mobile.biz.usercenter.entity.User;
import com.dusto.mobile.common.vo.BaseResponse;

public class UserListRes extends BaseResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6904054511519788319L;

	private List<User> result;

	public List<User> getResult() {
		return result;
	}

	public void setResult(List<User> result) {
		this.result = result;
	}

}
