package com.dusto.mobile.biz.scheduler.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dusto.mobile.biz.scheduler.dao.EnvMapper;
import com.dusto.mobile.biz.scheduler.service.EnvService;
import com.dusto.mobile.biz.scheduler.vo.EnvVO;

@Service("envService")
public class EnvServiceImpl implements EnvService {

	@Resource
	private EnvMapper envMapper;

	@Override
	public EnvVO getEnvdata(String envKey) {
		if (envKey == null) {
			return null;
		}
		return envMapper.getEnvdata(envKey);
	}

	@Override
	@Transactional
	public int addEnvdata(EnvVO envData) {
		if (envData == null) {
			return 0;
		}
		return 0;
	}

	@Override
	@Transactional
	public int updateEnvdata(EnvVO envData) {
		if (envData == null) {
			return 0;
		}
		return envMapper.updateEnvdata(envData);
	}

	@Override
	@Transactional
	public int deleteEnvdataByPrimaryKey(String envKey) {
		if (envKey == null) {
			return 0;
		}
		return envMapper.deleteEnvdataByPrimaryKey(envKey);
	}

	@Override
	@Transactional
	public int updateEnvdataValue(String envKey, String envValue) {
		if (envKey == null) {
			return 0;
		}
		return envMapper.updateEnvdataValue(envKey, envValue);
	}

	@Override
	public List<EnvVO> loadEnvdata() {

		return envMapper.loadEnvdata();
	}

}
