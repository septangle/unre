package com.unre.photo.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.unre.photo.biz.dto.OrderDto;
import com.unre.photo.biz.dto.PanoramaEngineDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.facade.IOrderFacade;
import com.unre.photo.biz.logic.facade.IPanoramaEngineFacade;
import com.unre.photo.biz.request.OrderRequest;
import com.unre.photo.biz.request.PanoramaEngineRequest;
import com.unre.photo.biz.response.OrderResponse;
import com.unre.photo.biz.response.PanoramaEngineResponse;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.util.FileUploadUtil;
import com.unre.photo.util.PhotoUrl;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;

@Controller
@RequestMapping("/engine")
public class PanoramaEngineController extends BaseController<PanoramaEngineController> {

	@Autowired
	private IPanoramaEngineFacade panoramaEngineFacade;//photo upload
	
	@Autowired 
	private IOrderFacade iOrderFacade;

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
		Long merberId = (Long) session.getAttribute("memberId");
		if (merberId == null) {
			throw new BusinessException(AppConstants.MEMBER_NOT_LOGIN_ERROR_CODE,
					AppConstants.MEMBER_NOT_LOGIN_ERROR_MESSAGE);
		}
		List<File> fileUrlList =FileUploadUtil.Upload(files, number, merberId);
		request.getPanoramaEngineDto().setApiKey(photoUrl.getKey());
		request.getPanoramaEngineDto().setApiBaseUrl(photoUrl.getUrl());
		request.getPanoramaEngineDto().setTitle(title);
		request.getPanoramaEngineDto().setFiles(fileUrlList);
		request.getPanoramaEngineDto().setNumber(number);
		request.getPanoramaEngineDto().setUid(merberId);
		return panoramaEngineFacade.addPhotos(request);
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

	/**
	 * 2D-->3D拼接后上传
	 * @param request
	 * @return resp
	 */
	@RequestMapping(value = "/addStitchedPhotos.do", method = RequestMethod.POST)
	public @ResponseBody PanoramaEngineResponse addPhotoStitchCompleted(@RequestParam("orderId") String orderId,
			@RequestParam MultipartFile[] files,HttpServletRequest servletRequest) throws Exception {
		PanoramaEngineRequest request = new PanoramaEngineRequest();
		PanoramaEngineDto panoramaEngineDto = new PanoramaEngineDto();
		request.setPanoramaEngineDto(panoramaEngineDto);
		OrderDto orderDto = new OrderDto();
		OrderRequest orderRequest = new OrderRequest();
		orderDto.setId(Long.parseLong(orderId));
		orderRequest.setOrderDto(orderDto);
		//1、取memberId
		OrderResponse orderResponse=iOrderFacade.queryOrder(orderRequest);
		orderDto=orderResponse.getOrderDtoList().get(0);
        String number =AppConstants.NUMBER_MESSAGE_3D;
        //2、上传图片至server
		List<File> fileUrlList =FileUploadUtil.Upload(files, number, orderDto.getMemberId());
        Long orderIdParam=Long.parseLong(orderId);
        request.getPanoramaEngineDto().setOrderId(orderIdParam);
        request.getPanoramaEngineDto().setFiles(fileUrlList);
        //3、保存数据库
		return panoramaEngineFacade.addPhotoStitchCompleted(request);
	}

}
