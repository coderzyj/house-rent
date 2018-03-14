package com.dusto.mobile.biz.usercenter.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dusto.mobile.biz.scheduler.job.CustomScheduledJob;
import com.dusto.mobile.biz.scheduler.service.MasterDataService;
import com.dusto.mobile.biz.scheduler.vo.ScheduleJob;
import com.dusto.mobile.biz.warehouse.vo.req.InboundServiceRequest;
import com.dusto.mobile.common.constant.DustoConstant;
import com.dusto.mobile.common.sap.conn.CustomSAPDestinationDataProvider.MyDestinationDataProvider;
import com.dusto.mobile.common.sap.conn.SAPConn;
import com.dusto.mobile.common.sap.jco.RfcManager;
import com.dusto.mobile.common.sap.test.StepByStepClient;
import com.dusto.mobile.common.sap.test.User;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.ext.Environment;

@Controller
@RequestMapping("sap")

public class SapTestController {
	private Logger logger = Logger.getLogger(SapTestController.class);

	@ResponseBody
	@RequestMapping("/sapTest")
	public String rfcTest() {

		logger.info("entry sap test controller");
		// RfcManager rfc = new RfcManager();
		JCoFunction function = RfcManager.getFunction("ZTEST1", 1);
		if (function == null) {
			return " Function 已经被删除";
		}
		JCoParameterList input = function.getImportParameterList();
		input.setValue("I_INPUT1", "1000");
		RfcManager.execute(function, 1);
		String result = function.getExportParameterList().getString("O_OUTPUT1");
		// JCoParameterList outputParam = function.getTableParameterList();
		// JCoTable bt = outputParam.getTable("TABLEOUT");
		// List<User> list = new ArrayList<User>();
		// for (int i = 0; i < bt.getNumRows(); i++) {
		// bt.setRow(i);
		// User user = new User();
		// user.setUserName(bt.getString("USER_NAME"));
		// list.add(user);
		// }
		// String json = JSON.toJSONString(list);
		// return list;
		return result;
	}

