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
   private IPanoramaFacade ipanoramaFacade;
   
   
   //删除Panorama场景
	@ApiOperation(value = "删除Panorama", httpMethod = "POST", response = PanoramaResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "panoramaDto.id", value = "ID", required = true, dataType = "long")})
	@RequestMapping(value = "/deletePanorama.do", method = RequestMethod.POST)
	public @ResponseBody PanoramaResponse deleteProcess(@RequestBody PanoramaRequest request,
			HttpServletRequest servletRequest) throws Exception {
		return ipanoramaFacade.updatePanorama(request);
	}
	
    //根据场景ID取得场景信息
	@ApiOperation(value = "查询Panorama", httpMethod = "POST", response = PanoramaResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "panoramaDto.id", value = "ID", required = true, dataType = "long")})
	@RequestMapping(value = "/findPanorama.do", method = RequestMethod.POST)
	public @ResponseBody PanoramaResponse findPanoramaById(@RequestBody PanoramaRequest request,
			HttpServletRequest servletRequest) throws Exception {
		return ipanoramaFacade.findProcessSourceById(request);
	}
}