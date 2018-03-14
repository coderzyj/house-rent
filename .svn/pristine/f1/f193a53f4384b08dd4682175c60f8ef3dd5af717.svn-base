package com.dusto.mobile.biz.usercenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dusto.mobile.biz.usercenter.dao.UserMapper;
import com.dusto.mobile.biz.usercenter.entity.User;
import com.dusto.mobile.biz.usercenter.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	public User selectByPrimaryKey(String userId) {
		if (userId == null) {
			return null;
		}
		return userMapper.getUser(userId);
	}

	public List<User> login(String userId, String password) {
		return userMapper.getUserByKeyAndPwd(userId, password);
	}

	@Transactional
	public int insert(User user) {
		if (user == null) {
			return 0;
		}
		return userMapper.addUser(user);
	}

	@Transactional
	public int update(User user) {
		if (user == null) {
			return 0;
		}
		return userMapper.updateUser(user);
	}

	@Transactional
	public int delete(String userId) {
		if (userId == null) {
			return 0;
		}
		return userMapper.deleteUserByPrimaryKey(userId);
	}

	@Override
	public List<User> listUsersByPage(int offset, int rows) {
		return userMapper.findUserlistUsersByPage(offset, rows);
	}

	@Override
	public List<User> searchByKeyword(String keyword) {
		return userMapper.searchByKeyword(keyword);
	}

}
