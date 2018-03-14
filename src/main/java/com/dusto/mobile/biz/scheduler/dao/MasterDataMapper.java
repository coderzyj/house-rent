package com.dusto.mobile.biz.scheduler.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.dusto.mobile.biz.scheduler.vo.MasterData;

public interface MasterDataMapper {

	public int deleteMasterdataByPrimaryKey(@Param(value = "zxh") String zxh);

	public int addMasterdata(MasterData masterData);

	public int updateMasterdata(MasterData masterData);

	public int updateMasterdataFlag(Map<String, Object> masterDataFlagRequest);

	public MasterData getMasterdata(@Param(value = "zxh") String zxh);

	public List<MasterData> loadMasterdataByZtpAndFlg(@Param(value = "ztp") String ztp,
			@Param(value = "flg") String flg);

	public int addMasterdataList(List<MasterData> masterDataList);

	public int updateMasterdataFlagByZTP(@Param(value = "ztp") String ztp, @Param(value = "flg") String flg);

	public List<MasterData> findMasterdata(List<MasterData> masterDataList);

	public int deleteMasterdata(List<MasterData> masterDataList);
}
