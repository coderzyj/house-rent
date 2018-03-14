package com.dusto.mobile.biz.scheduler.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dusto.mobile.biz.scheduler.vo.EnvVO;


public interface EnvMapper {
	
	public EnvVO getEnvdata(@Param(value = "envKey") String envKey);

	public int addEnvdata(EnvVO envData);
	
	public int updateEnvdata(EnvVO envData);
	
	public int deleteEnvdataByPrimaryKey(@Param(value = "envKey") String envKey);

	public int updateEnvdataValue(@Param(value = "envKey") String envKey,@Param(value = "envValue") String envValue);
	
	public List<EnvVO> loadEnvdata();
	
}
