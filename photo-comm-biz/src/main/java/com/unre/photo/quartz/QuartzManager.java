package com.unre.photo.quartz;

public interface QuartzManager {

	
	@SuppressWarnings("rawtypes")
	public boolean addJob(Class jobClass, String jobName, String jobGroup, String cron);
}
