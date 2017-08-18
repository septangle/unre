/**
 * 
 */
package com.unre.photo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.unre.photo.biz.logic.facade.IPhotoMemberFacade;
import com.unre.photo.biz.request.PhotoMemberRequest;
import com.unre.photo.biz.response.Error;
import com.unre.photo.biz.response.PhotoMemberResponse;
import com.unre.photo.comm.AppConstants;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiImplicitParams;

/**
 * @author TDH
 *
 */
@Controller
@RequestMapping("/member")
public class PhotoMemberController extends
		BaseController<PhotoMemberController> {

	@Autowired
	private IPhotoMemberFacade photoMemberFacade;

	@ResponseBody
	@RequestMapping(value = "/queryPhotoMember.do", method = RequestMethod.POST)
	public PhotoMemberResponse queryPhotoMember(
			@RequestBody PhotoMemberRequest request,
			HttpServletRequest servletRequest) throws Exception {
		return photoMemberFacade.queryPhotoMember(request);
	}

	@ResponseBody
	@RequestMapping(value = "/findPhotoMemberById.do", method = RequestMethod.POST)
	public PhotoMemberResponse findPhotoMemberById(
			@RequestBody PhotoMemberRequest request,
			HttpServletRequest servletRequest) throws Exception {
		return photoMemberFacade.findPhotoMemberById(request);
	}

	@ResponseBody
	@RequestMapping(value = "/deletephotoMember.do", method = RequestMethod.POST)
	public PhotoMemberResponse deletePhotoMember(Long id) throws Exception {
		// TODO
		return null;
	}
    
	//登录
	@ApiOperation(value = "登陆", httpMethod = "POST", response = PhotoMemberResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "photoMemberDto.id", value = "会员ID", required = false, dataType = "long"),
            @ApiImplicitParam(name = "photoMemberDto.password", value = "密码", required = true, dataType = "string"),
            @ApiImplicitParam(name = "photoMemberDto.tel", value = "联系电话", required = true, dataType = "string"),
           })
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public @ResponseBody PhotoMemberResponse queryLoginUsers(
			@RequestBody PhotoMemberRequest request,
			HttpServletRequest servletRequest) throws Exception {
		PhotoMemberResponse PhotoMemberResponse = null;
		try {
			PhotoMemberResponse = photoMemberFacade.login(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return PhotoMemberResponse;
	}

	// 注册
	@ApiOperation(value="注册",httpMethod="POST",response = PhotoMemberResponse.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "photoMemberDto.memberName", value = "会员名称", required = true, dataType = "string"),
		@ApiImplicitParam(name = "photoMemberDto.tel", value = "联系电话", required = true, dataType = "string"),
		@ApiImplicitParam(name = "photoMemberDto.password", value = "密码", required = true, dataType = "string"),
		@ApiImplicitParam(name = "photoMemberDto.company", value = "公司名称", required = true, dataType = "string"),
		@ApiImplicitParam(name = "photoMemberDto.province", value = "公司所在省份", required = true, dataType = "string"),
		@ApiImplicitParam(name = "photoMemberDto.city", value = "公司所在城市", required = true, dataType = "string"),
		@ApiImplicitParam(name = "photoMemberDto.adress", value = "地址", required = false, dataType = "string"),
		@ApiImplicitParam(name = "photoMemberDto.industry", value = "行业", required = true, dataType = "string"),
     	@ApiImplicitParam(name = "photoMemberDto.contact", value = "联系人", required = true, dataType = "string"),
		@ApiImplicitParam(name = "photoMemberDto.mail", value = "邮箱", required = true, dataType = "string"),
	})
    @RequestMapping(value = "/register.do", method = RequestMethod.POST)
	public @ResponseBody PhotoMemberResponse register(
			@RequestBody PhotoMemberRequest request,
			HttpServletRequest servletRequest) throws Exception {
		PhotoMemberResponse PhotoMemberResponse = null;
		try {
			PhotoMemberResponse = photoMemberFacade.register(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return PhotoMemberResponse;

	}

}
