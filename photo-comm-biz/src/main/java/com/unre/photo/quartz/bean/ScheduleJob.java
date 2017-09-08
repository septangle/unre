package com.unre.photo.quartz.bean;

import java.util.TimerTask;

import com.unre.photo.util.SpringContextUtil;
import com.unre.photo.biz.logic.core.IOrderEngineBiz;

//有一个新的任务调度  要实现JOB接口
public class ScheduleJob extends TimerTask{
	
	public static final String PARAM_DATA = "jobParam";
	
	private IOrderEngineBiz iorderEngineBiz;

	public IOrderEngineBiz getIorderEngineBiz() {
		return iorderEngineBiz;
	}

	public void setIorderEngineBiz(IOrderEngineBiz iorderEngineBiz) {
		this.iorderEngineBiz = iorderEngineBiz;
	}

	public static String getParamData() {
		return PARAM_DATA;
	}

	@Override
	public void run() {
		/*IProcessBiz = (IOrderBiz)SpringContextUtil.getBean("Process");
        System.err.println("ScheduleJob.execute() | 成功执行。。。       " + IProcessBiz);
        IProcessBiz.updateStatus();*/
		
		iorderEngineBiz = (IOrderEngineBiz)SpringContextUtil.getBean("Process");
        System.err.println("ScheduleJob.execute() | 成功执行:  " + iorderEngineBiz);
        iorderEngineBiz.updateOrderAndBalance();
	}
}
