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

import com.unre.photo.biz.dto.OrderDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.facade.IOrderFacade;
import com.unre.photo.biz.request.OrderRequest;
import com.unre.photo.biz.response.OrderResponse;
import com.unre.photo.comm.AppConstants;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;


@Controller
@RequestMapping("/order")
public class OrderController extends BaseController<OrderController> {

	@Autowired
	private IOrderFacade orderFacade;
	


	 //查询当前用户所有场景
	@ApiOperation(value = "查询当前用户panorama", httpMethod = "GET", response = OrderResponse.class)
	@RequestMapping(value = "/getCurrMemberScan.do", method = RequestMethod.GET)
	public @ResponseBody OrderResponse findCurrMemberProcessById(HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		Long id = (Long) session.getAttribute("ID");
		if(id == null) 
			throw new BusinessException(AppConstants.MEMBER_NOT_LOGIN_ERROR_CODE,AppConstants.MEMBER_NOT_LOGIN_ERROR_MESSAGE);
		OrderRequest request = new OrderRequest();
		OrderDto orderDto =  new OrderDto();
        orderDto.setMemberId(id);
        request.setOrderDto(orderDto);
		return orderFacade.queryStatus(request);
	}

	
	
}