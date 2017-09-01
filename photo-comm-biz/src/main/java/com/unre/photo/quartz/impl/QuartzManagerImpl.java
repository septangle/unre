package com.unre.photo.quartz.impl;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.unre.photo.quartz.QuartzManager;

@Component()
public class QuartzManagerImpl implements QuartzManager {
	
	@Autowired
	@Qualifier("scheduler")
	private Scheduler scheduler;
	
	public Scheduler getScheduler() {
		return scheduler;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}
	
	//添加任务的方法接口
	//第一个参数 class代表要执行任务的class   这个ScheduleJob，实现了job接口的类
	//第二个参数是指任务名称   这个要唯一不能重复    最好使用时间戳等算法保证唯一
	//第三个参数是指任务分组名称    这个可以重复    但是可以用来区分任务用的     
	//第四个参数是指任务表达式   秒 分 时 日 月 周 年   年可以不写。。。   参考http://blog.csdn.net/wangpeng047/article/details/13018757
	public boolean addJob(Class jobClass, String jobName, String jobGroup, String cron){
		
		
		boolean isAction = false;
		try {
			// 任务名，任务组，任务执行类
			JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroup).build();

			// 触发器
			TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
			
			// 触发器名,触发器组
			triggerBuilder.withIdentity(jobName, jobGroup);
			triggerBuilder.startNow();
			// 触发器时间设定
			triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
			// 创建Trigger对象
			CronTrigger trigger = (CronTrigger) triggerBuilder.build();
			//jobDetail.getJobDataMap().put(ScheduleJob.PARAM_DATA, null);
			// 调度容器设置JobDetail和Trigger
			scheduler.scheduleJob(jobDetail, trigger);
			// 启动
			if (!scheduler.isShutdown()) {
				scheduler.start();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		
		return isAction;
		
		
	}
	
	
	
	
}
