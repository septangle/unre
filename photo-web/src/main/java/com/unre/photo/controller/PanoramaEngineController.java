package com.unre.photo.controller;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.facade.IPanoramaEngineFacade;
import com.unre.photo.biz.request.PanoramaEngineRequest;
import com.unre.photo.biz.response.PanoramaEngineResponse;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.util.PhotoUrl;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;

@Controller
@RequestMapping("/engine")
public class PanoramaEngineController extends BaseController<PanoramaEngineController> {

	@Autowired
	private IPanoramaEngineFacade panoramaEngineFacade;//photo upload

	@Autowired
	private PhotoUrl photoUrl; //key、url

	/**
	 * 新建场景（上传全景照片）
	 * @param request
	 * @return resp
	 */
	/*	@ApiImplicitParams({
				@ApiImplicitParam(name = "panoramaEngineDto.title", value = "scan名称", required = true, dataType = "string"),
				@ApiImplicitParam(name = "panoramaEngineDto.files", value = "files", required = true, dataType = "List<File>") })*/
	@RequestMapping(value = "/addPanoramas.do", method = RequestMethod.POST)
	public @ResponseBody PanoramaEngineResponse addPhotos(@RequestParam MultipartFile[] files,
			@RequestParam("title") String title, HttpServletRequest servletRequest) throws Exception {
		PanoramaEngineRequest request = new PanoramaEngineRequest();
		HttpSession session = servletRequest.getSession();
		Long id = (Long) session.getAttribute("ID");
		//查询当前用户
		if (id == null)
			throw new BusinessException(AppConstants.MEMBER_NOT_LOGIN_ERROR_CODE,
					AppConstants.MEMBER_NOT_LOGIN_ERROR_MESSAGE);
        request.getPanoramaEngineDto().setTitle(title);
		request.getPanoramaEngineDto().setUid(id);
		request.getPanoramaEngineDto().setApiKey(photoUrl.getKey());
		request.getPanoramaEngineDto().setApiBaseUrl(photoUrl.getUrl());
		List<File> fileUrlList = new ArrayList<File>(); //用来保存文件路径，
		  for(MultipartFile file : files){ 
			  if(file.isEmpty()){  
	                System.out.println("文件未上传");  
	            }else{
	            	  System.out.println("文件长度: " + file.getSize());  
	                  System.out.println("文件类型: " + file.getContentType());  
	                  System.out.println("文件名称: " + file.getName());  
	                  System.out.println("文件原名: " + file.getOriginalFilename());
	                  String path=photoUrl.getPath()+file.getOriginalFilename();
	                  FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path, file.getOriginalFilename()));
	                  File f= new File(path);
	                  fileUrlList.add(f);
	            }
		  }
          request.getPanoramaEngineDto().setFiles(fileUrlList);
          return panoramaEngineFacade.addPhotos(request);
	}

	/**
	 * 处理照片
	 * @param request
	 * @return resp
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(name = "panoramaEngineDto.benacoScanId", value = "Benaco Scan Id", required = true, dataType = "string"),
			@ApiImplicitParam(name = "panoramaEngineDto.orderId", value = "orderId", required = true, dataType = "Long") })
	@RequestMapping(value = "/processPanoramas.do", method = RequestMethod.POST)
	public @ResponseBody PanoramaEngineResponse startProcessing(@RequestBody PanoramaEngineRequest request,
			HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		Long id = (Long) session.getAttribute("ID");
		//查询当前用户
		if (id == null)
			throw new BusinessException(AppConstants.MEMBER_NOT_LOGIN_ERROR_CODE,
					AppConstants.MEMBER_NOT_LOGIN_ERROR_MESSAGE);
		request.getPanoramaEngineDto().setUid(id);
		request.getPanoramaEngineDto().setApiKey(photoUrl.getKey());
		request.getPanoramaEngineDto().setApiBaseUrl(photoUrl.getUrl());
		return panoramaEngineFacade.startProcessing(request);
	}

	/*@ApiImplicitParams({
			@ApiImplicitParam(name = "panoramaEngineDto.benacoScanId", value = "Benaco Scan Id", required = true, dataType = "string"), })
	@RequestMapping(value = "/queryScanStatus.do", method = RequestMethod.POST)
	public @ResponseBody PanoramaEngineResponse queryScanStatus(@RequestBody PanoramaEngineRequest request,
			HttpServletRequest servletRequest) throws Exception {
		request.getPanoramaEngineDto().setApiKey(photoUrl.getKey());
		request.getPanoramaEngineDto().setApiBaseUrl(photoUrl.getUrl());
		return panoramaEngineFacade.queryScanStatus(request);
	}*/
	
}
