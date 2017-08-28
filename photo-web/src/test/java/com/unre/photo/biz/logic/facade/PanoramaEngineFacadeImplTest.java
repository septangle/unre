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

import com.unre.photo.biz.dto.PanoramaEngineDto;
import com.unre.photo.biz.request.PanoramaEngineRequest;
import com.unre.photo.biz.response.PanoramaEngineResponse;

@ContextConfiguration(locations = { "classpath:spring/photo_web_spring_test.xml" })
@TransactionConfiguration(transactionManager = "transactionManager")
//public class PanoramaEngineFacadeImplTest extends AbstractTransactionalJUnit4SpringContextTests {
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
	
			String jpgsPath = "D:/jpgs/";
			List<File> files = new ArrayList<File>();
			for(int i=1;i<=5;i++){
				File f = new File(jpgsPath + i +".jpg");
				files.add(f);
			}
			peDto.setFiles(files);
			
			request.setPanoramaEngineDto(peDto);
			
			PanoramaEngineResponse createResponse = panoramaEngineFacade.createScan(request);
			Assert.assertNotNull(createResponse);
			
			PanoramaEngineResponse addPhotosResponse = panoramaEngineFacade.addPhotos(request);
			Assert.assertNotNull(addPhotosResponse);
			
			PanoramaEngineResponse startProcessResponse = panoramaEngineFacade.startProcessing(request);
			Assert.assertNotNull(startProcessResponse);
			
			PanoramaEngineResponse queryStatusResponse = panoramaEngineFacade.queryScanStatus(request);
			Assert.assertNotNull(queryStatusResponse);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	/*@SuppressWarnings("unused")
	public void testGenerateScan() {
		try {
			PanoramaEngineRequest request = new PanoramaEngineRequest();
			PanoramaEngineDto peDto = new PanoramaEngineDto();
			//peDto.setApiUrl("https://beta.benaco.com/api/beta/scans/new");
			peDto.setApiBaseUrl("https://beta.benaco.com/api/beta/scans/");
			peDto.setApiKey("3c7c6941-2204-4ee7-a4b5-0981e0e6e09c");
			peDto.setTitle("test-01");
			File f = new File("D:/11.jpg");
			List<File> files = new ArrayList<File>();
			files.add(f);
			peDto.setFiles(files);
			request.setPanoramaEngineDto(peDto);
			PanoramaEngineResponse response = panoramaEngineFacade.generateScan(request);

			Assert.assertNotNull(response);

			PanoramaEngineResponse response1 = panoramaEngineFacade.queryScanStatus(request);

			Assert.assertNotNull(response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

}
