package com.dusto.mobile.biz.scheduler;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

public class QuartzManager {
	private static Logger logger = Logger.getLogger(QuartzManager.class);
	private static String JOB_GROUP_NAME = "JOB_GROUP_SYSTEM";
	private static String TRIGGER_GROUP_NAME = "TRIGGER_GROUP_SYSTEM";

	/**
	 * @Description: 添加一个定时任务，使用默认的任务组名，触发器名，触发器组名
	 * 
	 * @param sched
	 *            调度器
	 * 
	 * @param jobName
	 *            任务名
	 * @param cls
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 * 
	 * @Title: QuartzManager.java
	 */
	public static void addJob(Scheduler sched, String jobName, @SuppressWarnings("rawtypes") Class cls, String time) {
		logger.info("QuartzManager:addJob jobName=" + jobName + ",time=" + time);
		try {
			JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);// 任务名，任务组，任务执行类
			@SuppressWarnings("unchecked")
			JobDetail jobDetail = newJob(cls).withIdentity(jobKey).build();
			TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);// 触发器
			Trigger trigger = newTrigger().withIdentity(triggerKey).withSchedule(cronSchedule(time)).build();// 触发器时间设定
			sched.scheduleJob(jobDetail, trigger);
			if (!sched.isShutdown()) {
				sched.start();// 启动
			}
		} catch (Exception e) {
			logger.info("QuartzManager:addJob throw exception");
			throw new RuntimeException(e);
		}
		logger.info("QuartzManager:addJob end");
	}

	/**
	 * @Description: 添加一个定时任务
	 * 
	 * @param sched
	 *            调度器
	 * 
	 * @param jobName
	 *            任务名
	 * @param jobGroupName
	 *            任务组名
	 * @param triggerName
	 *            触发器名
	 * @param triggerGroupName
	 *            触发器组名
	 * @param jobClass
	 *            任务
	 * @param time
	 *            时间设置，参考quartz说明文档
	 * 
	 * @Title: QuartzManager.java
	 */
	public static void addJob(Scheduler sched, String jobName, String jobGroupName, String triggerJobName,
			String triggerGroupName, @SuppressWarnings("rawtypes") Class jobClass, String time) {
		logger.info("QuartzManager:addJob jobName=" + jobName + ",jobGroupName=" + jobGroupName + ",triggerName="
				+ triggerJobName + ",triggerGroupName=" + triggerGroupName + ",time=" + time);
		try {
			JobKey jobKey = new JobKey(jobName, jobGroupName);
			@SuppressWarnings("unchecked")
			JobDetail jobDetail = newJob(jobClass).withIdentity(jobKey).build();
			// 触发器
			TriggerKey triggerKey = new TriggerKey(triggerJobName, triggerGroupName);
			Trigger trigger = newTrigger().withIdentity(triggerKey).withSchedule(cronSchedule(time)).build();
			sched.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			logger.info("QuartzManager:addJob throw exception");
			throw new RuntimeException(e);
		}
		logger.info("QuartzManager:addJob end");
	}

	/**
	 * @Description: 修改一个任务的触发时间(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param sched
	 *            调度器
	 * @param jobName
	 * @param time
	 * 
	 * @Title: QuartzManager.java
	 */
	@SuppressWarnings("rawtypes")
	public static void modifyJobTime(Scheduler sched, String jobName, String time) {
		logger.info("QuartzManager:modifyJobTime jobName=" + jobName + ",time=" + time);
		try {
			TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);
				JobDetail jobDetail = sched.getJobDetail(jobKey);
				Class objJobClass = jobDetail.getJobClass();
				removeJob(sched, jobName);
				addJob(sched, jobName, objJobClass, time);
			}
		} catch (Exception e) {
			logger.info("QuartzManager:modifyJobTime throw exception");
			throw new RuntimeException(e);
		}
		logger.info("QuartzManager:modifyJobTime end");
	}

	/**
	 * @Description: 修改一个任务的触发时间
	 * @param sched
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerJobName
	 * @param triggerGroupName
	 * @param time
	 */
	public static void modifyJobTime(Scheduler sched, String jobName, String jobGroupName, String triggerJobName,
			String triggerGroupName, String time) {
		logger.info("QuartzManager:modifyJobTime jobName=" + jobName + ",jobGroupName=" + jobGroupName
				+ ",triggerJobName=" + triggerJobName + ",triggerGroupName=" + triggerGroupName + ",time=" + time);
		try {
			TriggerKey triggerKey = new TriggerKey(triggerJobName, triggerGroupName);
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
			if (trigger == null) {
				logger.info("QuartzManager:modifyJobTime trigger is null");
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				JobKey jobKey = new JobKey(jobName, jobGroupName);
				JobDetail jobDetail = sched.getJobDetail(jobKey);
				Class objJobClass = jobDetail.getJobClass();
				removeJob(sched, jobName, jobGroupName, triggerJobName, triggerGroupName);
				// addJob(sched, jobName, objJobClass, time);
				addJob(sched, jobName, jobGroupName, triggerJobName, triggerGroupName, objJobClass, time);
			}
		} catch (Exception e) {
			logger.info("QuartzManager:modifyJobTime throw exception");
			throw new RuntimeException(e);
		}
		logger.info("QuartzManager:modifyJobTime end");
	}

	/**
	 * @Description: 修改一个任务的触发时间
	 * 
	 * @param sched
	 *            调度器 *
	 * @param triggerJobName
	 * @param triggerGroupName
	 * @param time
	 * 
	 * @Title: QuartzManager.java
	 */
	public static void modifyJobTime(Scheduler sched, String triggerJobName, String triggerGroupName, String time) {
		logger.info("QuartzManager:modifyJobTime triggerJobName=" + triggerJobName + ",triggerGroupName="
				+ triggerGroupName + ",time=" + time);
		try {
			TriggerKey triggerKey = new TriggerKey(triggerJobName, triggerGroupName);
			CronTrigger trigger = (CronTrigger) sched.getTrigger(triggerKey);
			if (trigger == null) {
				return;
			}
			String oldTime = trigger.getCronExpression();
			if (!oldTime.equalsIgnoreCase(time)) {
				// 修改时间
				trigger.getTriggerBuilder().withSchedule(cronSchedule(time));
				// 重启触发器
				sched.resumeTrigger(triggerKey);
			}
		} catch (Exception e) {
			logger.info("QuartzManager:modifyJobTime throw exception");
			throw new RuntimeException(e);
		}
		logger.info("QuartzManager:modifyJobTime end");
	}

	/**
	 * @Description: 移除一个任务(使用默认的任务组名，触发器名，触发器组名)
	 * 
	 * @param sched
	 *            调度器
	 * @param jobName
	 * 
	 * @Title: QuartzManager.java
	 */
	public static void removeJob(Scheduler sched, String jobName) {
		logger.info("QuartzManager:removeJob jobName=" + jobName);
		try {
			TriggerKey triggerKey = new TriggerKey(jobName, TRIGGER_GROUP_NAME);
			sched.pauseTrigger(triggerKey);// 停止触发器
			sched.unscheduleJob(triggerKey);// 移除触发器
			JobKey jobKey = new JobKey(jobName, JOB_GROUP_NAME);
			sched.deleteJob(jobKey);// 删除任务
		} catch (Exception e) {
			logger.info("QuartzManager:removeJob throw exception");
			throw new RuntimeException(e);
		}
		logger.info("QuartzManager:removeJob end");
	}

	/**
	 * @Description: 移除一个任务
	 * 
	 * @param sched
	 *            调度器
	 * @param jobName
	 * @param jobGroupName
	 * @param triggerName
	 * @param triggerGroupName
	 * 
	 * @Title: QuartzManager.java
	 */
	public static void removeJob(Scheduler sched, String jobName, String jobGroupName, String triggerJobName,
			String triggerGroupName) {
		logger.info("QuartzManager:modifyJobTime triggerJobName=" + triggerJobName + ",triggerGroupName="
				+ triggerGroupName);
		try {
			TriggerKey triggerKey = new TriggerKey(triggerJobName, triggerGroupName);
			sched.pauseTrigger(triggerKey);// 停止触发器
			sched.unscheduleJob(triggerKey);// 移除触发器
			JobKey jobKey = new JobKey(jobName, jobGroupName);
			sched.deleteJob(jobKey);// 删除任务
		} catch (Exception e) {
			logger.info("QuartzManager:removeJob throw exception");
			throw new RuntimeException(e);
		}
		logger.info("QuartzManager:removeJob end");
	}

	/**
	 * @Description:启动所有定时任务
	 * 
	 * @param sched
	 *            调度器
	 * 
	 * @Title: QuartzManager.java
	 */
	public static void startJobs(Scheduler sched) {
		try {
			sched.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description:关闭所有定时任务
	 * 
	 * @param sched
	 *            调度器
	 * 
	 */
	public static void shutdownJobs(Scheduler sched) {
		try {
			if (!sched.isShutdown()) {
				sched.shutdown();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
