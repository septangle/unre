/**
 * 
 */
package com.unre.photo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unre.photo.biz.dto.ProcessDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.facade.IProcessFacade;
import com.unre.photo.biz.request.ProcessRequest;
import com.unre.photo.biz.response.ProcessResponse;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.quartz.QuartzManager;
import com.unre.photo.quartz.bean.ScheduleJob;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;


@Controller
@RequestMapping("/process")
public class ProcessController extends BaseController<ProcessController> {

	@Autowired
	private IProcessFacade processFacade;
	


	
	@ApiOperation(value = "查询当前用户process", httpMethod = "GET", response = ProcessResponse.class)
	@RequestMapping(value = "/getCurrMemberScan.do", method = RequestMethod.GET)
	public @ResponseBody ProcessResponse findCurrMemberProcessById(HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		Long id = (Long) session.getAttribute("ID");
		if(id == null) 
			throw new BusinessException(AppConstants.MEMBER_NOT_LOGIN_ERROR_CODE,AppConstants.MEMBER_NOT_LOGIN_ERROR_MESSAGE);
		ProcessRequest request = new ProcessRequest();
		ProcessDto scanDto =  new ProcessDto();
		scanDto.setId(id);
		request.setProcessDto(scanDto);
		return processFacade.queryProcess(request);
	}
    
/*	@ApiOperation(value = "查询Process", httpMethod = "POST", response = ProcessResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ProcessDto.id", value = "Photo Scan ID", required = true, dataType = "long")})
	@RequestMapping(value = "/findProcessById.do", method = RequestMethod.POST)
	public @ResponseBody ProcessResponse findProcessById(@RequestBody ProcessRequest request,
			HttpServletRequest servletRequest) throws Exception {
		return processFacade.findProcessById(request);
	}
    */
/*	@ApiOperation(value = "更新Process", httpMethod = "POST", response = ProcessResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "ProcessDto.id", value = "Photo Scan ID", required = true, dataType = "long"),
			@ApiImplicitParam(name = "ProcessDto.memberId", value = "会员 ID", required = false, dataType = "long"),
			@ApiImplicitParam(name = "ProcessDto.benacoScanId", value = "scan名称", required = false, dataType = "long"),
			@ApiImplicitParam(name = "ProcessDto.btchNo", value = "上传批次", required = false, dataType = "long"),
			@ApiImplicitParam(name = "ProcessDto.title", value = "上传名称", required = false, dataType = "string"),
			@ApiImplicitParam(name = "ProcessDto.status", value = "scan状态", required = false, dataType = "string")})
	@RequestMapping(value = "/updateProcess.do", method = RequestMethod.POST)
	public @ResponseBody ProcessResponse updateProcess(ProcessRequest request) throws Exception {
		return processFacade.updateProcess(request);
	}
	*/
	@ApiOperation(value = "删除Process", httpMethod = "POST", response = ProcessResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "processDto.id", value = "ID", required = true, dataType = "long")})
	@RequestMapping(value = "/deleteProcess.do", method = RequestMethod.POST)
	public @ResponseBody ProcessResponse deleteProcess(@RequestBody ProcessRequest request,
			HttpServletRequest servletRequest) throws Exception {
		return processFacade.deleteProcess(request);
	}
	
	
}
