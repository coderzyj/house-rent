package com.dusto.mobile.biz.scheduler.controller;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dusto.mobile.biz.scheduler.QuartzManager;
import com.dusto.mobile.biz.scheduler.service.ScheduleJobService;
import com.dusto.mobile.biz.scheduler.vo.JobCronExpression;
import com.dusto.mobile.biz.scheduler.vo.ScheduleJob;
import com.dusto.mobile.biz.scheduler.vo.res.JobListRes;
import com.dusto.mobile.common.constant.DustoConstant;

@Controller
@RequestMapping("job")
public class JobController {
	private Logger logger = Logger.getLogger(JobController.class);

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private ScheduleJobService scheduleJobService;
	
	private static final String KEY_SCHEDULER_START = "1";
	private static final String KEY_SCHEDULER_STOP = "2";
	private static final String KEY_SCHEDULER_STARTNOW = "3";
	private static final String KEY_SCHEDULER_PAUSE = "0";
	private static final String JOB_GROUP_PREFIX = "JOB_";
	private static final String TRIGGER_GROUP_PREFIX  = "TRIGGER_";
	
	@ResponseBody
	@RequestMapping(value = "/quartz", method = { RequestMethod.GET })
	public void quartz(ScheduleJob job) {
		//Job status: 0禁用 1启用 2删除 3 立刻执行一次
		logger.info("entry JobController: quartz input parameters:" + JSON.toJSONString(job));
		Scheduler scheduler = (Scheduler) ctx.getBean("schedulerFactoryBean");
		String jobGroup = JOB_GROUP_PREFIX+job.getJobGroup();
		String triggerGroup = TRIGGER_GROUP_PREFIX+job.getJobGroup();
		try {
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), triggerGroup);
			JobKey jobKey = new JobKey(job.getJobName(), jobGroup);
			if (KEY_SCHEDULER_START.equals(job.getJobStatus()) || KEY_SCHEDULER_STARTNOW.equals(job.getJobStatus())) {
				logger.info("entry JobController:start a new trigger");
				// 获取trigger
				CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey); // 不存在，创建一个
				if (null == trigger) {
					logger.info("entry JobController: create new trigger");
					String classNamestr = job.getClassName();
					if (classNamestr == null || classNamestr.isEmpty()) {
						classNamestr = "com.dusto.mobile.biz.scheduler.job.CustomScheduledJob";
					}
					@SuppressWarnings("unchecked")
					Class<? extends Job> className = (Class<? extends Job>) Class.forName(classNamestr);
					QuartzManager.addJob(scheduler, job.getJobName(), jobGroup, job.getJobName(), triggerGroup, className, job.getCronExpression());
					
				} else { // Trigger已存在，那么更新相应的定时设置 //表达式调度构建器
					logger.info("entry JobController: update an exist trigger");
					QuartzManager.modifyJobTime(scheduler, job.getJobName(), jobGroup, job.getJobName(), triggerGroup, job.getCronExpression());
				}
				if(KEY_SCHEDULER_STARTNOW.equals(job.getJobStatus())){
					scheduler.triggerJob(jobKey);
				}
			} else if (KEY_SCHEDULER_STOP.equals(job.getJobStatus())) {
				logger.info("entry JobController: stop an exist trigger");
				QuartzManager.removeJob(scheduler, job.getJobName(), jobGroup, job.getJobName(), triggerGroup);
			} else if (KEY_SCHEDULER_PAUSE.equals(job.getJobStatus())) {
				scheduler.pauseJob(jobKey);
			}
		} catch (SchedulerException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 获取所有任务列表
	 */
	@RequestMapping(value = "/loadData", method = { RequestMethod.GET })
	@ResponseBody
	public JobListRes loadData() throws IOException {
		logger.info("entry JobController:loadData");
		List<ScheduleJob> list = scheduleJobService.loadjob();
		JobListRes res = new JobListRes();
		res.setReturnCode(DustoConstant.RESPONSE_NORMAL);
		res.setResult(list);
		logger.info("exit JobController:loadData output parameters:" + JSON.toJSONString(res));
		return res;
	}

	/**
	 * 开始任务
	 */
	@RequestMapping(value = "/start", method = { RequestMethod.POST })
	@ResponseBody
	public void startJob(@RequestBody ScheduleJob req) throws IOException {
		logger.info("entry JobController:startJob input parameters:" + JSON.toJSONString(req));
		scheduleJobService.updateJobStatus(KEY_SCHEDULER_START, req.getJobId());
		req.setJobStatus(KEY_SCHEDULER_START);
		quartz(req);
		logger.info("exit JobController:startJob");
	}

	/**
	 * 停止任务
	 */
	@RequestMapping(value = "/stop", method = { RequestMethod.POST })
	@ResponseBody
	public void stopJob(@RequestBody ScheduleJob req) throws IOException {
		logger.info("entry JobController:stopJob input parameters:" + JSON.toJSONString(req));
		scheduleJobService.updateJobStatus(KEY_SCHEDULER_STOP, req.getJobId());
		req.setJobStatus(KEY_SCHEDULER_STOP);
		quartz(req);
		logger.info("exit JobController:stopJob");

	}

	/**
	 * 更新任务
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.POST })
	@ResponseBody
	public void modifyTrigger(@RequestBody ScheduleJob req) throws IOException {
		logger.info("entry JobController:modifyTrigger input parameters:" + JSON.toJSONString(req));
//		req.setJobStatus("1");
		scheduleJobService.update(req);
		quartz(req);
		logger.info("exit JobController:modifyTrigger");

	}

	/**
	 * 立即开始
	 */
	@RequestMapping(value = "/startNow", method = { RequestMethod.POST })
	@ResponseBody
	public void startNowJob(@RequestBody ScheduleJob req) throws IOException {
		logger.info("entry JobController:startNowJob input parameters:" + JSON.toJSONString(req));
		req.setJobStatus(KEY_SCHEDULER_START);
		scheduleJobService.update(req);
		//先停止，再启动
		req.setJobStatus(KEY_SCHEDULER_STOP);
		quartz(req);
		req.setJobStatus(KEY_SCHEDULER_STARTNOW);
		quartz(req);
		logger.info("exit JobController:startNowJob");
	}
	

	/**
	 * 暂停任务
	 */
	@RequestMapping(value = "/pause", method = { RequestMethod.POST })
	@ResponseBody
	public void pauseJob(@RequestBody ScheduleJob req) throws IOException {
		logger.info("entry JobController:pauseJob input parameters:" + JSON.toJSONString(req));

		logger.info("exit JobController:pauseJob");

	}

	/**
	 * 恢复任务
	 */
	@RequestMapping(value = "/resume", method = { RequestMethod.POST })
	@ResponseBody
	public void resumeJob(@RequestBody ScheduleJob req) throws IOException {
		logger.info("entry JobController:resumeJob input parameters:" + JSON.toJSONString(req));

		logger.info("exit JobController:resumeJob");

	}

	/**只更新cron表达式
	 * 更新任务
	 */
	@RequestMapping(value = "/updatecron", method = { RequestMethod.POST })
	@ResponseBody
	public JobListRes modifyCronExpression(@RequestBody JobCronExpression req) throws IOException {
		logger.info("entry JobController:modifyTrigger input parameters: jobId=" + req.getJobId() + ",cronExpression=" + req.getCronExpression());
//		req.setJobStatus("1");
		scheduleJobService.updateCronExpression(req.getCronExpression(), req.getJobId());
		ScheduleJob job = scheduleJobService.selectByPrimaryKey(req.getJobId());
		quartz(job);
		logger.info("exit JobController:modifyCronExpression");
		return loadData();
	}

}
