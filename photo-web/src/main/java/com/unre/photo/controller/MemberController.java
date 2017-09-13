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
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.facade.IMemberFacade;
import com.unre.photo.biz.request.MemberRequest;
import com.unre.photo.biz.response.MemberResponse;
import com.unre.photo.biz.response.PriceRespnose;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.util.MD5Util;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController<MemberController> {

	@Autowired
	private IMemberFacade memberFacade; //会员

	/**
	 * 查询当前会员
	 * @param ID
	 * @return Member
	 */
	@ApiOperation(value = "查询当前会员", httpMethod = "GET", response = MemberResponse.class)
	@RequestMapping(value = "/getCurrMember", method = RequestMethod.GET)
	public @ResponseBody MemberResponse findCurrMemberById(HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		//根据缓存ID查询当前登录会员
		Long id = (Long) session.getAttribute("ID");
		if (id == null)
			throw new BusinessException(AppConstants.MEMBER_NOT_LOGIN_ERROR_CODE,
					AppConstants.MEMBER_NOT_LOGIN_ERROR_MESSAGE);
		MemberRequest request = new MemberRequest();
		MemberDto memberDto = new MemberDto();
		memberDto.setId(id);
		request.setMemberDto(memberDto);
		//根据ID查询
		return memberFacade.findMemberById(request);
	}

	/**
	 * 登录
	 * @param request
	 * @return Member
	 */
	@ApiOperation(value = "登录", httpMethod = "POST", response = MemberResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "memberDto.tel", value = "联系电话", required = true, dataType = "string"),
			@ApiImplicitParam(name = "memberDto.password", value = "密码", required = true, dataType = "string") })
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public @ResponseBody MemberResponse queryLoginUsers(@RequestBody MemberRequest request,
			HttpServletRequest servletRequest) throws BusinessException {
		MemberResponse MemberResponse = null;
		HttpSession session = servletRequest.getSession();
		MemberDto phonto = (MemberDto) session.getAttribute("MemberDto");
		//判断会员是否登录
		if (phonto != null) {
			throw new BusinessException(AppConstants.QUERY_LOGIN_USERLOING_ERROR_CODE,
					AppConstants.QUERY_LOGIN_USERLOING_ERROR_MESSAGE);
		}
		try {//MD5加密
			request.getMemberDto().setPassword(MD5Util.encodeMD5String(request.getMemberDto().getPassword()));
			MemberResponse = memberFacade.login(request);
			//判断对象是否为空，并放入session
			if (MemberResponse != null && MemberResponse.getMemberDto() != null) {
				servletRequest.getSession().setAttribute("MemberDto", MemberResponse.getMemberDto());
				servletRequest.getSession().setAttribute("ID", MemberResponse.getMemberDto().getId());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MemberResponse;
	}

	/**
	 * 注册
	 * @param request
	 * @return Member
	 */
	@ApiOperation(value = "注册", httpMethod = "POST", response = MemberResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "memberDto.memberName", value = "会员名称", required = true, dataType = "string"),
			@ApiImplicitParam(name = "memberDto.tel", value = "联系电话", required = true, dataType = "string"),
			@ApiImplicitParam(name = "memberDto.password", value = "密码", required = true, dataType = "string"),
			@ApiImplicitParam(name = "memberDto.company", value = "公司名称", required = true, dataType = "string"),
			@ApiImplicitParam(name = "memberDto.province", value = "公司所在省份", required = true, dataType = "string"),
			@ApiImplicitParam(name = "memberDto.city", value = "公司所在城市", required = true, dataType = "string"),
			@ApiImplicitParam(name = "memberDto.adress", value = "地址", required = true, dataType = "string"),
			@ApiImplicitParam(name = "memberDto.industry", value = "行业", required = true, dataType = "string"),
			@ApiImplicitParam(name = "memberDto.contact", value = "联系人", required = true, dataType = "string"),
			@ApiImplicitParam(name = "memberDto.mail", value = "邮箱", required = true, dataType = "string") })
	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public @ResponseBody MemberResponse register(@RequestBody MemberRequest request, HttpServletRequest servletRequest)
			throws Exception {
		MemberResponse MemberResponse = null;
		try {//MD5加密
			request.getMemberDto().setPassword(MD5Util.encodeMD5String(request.getMemberDto().getPassword()));
			MemberResponse = memberFacade.register(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MemberResponse;

	}

	/**
	 * 注销
	 * @param requests
	 * @return true or false
	 */
	@ApiOperation(value = "注销", httpMethod = "POST", response = MemberResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "memberDto.memberName", value = "memberName", required = false, dataType = "long"), })
	@RequestMapping(value = "/logout.do", method = RequestMethod.POST)
	public @ResponseBody boolean logout(HttpServletRequest request, @RequestBody MemberRequest requests) {
		boolean flag = false;
		HttpSession session = request.getSession();
		MemberDto phonto = (MemberDto) session.getAttribute("MemberDto");
		if (phonto != null) {
			MemberRequest rquest = new MemberRequest();
			MemberDto memberDto = new MemberDto();
			//从缓存中取得该对象
			memberDto.setId(phonto.getId());
			memberDto.setStatus(AppConstants.QUERY_LOGIN_USER_NOT_STATUS_MESSAGE);
			rquest.setMemberDto(memberDto);
			try {
				//根据id修改登录状态
				memberFacade.updateMember(rquest);
				//清空缓存
				session.removeAttribute("MemberDto");
				session.removeAttribute("ID");
			} catch (Exception e) {
				e.printStackTrace();
			}
			flag = true;
		}
		return flag;

	}

	/**
	 * 取当前会员单价
	 * @param ID
	 * @return PriceDto
	 */
	@ApiOperation(value = "查询当前用户price", httpMethod = "GET", response = PriceRespnose.class)
	@RequestMapping(value = "/getPrice.do", method = RequestMethod.GET)
	public @ResponseBody PriceRespnose findCurrMemberPriceById(HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		Long id = (Long) session.getAttribute("ID");
		//判断用户是否登录
		if (id == null)
			throw new BusinessException(AppConstants.MEMBER_NOT_LOGIN_ERROR_CODE,
					AppConstants.MEMBER_NOT_LOGIN_ERROR_MESSAGE);
		MemberRequest request = new MemberRequest();
		MemberDto memberDto = new MemberDto();
		memberDto.setId(id);
		request.setMemberDto(memberDto);
		return memberFacade.SelPrice(request);
	}

}
