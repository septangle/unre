package com.unre.photo.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.unre.photo.biz.dto.PanoramaEngineDto;
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
	@RequestMapping(value = "/addPhotos.do", method = RequestMethod.POST)
	public @ResponseBody PanoramaEngineResponse addPhotos(@RequestParam("title") String title,
			@RequestParam MultipartFile[] files, @RequestParam("number") String number,
			HttpServletRequest servletRequest) throws Exception {
		PanoramaEngineRequest request = new PanoramaEngineRequest();
		PanoramaEngineDto panoramaEngineDto = new PanoramaEngineDto();
		request.setPanoramaEngineDto(panoramaEngineDto);
		HttpSession session = servletRequest.getSession();
		//查询当前用户
		Long merberId = (Long) session.getAttribute("MemberID");
		if (merberId == null)
			throw new BusinessException(AppConstants.MEMBER_NOT_LOGIN_ERROR_CODE,
					AppConstants.MEMBER_NOT_LOGIN_ERROR_MESSAGE);
		panoramaEngineDto.setTitle(title);
		panoramaEngineDto.setUid(merberId);
		panoramaEngineDto.setApiKey(photoUrl.getKey());
		panoramaEngineDto.setApiBaseUrl(photoUrl.getUrl());
		List<File> fileUrlList = new ArrayList<File>(); //用来保存文件路径，
    	String strFilePath = (AppConstants.NUMBER_MESSAGE_3D).equals(number) ? photoUrl.getParamoPath()
				: photoUrl.getPhotoPath();
		String strNowTime = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
		strFilePath = strFilePath + merberId.toString() + File.separator + strNowTime;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isEmpty()) {
				System.out.println("上传文件[" + i + "]为空");
			} else {
				FileUtils.copyInputStreamToFile(files[i].getInputStream(),
						new File(strFilePath, files[i].getOriginalFilename()));
				File f = new File(strFilePath + File.separator + files[i].getOriginalFilename());
				fileUrlList.add(f);
			}
		}
		request.getPanoramaEngineDto().setApiKey(photoUrl.getKey());
		request.getPanoramaEngineDto().setApiBaseUrl(photoUrl.getUrl());
		request.getPanoramaEngineDto().setTitle(title);
		request.getPanoramaEngineDto().setFiles(fileUrlList);
		request.getPanoramaEngineDto().setNumber(number);
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
		Long memberId = (Long) session.getAttribute("MemberID");
		//查询当前用户
		if (memberId == null)
			throw new BusinessException(AppConstants.MEMBER_NOT_LOGIN_ERROR_CODE,
					AppConstants.MEMBER_NOT_LOGIN_ERROR_MESSAGE);
		request.getPanoramaEngineDto().setUid(memberId);
		request.getPanoramaEngineDto().setApiKey(photoUrl.getKey());
		request.getPanoramaEngineDto().setApiBaseUrl(photoUrl.getUrl());
		return panoramaEngineFacade.startProcessing(request);
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "panoramaEngineDto.benacoScanId", value = "Benaco Scan Id", required = true, dataType = "string"), })
	@RequestMapping(value = "/queryScanStatus.do", method = RequestMethod.POST)
	public @ResponseBody PanoramaEngineResponse queryScanStatus(@RequestBody PanoramaEngineRequest request,
			HttpServletRequest servletRequest) throws Exception {
		request.getPanoramaEngineDto().setApiKey(photoUrl.getKey());
		request.getPanoramaEngineDto().setApiBaseUrl(photoUrl.getUrl());
		return panoramaEngineFacade.queryScanStatus(request);
	}

}
