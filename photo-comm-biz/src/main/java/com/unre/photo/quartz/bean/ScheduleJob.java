package com.unre.photo.quartz.bean;


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.unre.photo.biz.logic.core.IPhotoScanBiz;
import com.unre.photo.util.SpringContextUtil;

//有一个新的任务调度  要实现JOB接口
public class ScheduleJob implements Job{
	
	public static final String PARAM_DATA = "jobParam";
	
	@Autowired
	private IPhotoScanBiz iPhotoScanBiz;
	
	@Override
	//execute方法是定时任务调度要执行的方法   此方法写业务逻辑
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		iPhotoScanBiz = (IPhotoScanBiz)SpringContextUtil.getBean("photoScan");
        System.err.println("ScheduleJob.execute() | 成功执行。。。");
		iPhotoScanBiz.queryStatus();
	}
	
}
