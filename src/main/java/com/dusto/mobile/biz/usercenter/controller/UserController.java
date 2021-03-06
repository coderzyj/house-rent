package com.dusto.mobile.biz.usercenter.controller;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dusto.mobile.biz.usercenter.entity.User;
import com.dusto.mobile.biz.usercenter.entity.Warehouse;
import com.dusto.mobile.biz.usercenter.service.UserService;
import com.dusto.mobile.biz.usercenter.service.WarehouseService;
import com.dusto.mobile.biz.usercenter.vo.req.ListUserReq;
import com.dusto.mobile.biz.usercenter.vo.req.LoginUserReq;
import com.dusto.mobile.biz.usercenter.vo.res.UserListRes;
import com.dusto.mobile.biz.usercenter.vo.res.UserVoRes;
import com.dusto.mobile.common.constant.DustoConstant;
import com.dusto.mobile.common.vo.BaseResponse;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private WarehouseService warehouseService;

	private Logger logger = Logger.getLogger(UserController.class);

	/**
	 * 获取用户
	 */
	@RequestMapping(value = "/getUserList", method = { RequestMethod.POST })
	@ResponseBody
	public UserListRes getUserList(@RequestBody ListUserReq req) throws IOException {
		logger.info("entry UserController:getUserList input parameters:" + JSON.toJSONString(req));
		int offset = 0;
		int rows = 10000;
		if (req != null) {
			offset = req.getOffset();
			rows = req.getRows();
		}
		List<User> list = userService.listUsersByPage(offset, rows);
		UserListRes userList = new UserListRes();
		userList.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		userList.setResult(list);
		logger.info("exit UserController:getUserList output parameters:" + JSON.toJSONString(userList));
		return userList;
	}

	/**
	 * 登录
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	@ResponseBody
	public UserVoRes login(@RequestBody LoginUserReq req) throws IOException {
		logger.info("entry UserController:login input parameters:" + JSON.toJSONString(req));
		List<User> userlist = userService.login(req.getUserId(), req.getPassword());
		UserVoRes res = new UserVoRes();
		if (userlist.size() == 1) {
			// if (warehouse != null) {
			User user = userlist.get(0);
			Warehouse warehouse = warehouseService.selectByPrimaryKey(user.getWarehouse());
			res.setWarehouse(warehouse);
			res.setResult(user);
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
			// } else {
			// res.setReturnCode(DustoConstant.RESPONSE_LOGIN_NO_WAREHOUSE);
			// }

		} else {
			res.setReturnCode(DustoConstant.RESPONSE_LOGIN_FAILED);
		}
		logger.info("exit UserController:login output parameters:" + JSON.toJSONString(res));
		return res;
	}

	@RequestMapping(value = "/get/{userId}")
	@ResponseBody
	public UserVoRes selectByPrimaryKey(@PathVariable("userId") String userId) {
		logger.info("entry UserController:selectByPrimaryKey  input parameters: userId:" + userId);
		User user = userService.selectByPrimaryKey(userId);
		UserVoRes vo = new UserVoRes();
		vo.setResult(user);
		vo.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		logger.info("exit UserController:selectByPrimaryKey  output parameters:" + JSON.toJSONString(vo));
		return vo;
	}

	@RequestMapping(value = "/insert", method = { RequestMethod.POST })
	@ResponseBody
	public BaseResponse insert(@RequestBody User req) {
		logger.info("entry UserController:insert input parameters: " + JSON.toJSONString(req));
		if (req.getAuthority() == null || "".equals(req.getAuthority())) {
			req.setAuthority("111111");
		}
		int ret = userService.insert(req);
		BaseResponse res = new BaseResponse();
		if (ret == 1) {
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SQL_NODATA);
		}
		logger.info("exit UserController:insert output parameters:" + JSON.toJSONString(res));
		return res;
	}

	@RequestMapping(value = "/delete/{userId}")
	@ResponseBody
	public BaseResponse deleteByPrimaryKey(@PathVariable("userId") String userId) {
		logger.info("entry UserController:deleteByPrimaryKey input parameters: userId:" + userId);
		int ret = userService.delete(userId);
		BaseResponse res = new BaseResponse();
		if (ret == 1) {
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SQL_NODATA);
		}
		logger.info("exit UserController:deleteByPrimaryKey output parameters:" + JSON.toJSONString(res));
		return res;
	}

	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public BaseResponse update(@RequestBody User req) {
		logger.info("entry UserController:update");
		logger.info("input parameters: " + JSON.toJSONString(req));
		if (req.getWarehouse() != null) {
			Warehouse wa = warehouseService.selectByWarehouseName(req.getWarehouse());
			if (wa != null) {
				req.setWarehouse(wa.getWarehouseId());
			}
		}

		int ret = userService.update(req);
		BaseResponse res = new BaseResponse();
		if (ret == 1) {
			res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		} else {
			res.setReturnCode(DustoConstant.RESPONSE_SQL_NODATA);
		}
		logger.info("update output parameters:" + JSON.toJSONString(res));
		logger.info("exit UserController:update");
		return res;
	}

	@RequestMapping(value = "/search/{keyword}")
	@ResponseBody
	public UserListRes searchByKeyword(@PathVariable("keyword") String keyword) {
		logger.info("entry UserController:searchByKeyword");
		logger.info("input parameters:" + JSON.toJSONString(keyword));
		List<User> list = userService.searchByKeyword(keyword);
		UserListRes userList = new UserListRes();
		userList.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		userList.setResult(list);
		logger.info("searchByKeyword output parameters:" + JSON.toJSONString(userList));
		logger.info("exit UserController:searchByKeyword");
		return userList;
	}

	@RequestMapping(value = "/updatepwd", method = { RequestMethod.POST })
	@ResponseBody
	public BaseResponse updatePwd(@RequestBody LoginUserReq req) throws IOException {
		logger.info("entry UserController:updatePwd input parameters:" + JSON.toJSONString(req));
		BaseResponse res = new BaseResponse();
		User user = userService.selectByPrimaryKey(req.getUserId());
		if (user != null) {
			user.setPassword(req.getPassword());
			int ret = userService.update(user);
			if (ret == 1) {
				res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
			} else {
				res.setReturnCode(DustoConstant.RESPONSE_SYS_ERROR);
			}

		} else {
			res.setReturnCode(DustoConstant.RESPONSE_PARAM_ERROR);
		}
		logger.info("exit UserController:updatePwd output parameters:" + JSON.toJSONString(res));
		return res;
	}
}
