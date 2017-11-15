package com.unre.photo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unre.photo.biz.request.BalanceTraceRequest;
import com.unre.photo.biz.response.BalanceTraceResponse;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.biz.dto.BalanceTraceDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.facade.IBalanceTraceFacade;

import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;


@Controller
@RequestMapping("/balancetrace")
public class BalanceTraceController extends BaseController<BalanceTraceController> {

	@Autowired
	private IBalanceTraceFacade balanceFacade;

	/**
	 * 
	 * @param request
	 * @return 
	 */
	@ApiOperation(value = "充值退款", httpMethod = "POST", response = BalanceTraceResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "balanceTraceDto.memberId", value = "会员ID", required = true, dataType = "long"),
			@ApiImplicitParam(name = "balanceTraceDto.amount", value = "充值金额", required = true, dataType = "long"),
			@ApiImplicitParam(name = "balanceTraceDto.remark", value = "备注", required = false, dataType = "string"),
			@ApiImplicitParam(name = "balanceTraceDto.transType", value = "类型", required = true, dataType = "string")})
	@RequestMapping(value = "/recharge.do", method = RequestMethod.POST)
	public @ResponseBody BalanceTraceResponse recharge(@RequestBody BalanceTraceRequest request,
			HttpServletRequest servletRequest) throws Exception {
           	return balanceFacade.insertBalanceTrace(request);
	}
	
	/**
	 * @author zx
	 * 
	 * @param request
	 * @return BalanceTraceResponse
	 */
	@ApiOperation(value = "查询充值/退款记录", httpMethod = "GET", response = BalanceTraceResponse.class)
	@RequestMapping(value = "/getBalanceTraceRecord.do", method = RequestMethod.GET)
	public @ResponseBody BalanceTraceResponse findBalanceTraceRecord(HttpServletRequest servletRequest) throws Exception {
		
		HttpSession session = servletRequest.getSession();
		Long memberId = (Long) session.getAttribute("memberId");

		//判断用户是否登录
		if (memberId == null) {
			throw new BusinessException(AppConstants.MEMBER_NOT_LOGIN_ERROR_CODE,
					AppConstants.MEMBER_NOT_LOGIN_ERROR_MESSAGE);
		}
		
		BalanceTraceRequest request = new BalanceTraceRequest();
		BalanceTraceDto balanceTraceDto = new BalanceTraceDto();
		balanceTraceDto.setMemberId(memberId);
		request.setBalanceTraceDto(balanceTraceDto);
		
		return balanceFacade.findBalanceTraceByMemberId(request);
	
	
	}
	
}
