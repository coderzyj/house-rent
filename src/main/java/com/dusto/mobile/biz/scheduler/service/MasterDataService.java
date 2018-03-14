package com.dusto.mobile.biz.scheduler.service;

import java.util.List;

import com.dusto.mobile.biz.scheduler.vo.MasterData;

public interface MasterDataService {
	public int delete(String zxh);

	public int insert(MasterData masterData);

	public int update(MasterData masterData);

	public int updateMasterdataFlag(String flg, String ztp,String sapsys,  List<String> zxhList);

	public MasterData getMasterdata(String zxh);

	public int addMasterdataList(List<MasterData> masterDataList);

	public List<MasterData> loadBindMasterdataByZtp(String ztp);

	public List<MasterData> loadUnbindMasterdataByZtp(String ztp);

	public int updateMasterdataFlagByZTPToBinding(String ztp);

	public int updateMasterdataFlagByZTPToUnbinding(String ztp);

	public List<MasterData> findMasterdata(List<MasterData> masterDataList);

	public int deleteMasterdata(List<MasterData> masterDataList);
}
