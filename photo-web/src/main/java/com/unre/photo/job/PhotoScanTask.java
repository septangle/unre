package com.unre.photo.job;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unre.photo.biz.dto.PanoramaEngineDto;
import com.unre.photo.biz.logic.core.IOrderEngineBiz;
import com.unre.photo.biz.logic.core.IPanoramaEngineBiz;
import com.unre.photo.biz.logic.facade.IPanoramaEngineFacade;
import com.unre.photo.comm.AppConstants;

@Component("photoScanTask")
public class PhotoScanTask {
	
	Log logger = LogFactory.getLog(PhotoScanTask.class);
	
	@Autowired
	IPanoramaEngineFacade panoramaEngineFacade;
	
	@Autowired 
	IOrderEngineBiz orderEngineBiz;
	
	public void startPanoramaProcessTask() {

        logger.info("处理Panorama任务开始...");
        try {
        	panoramaEngineFacade.startPanoramaProcess();
        } catch (Exception e) {
            logger.error("处理Panorama任务失败", e);
        }
        logger.info("处理Panorama任务结束。");
    }
	
	public void updateOrderAndBalanceTask() {

        logger.info("同步Benaco状态任务开始...");
        try {
        	orderEngineBiz.updateOrderAndBalance();
        } catch (Exception e) {
            logger.error("同步Benaco状态任务失败", e);
        }
        logger.info("同步Benaco状态任务结束。");
    }
	
}
