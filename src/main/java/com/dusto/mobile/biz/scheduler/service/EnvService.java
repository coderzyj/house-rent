package com.dusto.mobile.biz.scheduler.service;

import java.util.List;

import com.dusto.mobile.biz.scheduler.vo.EnvVO;

public interface EnvService {
	
	public EnvVO getEnvdata(String envKey);

	public int addEnvdata(EnvVO envData);
	
	public int updateEnvdata(EnvVO envData);
	
	public int deleteEnvdataByPrimaryKey( String envKey);

	public int updateEnvdataValue( String envKey,String envValue);
	
	public List<EnvVO> loadEnvdata();
}
