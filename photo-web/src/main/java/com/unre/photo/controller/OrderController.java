package com.unre.photo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unre.photo.biz.dto.MemberDto;
import com.unre.photo.biz.dto.OrderDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.facade.IMemberFacade;
import com.unre.photo.biz.logic.facade.IOrderFacade;
import com.unre.photo.biz.request.MemberRequest;
import com.unre.photo.biz.request.OrderRequest;
import com.unre.photo.biz.response.MemberResponse;
import com.unre.photo.biz.response.OrderResponse;
import com.unre.photo.comm.AppConstants;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/order")
public class OrderController extends BaseController<OrderController> {

	@Autowired
	private IOrderFacade orderFacade;//订单

	@Autowired
	private IMemberFacade memberFacade; //会员

	/**
	 * 查询当前会员所有场景
	 * @param ID
	 * @return processDtoList
	 */
	@ApiOperation(value = "查询当前用户panorama", httpMethod = "GET", response = OrderResponse.class)
	@RequestMapping(value = "/getCurrMemberScan.do", method = RequestMethod.GET)
	public @ResponseBody OrderResponse findCurrMemberPanorama(HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		Long memberId = (Long) session.getAttribute("memberId");
		if (memberId == null) {
			throw new BusinessException(AppConstants.MEMBER_NOT_LOGIN_ERROR_CODE,
					AppConstants.MEMBER_NOT_LOGIN_ERROR_MESSAGE);
		}
		MemberDto memberDto = new MemberDto();
		MemberRequest memberRequest = new MemberRequest();
		memberDto.setId(memberId);
		memberRequest.setMemberDto(memberDto);
		//根据id查询登录名是否是admin
		MemberResponse memberResponse = memberFacade.queryMember(memberRequest);
		memberDto = memberResponse.getMemberDtoList().get(0);
		//是则清空条件
		if (AppConstants.LOGIN_NAME.equals(memberDto.getMemberName())) {
			memberId = null;
		}
		OrderRequest request = new OrderRequest();
		OrderDto orderDto = new OrderDto();
		orderDto.setMemberId(memberId);
		request.setOrderDto(orderDto);
		return orderFacade.findCurrMemberPanorama(request);
	}

	/**
	 * 删除order场景(更新is_Deleted字段)
	 * @param ID
	 * @return resp
	 */
	@ApiOperation(value = "删除订单", httpMethod = "POST", response = OrderResponse.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "orderDto.id", value = "ID", required = true, dataType = "long") })
	@RequestMapping(value = "/removeOrderById.do", method = RequestMethod.POST)
	public @ResponseBody OrderResponse removeOrderById(@RequestBody OrderRequest request,
			HttpServletRequest servletRequest) throws Exception {
		return orderFacade.removeOrderById(request);
	}

	/**
	 * 根据status查询用户场景
	 * @param description
	 * @return resp
	 */
	@ApiOperation(value ="根据status查询场景",httpMethod="POST",response=OrderResponse.class)
	@ApiImplicitParams({@ApiImplicitParam(name="orderDto.status",value="status",required=true,dataType="string")})
	@RequestMapping(value="/queryScene.do",method=RequestMethod.POST)
	public @ResponseBody OrderResponse queryScene(@RequestBody OrderRequest request,
			HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		Long memberId = (Long) session.getAttribute("memberId");
		OrderDto orderDto=request.getOrderDto();
		orderDto.setMemberId(memberId);
		request.setOrderDto(orderDto);
		return orderFacade.findCurrMemberPanorama(request);
	}
	
	/**
	 * 根据member_id查询消费记录
	 * @param memberId
	 * @resp
	 */
	@ApiOperation(value = "查询当前用户消费记录", httpMethod = "GET", response = OrderResponse.class)
	@RequestMapping(value = "/findRecordsConsumption.do", method = RequestMethod.GET)
	public @ResponseBody OrderResponse findRecordsConsumption (HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		Long memberId = (Long) session.getAttribute("memberId");
		//判断用户是否登录
		if (memberId == null) {
			throw new BusinessException(AppConstants.MEMBER_NOT_LOGIN_ERROR_CODE,
					AppConstants.MEMBER_NOT_LOGIN_ERROR_MESSAGE);
		}
		OrderRequest orderRequest = new OrderRequest();
		OrderDto orderDto = new OrderDto();
		orderDto.setMemberId(memberId);
		orderRequest.setOrderDto(orderDto);
		return orderFacade.queryOrder(orderRequest);
	}
}
