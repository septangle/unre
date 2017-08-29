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
import com.unre.photo.biz.dto.PhotoMemberDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.facade.IPhotoMemberFacade;
import com.unre.photo.biz.request.PhotoMemberRequest;
import com.unre.photo.biz.response.PhotoMemberResponse;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.util.MD5Util;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/member")
public class PhotoMemberController extends BaseController<PhotoMemberController> {

	@Autowired
	private IPhotoMemberFacade photoMemberFacade;

	@ApiOperation(value = "查询会员", httpMethod = "POST", response = PhotoMemberResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "photoMemberDto.memberName", value = "会员名称", required = false, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.tel", value = "联系电话", required = false, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.password", value = "密码", required = false, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.company", value = "公司名称", required = false, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.province", value = "公司所在省份", required = false, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.city", value = "公司所在城市", required = false, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.adress", value = "地址", required = false, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.industry", value = "行业", required = false, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.contact", value = "联系人", required = false, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.mail", value = "邮箱", required = false, dataType = "string") })
	@RequestMapping(value = "/queryMember.do", method = RequestMethod.POST)
	public @ResponseBody PhotoMemberResponse queryPhotoMember(@RequestBody PhotoMemberRequest request,
			HttpServletRequest servletRequest) throws Exception {
		return photoMemberFacade.queryPhotoMember(request);
	}
    
	@ApiOperation(value = "查询当前会员", httpMethod = "GET", response = PhotoMemberResponse.class)
	@RequestMapping(value = "/getCurrMember", method = RequestMethod.GET)
	public @ResponseBody PhotoMemberResponse findCurrMemberById(HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		Long id = (Long) session.getAttribute("ID");
		if(id == null) 
			throw new BusinessException(AppConstants.MEMBER_NOT_LOGIN_ERROR_CODE,AppConstants.MEMBER_NOT_LOGIN_ERROR_MESSAGE);
		PhotoMemberRequest request = new PhotoMemberRequest();
		PhotoMemberDto memberDto =  new PhotoMemberDto();
		memberDto.setId(id);
		request.setPhotoMemberDto(memberDto);
		return photoMemberFacade.findPhotoMemberById(request);
	}

	/*	@ApiOperation(value = "通过会员ID删除会员", httpMethod = "POST", response = PhotoMemberResponse.class)
		@ApiImplicitParams({
				@ApiImplicitParam(name = "photoMemberDto.id", value = "会员系统主键", required = true, dataType = "long") })
		@RequestMapping(value = "/deletephotoMember.do", method = RequestMethod.POST)
		public @ResponseBody PhotoMemberResponse deletePhotoMember(Long id) throws Exception {
			// TODO
			return null;
		}*/

	@ApiOperation(value = "登陆", httpMethod = "POST", response = PhotoMemberResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "photoMemberDto.tel", value = "联系电话", required = true, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.password", value = "密码", required = true, dataType = "string") })
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public @ResponseBody PhotoMemberResponse queryLoginUsers(@RequestBody PhotoMemberRequest request,
			HttpServletRequest servletRequest) throws BusinessException {
		PhotoMemberResponse PhotoMemberResponse = null;
		HttpSession session = servletRequest.getSession();
		PhotoMemberDto phonto = (PhotoMemberDto) session.getAttribute("photomemberDto");
		if (phonto != null) {
			throw new BusinessException(AppConstants.QUERY_LOGIN_USERLOING_ERROR_CODE,
					AppConstants.QUERY_LOGIN_USERLOING_ERROR_MESSAGE);
		}
		try {
			request.getPhotoMemberDto().setPassword(MD5Util.encodeMD5String(request.getPhotoMemberDto().getPassword()));
			PhotoMemberResponse = photoMemberFacade.login(request);
			if (PhotoMemberResponse != null && PhotoMemberResponse.getPhotoMemberDto() != null) {
				servletRequest.getSession().setAttribute("photomemberDto", PhotoMemberResponse.getPhotoMemberDto());
				servletRequest.getSession().setAttribute("ID", PhotoMemberResponse.getPhotoMemberDto().getId());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return PhotoMemberResponse;
	}

	// 注册
	@ApiOperation(value = "注册", httpMethod = "POST", response = PhotoMemberResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "photoMemberDto.memberName", value = "会员名称", required = true, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.tel", value = "联系电话", required = true, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.password", value = "密码", required = true, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.company", value = "公司名称", required = true, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.province", value = "公司所在省份", required = true, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.city", value = "公司所在城市", required = true, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.adress", value = "地址", required = true, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.industry", value = "行业", required = true, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.contact", value = "联系人", required = true, dataType = "string"),
			@ApiImplicitParam(name = "photoMemberDto.mail", value = "邮箱", required = true, dataType = "string") })
	@RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public @ResponseBody PhotoMemberResponse register(@RequestBody PhotoMemberRequest request,
			HttpServletRequest servletRequest) throws Exception {
		PhotoMemberResponse PhotoMemberResponse = null;
		try {
			request.getPhotoMemberDto().setPassword(MD5Util.encodeMD5String(request.getPhotoMemberDto().getPassword()));
			PhotoMemberResponse = photoMemberFacade.register(request);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return PhotoMemberResponse;

	}

	//登出
	@ApiOperation(value = "登出", httpMethod = "POST", response = PhotoMemberResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "photoMemberDto.memberName", value = "memberName", required = false, dataType = "long"), })
	@RequestMapping(value = "/logout.do", method = RequestMethod.POST)
	public @ResponseBody boolean logout(HttpServletRequest request, @RequestBody PhotoMemberRequest requests) {
		boolean flag = false;
		HttpSession session = request.getSession();
		PhotoMemberDto phonto = (PhotoMemberDto) session.getAttribute("photomemberDto");
		if (phonto != null) {
			session.removeAttribute("photomemberDto");
			flag = true;
		}
		return flag;

	}

}
