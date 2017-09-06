package com.unre.photo.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware{
	private static ApplicationContext ctx;
	@SuppressWarnings("static-access")
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.ctx = applicationContext;		
	}
	
	public static ApplicationContext getCtx(){
		return ctx;
	}
	
	public static Object getBean(String name) throws BeansException { 
        return ctx.getBean(name); 
	} 
}
