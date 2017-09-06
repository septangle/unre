package com.unre.photo.quartz.bean;


import java.util.TimerTask;

import com.unre.photo.biz.logic.core.IOrderBiz;
import com.unre.photo.util.SpringContextUtil;

//有一个新的任务调度  要实现JOB接口
public class ScheduleJob extends TimerTask{
	
	public static final String PARAM_DATA = "jobParam";
	
	private IOrderBiz IProcessBiz;
	
	

	public IOrderBiz getIProcessBiz() {
		return IProcessBiz;
	}

	public void setIProcessBiz(IOrderBiz IProcessBiz) {
		this.IProcessBiz = IProcessBiz;
	}

	@Override
	public void run() {
		IProcessBiz = (IOrderBiz)SpringContextUtil.getBean("Process");
        System.err.println("ScheduleJob.execute() | 成功执行。。。       " + IProcessBiz);
        IProcessBiz.updateStatus();
	}
	
	
	
}