	@ResponseBody
	@RequestMapping("/sapTest2")
	public String rfcTest2() {
		Logger logger = Logger.getLogger(SapTestController.class);
		logger.info("entry sap 2 test controller");
		JCoFunction function = null;
		JCoDestination destination = SAPConn.connect();
		String result = "";// 调用接口返回状态
		String message = "";// 调用接口返回信息
		try {
			// 调用ZRFC_GET_REMAIN_SUM函数
			function = destination.getRepository().getFunction("ZTEST1");
			JCoParameterList input = function.getImportParameterList();
			// 发出扫码仓库
			input.setValue("I_INPUT1", "1000");
			// // 发出扫码库位
			// input.setValue("ZSNLGORT", "0001");
			// // 采购凭证号
			// input.setValue("EBELN", "1");
			//
			// // 获取传入表参数SN_ITEM
			// JCoTable SN_ITEM =
			// function.getTableParameterList().getTable("SN_ITEM");
			// SN_ITEM.appendRow();// 增加一行
			// // 给表参数中的字段赋值，此处测试，就随便传两个值进去
			// // 商品编码
			// SN_ITEM.setValue("MATNR", "1");
			// // 商品序列号
			// SN_ITEM.setValue("ZSERIAL", "1");
			function.execute(destination);
			result = function.getExportParameterList().getString("O_OUTPUT1");// 调用接口返回状态
			// message =
			// function.getExportParameterList().getString("MESSAGE");//
			// 调用接口返回信息
			System.out.println("调用返回状态--->" + result + ";调用返回信息--->" + message);
			// SN_ITEM.clear();// 清空本次条件，如果要继续传入值去或者还要循环，那得将之前的条件清空
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "NO data";
	}

	@ResponseBody
	@RequestMapping("/sapTest3")
	public String rfcTest3() {
		// 获取单例
		MyDestinationDataProvider myProvider = MyDestinationDataProvider.getInstance();

		// Register the MyDestinationDataProvider 环境注册
		Environment.registerDestinationDataProvider(myProvider);

		// TEST 01：直接测试
		// ABAP_AS is the test destination name ：ABAP_AS为目标连接属性名（只是逻辑上的命名）
		String destinationName = "ABAP_AS";
		System.out.println("Test destination - " + destinationName);
		// Properties connectProperties = new Properties();

		// connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST,
		// "192.168.111.123");
		// connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,
		// "00");
		// connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT,
		// "800");
		// connectProperties.setProperty(DestinationDataProvider.JCO_USER,
		// "SAPECC");
		// connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD,
		// "sapecc60");
		// connectProperties.setProperty(DestinationDataProvider.JCO_LANG,
		// "en");
		Properties connectProperties = loadProperties();
		// Add a destination
		myProvider.addDestination(destinationName, connectProperties);
		try {
			// Get a destination with the name of "ABAP_AS"
			JCoDestination DES_ABAP_AS = JCoDestinationManager.getDestination(destinationName);

			// Test the destination with the name of "ABAP_AS"

			DES_ABAP_AS.ping();
			System.out.println("Destination - " + destinationName + " is ok");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Destination - " + destinationName + " is invalid");
		}
		return "No data";
	}

	@ResponseBody
	@RequestMapping("/sapTest4")
	public String rfcTest4() {
		Logger logger = Logger.getLogger(SapTestController.class);
		logger.info("entry sap 4 test controller");
		StepByStepClient.init();
		try {
			StepByStepClient.step1Connect();
			StepByStepClient.step2ConnectUsingPool();
			StepByStepClient.step3SimpleCall();
			StepByStepClient.step4WorkWithTable();
			StepByStepClient.step4SimpleStatefulCalls();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "NODATA";
	}

	private static Properties loadProperties() {
		Properties prop = new Properties();
		try {
			String path = getClassesPath(SapTestController.class) + "conf/sap_afs.properties";
			InputStream in = new BufferedInputStream(new FileInputStream(path));
			prop.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}

	private static String getClassesPath(Class<SapTestController> clazz) {
		String path = clazz.getClassLoader().getResource("").getPath();
		try {
			return java.net.URLDecoder.decode(path, "UTF-8");
		} catch (java.io.UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "";
	}

	@ResponseBody
	@RequestMapping(value = "/jsontest", method = { RequestMethod.GET })
	public User jsontest() {
		logger.info("entry sap test controller");
		logger.info(System.getProperty("java.library.path"));
		User user = new User();
		user.setUserName("user name");
		user.setPassword("password");
		return user;
	}

	@ResponseBody
	@RequestMapping(value = "/postjson", method = { RequestMethod.POST })
	public User postjson(@RequestBody User req) {
		User user = new User();
		if (req == null) {
			req = user;
		}
		// req.setUserName("user name");
		req.setPassword("password");
		return req;
	}

	@Autowired
	private ApplicationContext ctx;

	@ResponseBody
	@RequestMapping(value = "/quartz", method = { RequestMethod.GET })
	public String quartz() {
		logger.info("entry sap test controller: quartz");
		// schedulerFactoryBean 由spring创建注入
		// ApplicationContext ctx = new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		System.out.println(ctx);
		Scheduler scheduler = (Scheduler) ctx.getBean("schedulerFactoryBean");
		try {
			System.out.println(scheduler); // 这里获取任务信息数据
			List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
			for (int i = 0; i < 2; i++) {
				ScheduleJob job = new ScheduleJob();
				job.setJobId("" + i);
				job.setJobName("JobName_" + i);
				job.setJobGroup("dusto");
				job.setJobStatus("3");
				job.setCronExpression("0/5 * * * * ?");
				job.setJobDesc("数据导入任务");
				jobList.add(job);
			}
			for (ScheduleJob job : jobList) {
				TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
				// 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
				CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey); // 不存在，创建一个
				if (null == trigger) {
					JobDetail jobDetail = JobBuilder.newJob(CustomScheduledJob.class)
							.withIdentity(job.getJobName(), job.getJobGroup()).build();
					jobDetail.getJobDataMap().put("scheduleJob", job);
					// 表达式调度构建器
					CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
					// 按新的cronExpression表达式构建一个新的trigger
					trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
							.withSchedule(scheduleBuilder).build();
					scheduler.scheduleJob(jobDetail, trigger);
				} else { // Trigger已存在，那么更新相应的定时设置 //表达式调度构建器
					String originCronExpression = trigger.getCronExpression();
					if (!originCronExpression.equalsIgnoreCase(job.getCronExpression())) {
						CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
						// 按新的cronExpression表达式重新构建trigger
						trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder)
								.build();
						// 按新的trigger重新设置job执行
						scheduler.rescheduleJob(triggerKey, trigger);
					}

				}
			}
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "OK";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/quartz2", method = { RequestMethod.GET })
	public String quartz2() {
		logger.info("entry sap test controller: quartz");
		// schedulerFactoryBean 由spring创建注入
		// ApplicationContext ctx = new
		// ClassPathXmlApplicationContext("applicationContext.xml");
		System.out.println(ctx);
		Scheduler scheduler = (Scheduler) ctx.getBean("schedulerFactoryBean");
		try {
			System.out.println(scheduler); // 这里获取任务信息数据
			List<ScheduleJob> jobList = new ArrayList<ScheduleJob>();
			for (int i = 0; i < 2; i++) {
				ScheduleJob job = new ScheduleJob();
				job.setJobId("" + i);
				job.setJobName("JobName_" + i);
				job.setJobGroup("dusto");
				job.setJobStatus("3");
				job.setCronExpression("0/5 * * * * ?");
				job.setClassName("com.dusto.mobile.biz.scheduler.job.CustomScheduledJob");
				job.setJobDesc("数据导入任务");
				jobList.add(job);
			}
			for (ScheduleJob job : jobList) {
				TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
				JobKey jobKey = new JobKey(job.getJobName(), job.getJobGroup());
				if("1".equals(job.getJobStatus())){
				// 获取trigger，即在spring配置文件中定义的 bean id="myTrigger"
				CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey); // 不存在，创建一个
				if (null == trigger) {
					@SuppressWarnings("unchecked")
					Class <? extends Job> className = (Class<? extends Job>) Class.forName(job.getClassName());
					JobDetail jobDetail = JobBuilder.newJob(className)
							.withIdentity(job.getJobName(), job.getJobGroup()).build();
					jobDetail.getJobDataMap().put("scheduleJob", job);
					// 表达式调度构建器
					CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
					// 按新的cronExpression表达式构建一个新的trigger
					trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
							.withSchedule(scheduleBuilder).build();
					scheduler.scheduleJob(jobDetail, trigger);
				} else { // Trigger已存在，那么更新相应的定时设置 //表达式调度构建器
					String originCronExpression = trigger.getCronExpression();
					if (!originCronExpression.equalsIgnoreCase(job.getCronExpression())) {
						CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
						// 按新的cronExpression表达式重新构建trigger
						trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder)
								.build();
						// 按新的trigger重新设置job执行
						scheduler.rescheduleJob(triggerKey, trigger);
					}
				}
				}else if ("3".equals(job.getJobStatus())){
//					scheduler.shutdown(false);
//					scheduler.getJobDetail(jobKey).
					scheduler.deleteJob(jobKey);
				}else if ("2".equals(job.getJobStatus())){
//					scheduler.shutdown(false);
//					scheduler.getJobDetail(jobKey).
					scheduler.pauseJob(jobKey);
				}
			}
		} catch (SchedulerException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "OK";
	}
	
	@Autowired
	private MasterDataService masterDataService;
	
	/**
	 * 获取用户
	 */
	@RequestMapping(value = "/binding", method = { RequestMethod.POST })
	@ResponseBody
	public int binding(@RequestBody InboundServiceRequest req) throws IOException {
		logger.info("entry SapTestController:binding input parameters:" + JSON.toJSONString(req));
		List<String> zxhList = req.getZxhList();
		int ret = masterDataService.updateMasterdataFlag(DustoConstant.ZXH_STATUS_BINDING, req.getZtp(),req.getWarehouseId(), zxhList);
		
		logger.info("exit SapTestController:binding output parameters:" + ret);
		return ret;
	}
	
}
