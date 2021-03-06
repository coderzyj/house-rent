package com.dusto.mobile.biz.scheduler.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dusto.mobile.biz.scheduler.dao.MasterDataMapper;
import com.dusto.mobile.biz.scheduler.service.MasterDataService;
import com.dusto.mobile.biz.scheduler.vo.MasterData;
import com.dusto.mobile.common.constant.DustoConstant;

@Service("masterDataService")
public class MasterDataServiceImpl implements MasterDataService {

	@Resource
	private MasterDataMapper masterDataMapper;

	@Override
	@Transactional
	public int delete(String zxh) {
		if (zxh == null) {
			return 0;
		}
		return masterDataMapper.deleteMasterdataByPrimaryKey(zxh);
	}

	@Override
	@Transactional
	public int insert(MasterData masterData) {
		if (masterData == null) {
			return 0;
		}
		return masterDataMapper.addMasterdata(masterData);
	}

	@Override
	@Transactional
	public int update(MasterData masterData) {
		if (masterData == null) {
			return 0;
		}
		return masterDataMapper.updateMasterdata(masterData);
	}

	@Override
	@Transactional
	public int updateMasterdataFlag(String flg, String ztp, String sapsys, List<String> zxhList) {
		if (zxhList == null || zxhList.size() == 0) {
			return 0;
		}
		Map<String, Object> request = new HashMap<String, Object>();
		request.put("flg", flg);
		request.put("ztp", ztp);
		request.put("zxhList", zxhList);
		request.put("sapsys", sapsys);
		return masterDataMapper.updateMasterdataFlag(request);
	}

	@Override
	public MasterData getMasterdata(String zxh) {
		if (zxh == null) {
			return null;
		}
		return masterDataMapper.getMasterdata(zxh);
	}

	@Override
	@Transactional
	public int addMasterdataList(List<MasterData> masterDataList) {
		if (masterDataList == null || masterDataList.size() == 0) {
			return 0;
		}
		return masterDataMapper.addMasterdataList(masterDataList);
	}

	@Override
	public List<MasterData> loadBindMasterdataByZtp(String ztp) {
		if (ztp == null) {
			return new ArrayList<MasterData>();
		}

		return masterDataMapper.loadMasterdataByZtpAndFlg(ztp, DustoConstant.ZXH_STATUS_BINDING);
	}

	@Override
	public List<MasterData> loadUnbindMasterdataByZtp(String ztp) {
		if (ztp == null) {
			return new ArrayList<MasterData>();
		}

		return masterDataMapper.loadMasterdataByZtpAndFlg(ztp, DustoConstant.ZXH_STATUS_NO_BINDING);
	}

	@Override
	public int updateMasterdataFlagByZTPToBinding(String ztp) {
		if (ztp == null) {
			return 0;
		}
		return masterDataMapper.updateMasterdataFlagByZTP(ztp, DustoConstant.ZXH_STATUS_BINDING);
	}

	@Override
	public int updateMasterdataFlagByZTPToUnbinding(String ztp) {
		if (ztp == null) {
			return 0;
		}
		return masterDataMapper.updateMasterdataFlagByZTP(ztp, DustoConstant.ZXH_STATUS_NO_BINDING);
	}

	@Override
	public List<MasterData> findMasterdata(List<MasterData> masterDataList) {
		if (masterDataList == null || masterDataList.size() == 0) {
			return new ArrayList<MasterData>();
		}
		return masterDataMapper.findMasterdata(masterDataList);
	}

	@Override
	@Transactional
	public int deleteMasterdata(List<MasterData> masterDataList) {
		if (masterDataList == null || masterDataList.size() == 0) {
			return 0;
		}
		return masterDataMapper.deleteMasterdata(masterDataList);
	}

}
