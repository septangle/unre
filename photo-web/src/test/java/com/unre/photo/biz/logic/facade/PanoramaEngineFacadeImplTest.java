package com.unre.photo.biz.logic.facade;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.unre.photo.biz.dto.ImageInfoDto;
import com.unre.photo.biz.dto.PanoramaEngineDto;
import com.unre.photo.biz.request.PanoramaEngineRequest;
import com.unre.photo.biz.response.PanoramaEngineResponse;

@ContextConfiguration(locations = { "classpath:spring/photo_web_spring_test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager")
public class PanoramaEngineFacadeImplTest extends AbstractJUnit4SpringContextTests{
	@Autowired
	private IPanoramaEngineFacade panoramaEngineFacade;
    
	@Test@Rollback(false)
	public void testGenScanStepByStep() {
		try {
			PanoramaEngineRequest request = new PanoramaEngineRequest();
			PanoramaEngineDto peDto = new PanoramaEngineDto();
			peDto.setApiBaseUrl("https://beta.benaco.com/api/beta/scans/");
			peDto.setApiKey("3c7c6941-2204-4ee7-a4b5-0981e0e6e09c");
			peDto.setTitle("test-001");
	
			String jpgsPath = "C:/jpgs/";
			List<ImageInfoDto> imageInfoList = new ArrayList<ImageInfoDto>();
			for(int i=1;i<=5;i++){
				ImageInfoDto imageInfo = new ImageInfoDto();
				imageInfo.setFullPath(jpgsPath + i +".jpg");
				imageInfoList.add(imageInfo);
			}
			peDto.setImageInfoList(imageInfoList);
			peDto.setBenacoScanId("6a079061-680e-4cf6-a2f6-62df9bd53a6a");
			request.setPanoramaEngineDto(peDto);
			/*
			PanoramaEngineResponse createResponse = panoramaEngineFacade.createScan(request);
			Assert.assertNotNull(createResponse);
			
			PanoramaEngineResponse addPhotosResponse = panoramaEngineFacade.addPhotos(request);
			Assert.assertNotNull(addPhotosResponse);
			
			PanoramaEngineResponse startProcessResponse = panoramaEngineFacade.startProcessing(request);
			Assert.assertNotNull(startProcessResponse);
            */
			
			//PanoramaEngineResponse startProcessResponse = panoramaEngineFacade.startPanoramaProcess();
			//Assert.assertNotNull(startProcessResponse);
			
			PanoramaEngineResponse queryStatusResponse = panoramaEngineFacade.queryScanStatus(request);
			Assert.assertNotNull(queryStatusResponse);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test@Rollback(false)
	public void testStartPanoramaProcess() {
		try {
			PanoramaEngineRequest request = new PanoramaEngineRequest();
			PanoramaEngineDto peDto = new PanoramaEngineDto();
			peDto.setApiBaseUrl("https://beta.benaco.com/api/beta/scans/");
			peDto.setApiKey("3c7c6941-2204-4ee7-a4b5-0981e0e6e09c");
			request.setPanoramaEngineDto(peDto);
			PanoramaEngineResponse response = panoramaEngineFacade.startPanoramaProcess();
			Assert.assertNotNull(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
