package com.unre.photo.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import com.unre.photo.comm.dal.model.Process;
import com.unre.photo.util.HttpClientResponse;
import com.unre.photo.util.HttpClientUtil;

import net.sf.json.JSONObject;

public class ScheduleJob implements Job{
	
	public static final String PARAM_DATA = "jobParam";
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		
	}
	
}
