package com.dusto.mobile.biz.usercenter.service;

import java.util.List;

import com.dusto.mobile.biz.usercenter.entity.User;

public interface UserService {

	public User selectByPrimaryKey(String userId);

	public int insert(User user);

	public int delete(String userId);

	public int update(User user);

	public List<User> listUsersByPage(int offset, int rows);

	public List<User> searchByKeyword(String keyword);

	public List<User> login(String userId, String password);
}
