package com.unre.photo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unre.photo.biz.request.BalanceTraceRequest;
import com.unre.photo.biz.response.BalanceTraceResponse;

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
}
