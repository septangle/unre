/**
 * 
 */
package com.unre.photo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unre.photo.biz.logic.facade.IPhotoScanFacade;
import com.unre.photo.biz.request.PhotoScanRequest;
import com.unre.photo.biz.response.PhotoScanResponse;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;


@Controller
@RequestMapping("/photoscan")
public class PhotoScanController extends BaseController<PhotoScanController> {

	@Autowired
	private IPhotoScanFacade photoScanFacade;

	
	@ApiOperation(value = "查询PhotoScan", httpMethod = "POST", response = PhotoScanResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "PhotoScanDto.id", value = "Photo Scan ID", required = false, dataType = "long"),
			@ApiImplicitParam(name = "PhotoScanDto.memberId", value = "会员 ID", required = false, dataType = "long"),
			@ApiImplicitParam(name = "PhotoScanDto.benacoScanId", value = "scan名称", required = false, dataType = "long"),
			@ApiImplicitParam(name = "PhotoScanDto.btchNo", value = "上传批次", required = false, dataType = "long"),
			@ApiImplicitParam(name = "PhotoScanDto.title", value = "上传名称", required = false, dataType = "string"),
			@ApiImplicitParam(name = "PhotoScanDto.status", value = "scan状态", required = false, dataType = "string")})
	@RequestMapping(value = "/queryPhotoScan.do", method = RequestMethod.POST)
	public @ResponseBody PhotoScanResponse queryPhotoScan(@RequestBody PhotoScanRequest request,
			HttpServletRequest servletRequest) throws Exception {
		return photoScanFacade.queryPhotoScan(request);
	}
    
	@ApiOperation(value = "查询PhotoScan", httpMethod = "POST", response = PhotoScanResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "PhotoScanDto.id", value = "Photo Scan ID", required = true, dataType = "long")})
	@RequestMapping(value = "/findPhotoScanById.do", method = RequestMethod.POST)
	public @ResponseBody PhotoScanResponse findPhotoScanById(@RequestBody PhotoScanRequest request,
			HttpServletRequest servletRequest) throws Exception {
		return photoScanFacade.findPhotoScanById(request);
	}
    
	@ApiOperation(value = "更新PhotoScan", httpMethod = "POST", response = PhotoScanResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "PhotoScanDto.id", value = "Photo Scan ID", required = true, dataType = "long"),
			@ApiImplicitParam(name = "PhotoScanDto.memberId", value = "会员 ID", required = false, dataType = "long"),
			@ApiImplicitParam(name = "PhotoScanDto.benacoScanId", value = "scan名称", required = false, dataType = "long"),
			@ApiImplicitParam(name = "PhotoScanDto.btchNo", value = "上传批次", required = false, dataType = "long"),
			@ApiImplicitParam(name = "PhotoScanDto.title", value = "上传名称", required = false, dataType = "string"),
			@ApiImplicitParam(name = "PhotoScanDto.status", value = "scan状态", required = false, dataType = "string")})
	@RequestMapping(value = "/updatePhotoScan.do", method = RequestMethod.POST)
	public @ResponseBody PhotoScanResponse updatePhotoScan(PhotoScanRequest request) throws Exception {
		return photoScanFacade.updatePhotoScan(request);
	}
	
	@ApiOperation(value = "删除PhotoScan", httpMethod = "POST", response = PhotoScanResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "PhotoScanDto.id", value = "Photo Scan ID", required = true, dataType = "long")})
	@RequestMapping(value = "/deletephotoMember.do", method = RequestMethod.POST)
	public @ResponseBody PhotoScanResponse deletePhotoScan(Long id) throws Exception {
		return photoScanFacade.deletePhotoScan(id);
	}
}
