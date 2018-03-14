package com.dusto.mobile.biz.scheduler.job;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.dusto.mobile.biz.scheduler.controller.JobController;
import com.dusto.mobile.biz.scheduler.service.ScheduleJobService;
import com.dusto.mobile.biz.scheduler.vo.ScheduleJob;

@Component
public class InitEventProcessor extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8509377260270399733L;

	private Logger logger = Logger.getLogger(InitEventProcessor.class);

	@Autowired
	private ScheduleJobService scheduleJobService;

	@Autowired
	private JobController controller;

	@Override
	@PostConstruct
	public void init() {
		FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(5000);
				start();
				return "Collection Completed";
			}

		});
		new Thread(task).start();
	}

	private void start() {
		logger.info("InitEventProcessor:start start");
		if (scheduleJobService == null) {
			logger.info("InitEventProcessor:start scheduleJobService is null");
			return;
		}
		List<ScheduleJob> jobs = scheduleJobService.loadjob();
		for (ScheduleJob job : jobs) {
			logger.info("job info:" + JSON.toJSONString(job));
			controller.quartz(job);
		}
		logger.info("InitEventProcessor:start end");
	}

}
