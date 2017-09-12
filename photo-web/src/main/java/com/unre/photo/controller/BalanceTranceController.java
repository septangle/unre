package com.unre.photo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.request.BalanceTraceRequest;
import com.unre.photo.biz.response.BalanceTranceResponse;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/balance")
public class BalanceTranceController {

	@ApiOperation(value = "充值", httpMethod = "POST", response = BalanceTranceResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "balanceTraceDto.memberId", value = "会员id", required = true, dataType = "Long"),
			@ApiImplicitParam(name = "balanceTraceDto.amount", value = "充值金额", required = true, dataType = "string"),
			@ApiImplicitParam(name = "balanceTraceDto.remark", value = "备注", required = true, dataType = "string")})
	@RequestMapping(value = "/recharge.do", method = RequestMethod.POST)
	public @ResponseBody BalanceTranceResponse recharge(@RequestBody BalanceTraceRequest request,
			HttpServletRequest servletRequest) throws BusinessException {
				return null;
}
}
