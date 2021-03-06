package com.dusto.mobile.biz.scheduler.service;

import java.util.List;

import com.dusto.mobile.biz.scheduler.vo.ScheduleJob;

public interface ScheduleJobService {
	
	public ScheduleJob selectByPrimaryKey(String jobId);

	public int insert(ScheduleJob scheduleJob);

	public int delete(String jobId);

	public int update(ScheduleJob scheduleJob);
	
	public List<ScheduleJob> loadjob();
	
	public int updateJobStatus(String jobStatus, String jobId);
	
	public int updateCronExpression(String cronExpression, String jobId);
}
