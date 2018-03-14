package com.dusto.mobile.biz.usercenter.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dusto.mobile.biz.usercenter.entity.User;

public interface UserMapper {

	public int deleteUserByPrimaryKey(@Param(value = "userId") String userId);

	public int addUser(User user);

	public int updateUser(User user);

	public User getUser(@Param(value = "userId") String userId);

	public List<User> findUserlistUsersByPage(@Param(value = "offset") int offset, @Param(value = "rows") int rows);

	public List<User> getUserByKeyAndPwd(@Param(value = "userId") String userId, @Param(value = "password") String password);

	public List<User> searchByKeyword(@Param(value = "keyword") String keyword);
}
