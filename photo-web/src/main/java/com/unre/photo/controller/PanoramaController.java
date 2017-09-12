package com.unre.photo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unre.photo.biz.logic.facade.IPanoramaFacade;
import com.unre.photo.biz.request.PanoramaRequest;
import com.unre.photo.biz.response.PanoramaResponse;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/Panorama")
public class PanoramaController extends BaseController<OrderController>{
   @Autowired
   private IPanoramaFacade ipanoramaFacade;//3D照片(包含2D生成)
  
	/**
	 * 删除Panorama场景(做更新ID操作)
	 * @param ID
	 * @return resp
	 */
	@ApiOperation(value = "删除Panorama", httpMethod = "POST", response = PanoramaResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "panoramaDto.id", value = "ID", required = true, dataType = "long")})
	@RequestMapping(value = "/deletePanorama.do", method = RequestMethod.POST)
	public @ResponseBody PanoramaResponse deleteProcess(@RequestBody PanoramaRequest request,
			HttpServletRequest servletRequest) throws Exception {
		//根据id进行更新操作
		return ipanoramaFacade.updatePanorama(request);
	}
	
	/**
	 * 根据ID查询场景
	 * @param ID
	 * @return resp
	 */
	@ApiOperation(value = "查询Panorama", httpMethod = "POST", response = PanoramaResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "panoramaDto.id", value = "ID", required = true, dataType = "long")})
	@RequestMapping(value = "/findPanorama.do", method = RequestMethod.POST)
	public @ResponseBody PanoramaResponse findPanoramaById(@RequestBody PanoramaRequest request,
			HttpServletRequest servletRequest) throws Exception {
		return ipanoramaFacade.findProcessSourceById(request);
	}
}
