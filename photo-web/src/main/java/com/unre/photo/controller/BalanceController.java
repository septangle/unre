package com.unre.photo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unre.photo.biz.request.BalanceRequest;
import com.unre.photo.biz.response.BalanceResponse;

import com.unre.photo.biz.logic.facade.IBalanceFacade;

import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;


@Controller
@RequestMapping("/balance")
public class BalanceController extends BaseController<BalanceController> {

	@Autowired
	private IBalanceFacade balanceFacade;

	/**
	 * 
	 * @param request
	 * @return 
	 */
	@ApiOperation(value = "取得余额信息", httpMethod = "POST", response = BalanceResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "balanceDto.memberId", value = "会员ID", required = true, dataType = "long")})
	@RequestMapping(value = "/balance.do", method = RequestMethod.POST)
	public @ResponseBody BalanceResponse recharge(@RequestBody BalanceRequest request,
			HttpServletRequest servletRequest) throws Exception {
		
		return balanceFacade.selectBalance(request);
	}
}
