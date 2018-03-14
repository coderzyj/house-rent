package com.dusto.mobile.biz.scheduler.job;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class AbstractScheduledJob implements Job{
	private Logger logger = Logger.getLogger(AbstractScheduledJob.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("task run at: " + context.getFireTime());
		doScheduledJob();
	}
	
	public abstract void doScheduledJob();

}
