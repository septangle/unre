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
import com.unre.photo.biz.dto.MemberDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.facade.IMemberFacade;
import com.unre.photo.biz.request.MemberRequest;
import com.unre.photo.biz.response.MemberResponse;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.util.MD5Util;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/member")
public class MemberController extends BaseController<MemberController> {

	@Autowired
	private IMemberFacade memberFacade;

/*	@ApiOperation(value = "查询会员", httpMethod = "POST", response = MemberResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "MemberDto.memberName", value = "会员名称", required = false, dataType = "string"),
			@ApiImplicitParam(name = "MemberDto.tel", value = "联系电话", required = false, dataType = "string"),
			@ApiImplicitParam(name = "MemberDto.password", value = "密码", required = false, dataType = "string"),
			@ApiImplicitParam(name = "MemberDto.company", value = "公司名称", required = false, dataType = "string"),
			@ApiImplicitParam(name = "MemberDto.province", value = "公司所在省份", required = false, dataType = "string"),
			@ApiImplicitParam(name = "MemberDto.city", value = "公司所在城市", required = false, dataType = "string"),
			@ApiImplicitParam(name = "MemberDto.adress", value = "地址", required = false, dataType = "string"),
			@ApiImplicitParam(name = "MemberDto.industry", value = "行业", required = false, dataType = "string"),
			@ApiImplicitParam(name = "MemberDto.contact", value = "联系人", required = false, dataType = "string"),
			@ApiImplicitParam(name = "MemberDto.mail", value = "邮箱", required = false, dataType = "string") })
	@RequestMapping(value = "/queryMember.do", method = RequestMethod.POST)
	public @ResponseBody MemberResponse queryMember(@RequestBody MemberRequest request,
			HttpServletRequest servletRequest) throws Exception {
		return memberFacade.queryMember(request);
	}*/
    
	@ApiOperation(value = "查询当前会员", httpMethod = "GET", response = MemberResponse.class)
	@RequestMapping(value = "/getCurrMember", method = RequestMethod.GET)
	public @ResponseBody MemberResponse findCurrMemberById(HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		Long id = (Long) session.getAttribute("ID");
		if(id == null) 
			throw new BusinessException(AppConstants.MEMBER_NOT_LOGIN_ERROR_CODE,AppConstants.MEMBER_NOT_LOGIN_ERROR_MESSAGE);
		MemberRequest request = new MemberRequest();
		MemberDto memberDto =  new MemberDto();
		memberDto.setId(id);
		request.setMemberDto(memberDto);
		return memberFacade.findMemberById(request);
	}

	/*	@ApiOperation(value = "通过会员ID删除会员", httpMethod = "POST", response = MemberResponse.class)
		@ApiImplicitParams({
				@ApiImplicitParam(name = "MemberDto.id", value = "会员系统主键", required = true, dataType = "long") })
		@RequestMapping(value = "/deleteMember.do", method = RequestMethod.POST)
		public @ResponseBody MemberResponse deleteMember(Long id) throws Exception {
			// TODO
			return null;
		}*/

	@ApiOperation(value = "登陆", httpMethod = "POST", response = MemberResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "memberDto.tel", value = "联系电话", required = true, dataType = "string"),
			@ApiImplicitParam(name = "memberDto.password", value = "密码", required = true, dataType = "string") })
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public @ResponseBody MemberResponse queryLoginUsers(@RequestBody MemberRequest request,
			HttpServletRequest servletRequest) throws BusinessException {
		MemberResponse MemberResponse = null;
		HttpSession session = servletRequest.getSession();
		MemberDto phonto = (MemberDto) session.getAttribute("MemberDto");
		if (phonto != null) {
			throw new BusinessException(AppConstants.QUERY_LOGIN_USERLOING_ERROR_CODE,
					AppConstants.QUERY_LOGIN_USERLOING_ERROR_MESSAGE);
		}
		try {
			request.getMemberDto().setPassword(MD5Util.encodeMD5String(request.getMemberDto().getPassword()));
			MemberResponse = memberFacade.login(request);
			if (MemberResponse != null && MemberResponse.getMemberDto() != null) {
				servletRequest.getSession().setAttribute("MemberDto", MemberResponse.getMemberDto());
				servletRequest.getSession().setAttribute("ID", MemberResponse.getMemberDto().getId());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MemberResponse;
	}

	// 注册
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
	public @ResponseBody MemberResponse register(@RequestBody MemberRequest request,
			HttpServletRequest servletRequest) throws Exception {
		MemberResponse MemberResponse = null;
		try {
			request.getMemberDto().setPassword(MD5Util.encodeMD5String(request.getMemberDto().getPassword()));
			MemberResponse = memberFacade.register(request);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return MemberResponse;

	}

	//登出
	@ApiOperation(value = "登出", httpMethod = "POST", response = MemberResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "memberDto.memberName", value = "memberName", required = false, dataType = "long"), })
	@RequestMapping(value = "/logout.do", method = RequestMethod.POST)
	public @ResponseBody boolean logout(HttpServletRequest request, @RequestBody MemberRequest requests) {
		boolean flag = false;
		HttpSession session = request.getSession();
		MemberDto phonto = (MemberDto) session.getAttribute("MemberDto");
		if (phonto != null) {
			session.removeAttribute("MemberDto");
			flag = true;
		}
		return flag;

	}

}
