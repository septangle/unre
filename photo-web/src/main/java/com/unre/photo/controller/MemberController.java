package com.unre.photo.controller;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unre.photo.biz.dto.MemberDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.facade.IMemberFacade;
import com.unre.photo.biz.request.MemberRequest;
import com.unre.photo.biz.response.Error;
import com.unre.photo.biz.response.MemberResponse;
import com.unre.photo.biz.response.PriceRespnose;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.util.MD5Util;
import com.unre.photo.util.MailUtils;
import com.unre.photo.util.Sendsms;
import com.unre.photo.util.Token;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * 平台会员信息
 * 
 * @author zx
 *
 */
@Controller
@RequestMapping("/member")
public class MemberController extends BaseController<MemberController> {

	@Autowired
	private IMemberFacade memberFacade; //会员

	/**
	 * 登录
	 * @param request
	 * @return member
	 */
	@ApiOperation(value = "登录", httpMethod = "POST", response = MemberResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "memberDto.tel", value = "联系电话", required = true, dataType = "string"),
			@ApiImplicitParam(name = "memberDto.password", value = "密码", required = true, dataType = "string") })
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public @ResponseBody MemberResponse queryLoginUser(@RequestBody MemberRequest request,
			HttpServletRequest servletRequest) throws Exception {
		MemberResponse memberResponse = null;
		HttpSession session = servletRequest.getSession();
		MemberDto member = (MemberDto) session.getAttribute("memberDto");
		//判断会员是否登录
		if (member != null) {
			throw new BusinessException(AppConstants.QUERY_LOGIN_USERLOING_ERROR_CODE,
					AppConstants.QUERY_LOGIN_USERLOING_ERROR_MESSAGE);
		}
		//登录方式：用户名/电话号码
		String loginParam = request.getMemberDto().getTel();
		boolean flag = isInteger(loginParam);
		//整数返回true,否则返回false
		if (flag == false) {
			request.getMemberDto().setMail(loginParam);
			request.getMemberDto().setTel(null);
		}
		//加密
		request.getMemberDto().setPassword(MD5Util.encodeMD5String(request.getMemberDto().getPassword()));
		memberResponse = memberFacade.login(request);
		//判断对象是否为空，并放入session
		if (memberResponse != null && memberResponse.getMemberDto() != null) {
			servletRequest.getSession().setAttribute("memberDto", memberResponse.getMemberDto());
			servletRequest.getSession().setAttribute("memberId", memberResponse.getMemberDto().getId());
		}
		return memberResponse;
	}

	/**
	 * 注册
	 * @param request
	 * @return member
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
		MemberResponse memberResponse = null;
		request.getMemberDto().setPassword(MD5Util.encodeMD5String(request.getMemberDto().getPassword()));
		memberResponse = memberFacade.register(request);
		return memberResponse;
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
	public @ResponseBody boolean logout(HttpServletRequest request, @RequestBody MemberRequest requests)
			throws Exception {
		boolean flag = false;
		HttpSession session = request.getSession();
		MemberDto member = (MemberDto) session.getAttribute("memberDto");
		if (member != null) {
			//清空缓存
			session.removeAttribute("memberDto");
			session.removeAttribute("memberId");
			flag = true;
		}
		return flag;

	}

	/**
	 * 取当前会员单价
	 * @param ID
	 * @return priceDto
	 */
	@ApiOperation(value = "查询当前用户price", httpMethod = "GET", response = PriceRespnose.class)
	@RequestMapping(value = "/getPrice.do", method = RequestMethod.GET)
	public @ResponseBody PriceRespnose findCurrMemberPrice(HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		Long memberId = (Long) session.getAttribute("memberId");

		//判断用户是否登录
		if (memberId == null) {
			throw new BusinessException(AppConstants.MEMBER_NOT_LOGIN_ERROR_CODE,
					AppConstants.MEMBER_NOT_LOGIN_ERROR_MESSAGE);
		}

		MemberRequest request = new MemberRequest();
		MemberDto memberDto = new MemberDto();
		memberDto.setId(memberId);
		request.setMemberDto(memberDto);

		return memberFacade.findCurrMemberPrice(request);
	}

	/**
	 * 查询所有用户
	 * @param ID
	 * @return 
	 */
	@ApiOperation(value = "查询ALL用户", httpMethod = "GET", response = PriceRespnose.class)
	@RequestMapping(value = "/getAllMember.do", method = RequestMethod.GET)
	public @ResponseBody MemberResponse findAllMember(HttpServletRequest servletRequest) throws Exception {
		return memberFacade.queryAllMember(new MemberRequest());
	}

	/**
	  * 判断是否为整数 
	  * @param str 传入的字符串 
	  * @return 是整数返回true,否则返回false 
	  */
	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
		return pattern.matcher(str).matches();
	}

	@ApiOperation(value = "查询当前会员信息", httpMethod = "GET", response = MemberResponse.class)
	@RequestMapping(value = "/getMemberInfomaction.do", method = RequestMethod.GET)
	public @ResponseBody MemberResponse findMemberInfomaction(HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		//根据缓存ID查询当前登录会员
		Long memberId = (Long) session.getAttribute("memberId");
		//判断用户是否登录
		if (memberId == null) {
			throw new BusinessException(AppConstants.MEMBER_NOT_LOGIN_ERROR_CODE,
					AppConstants.MEMBER_NOT_LOGIN_ERROR_MESSAGE);
		}
		MemberRequest request = new MemberRequest();
		MemberDto memberDto = new MemberDto();
		memberDto.setId(memberId);
		request.setMemberDto(memberDto);
		//根据ID查询
		return memberFacade.findMemberInfomaction(request);
	}

	/**
	 * 忘记密码
	 * 第一步：
	 * 验证手机号/邮箱
	 * @param mailOrTel
	 */
	@RequestMapping(value = "/verifyAccount.do", method = RequestMethod.POST)
	public @ResponseBody MemberResponse verifyAccount(@RequestParam("mailOrTel") String mailOrTel,
			HttpServletRequest servletRequest) throws Exception {
		MemberResponse memberResponse = null;
		MemberRequest request = new MemberRequest();
		MemberDto memberDto = new MemberDto();
		boolean flag = isInteger(mailOrTel);

		//整数返回true,否则返回false
		if (flag == false) {
			memberDto.setMail(mailOrTel);
			request.setMemberDto(memberDto);
		} else {
			memberDto.setTel(mailOrTel);
			request.setMemberDto(memberDto);
		}
		memberResponse = memberFacade.queryMember(request);

		if (memberResponse.getMemberDtoList() != null) {
			servletRequest.getSession().setAttribute("memberId", memberResponse.getMemberDtoList().get(0).getId());
		}

		return memberResponse;
	}

	/**
	 * 第二步：
	 * 发送验证码/邮件 
	 * @param mailOrTel
	 */
	@RequestMapping(value = "/sendVerifyCode.do", method = RequestMethod.POST)
	public @ResponseBody MemberResponse sendVerifyCode(@RequestParam("mailOrTel") String mailOrTel,
			HttpServletRequest servletRequest) throws Exception {
		MemberResponse response = new MemberResponse();
		boolean flag = isInteger(mailOrTel);
		int code = 0;
		//整数返回true,否则返回false
		if (flag == false) {
			//发送邮件
			code = MailUtils.email(AppConstants.SUBJECT, mailOrTel);
			if (code == 0) {
				response.setError(new Error(AppConstants.SEND_CODE_FAIL_CODE, AppConstants.SEND_CODE_FAIL));
			}
		} else {
			//发送短信
			code = Sendsms.sendMessage(mailOrTel);
			if (code == 0) {
				response.setError(new Error(AppConstants.SEND_CODE_FAIL_CODE, AppConstants.SEND_CODE_FAIL));
			}

		}
		servletRequest.getSession().setAttribute("code", code);
		servletRequest.getSession().setMaxInactiveInterval(60*5);

		return response;
	}

	/**
	 * 第三步：
	 * 校验验证码
	 * @param mailOrTel,code
	 */
	@RequestMapping(value = "/verifyCode.do", method = RequestMethod.POST)
	public @ResponseBody MemberResponse verifyCode(@RequestParam("code") String code, HttpServletRequest servletRequest)
			throws Exception {
		MemberResponse response = new MemberResponse();
		HttpSession session = servletRequest.getSession();
		if (session.getAttribute("code") == null) {
			response.setError(new Error(AppConstants.VERIFY_CODE_FAIL_CODE, AppConstants.VERIFY_CODE_FAIL_INVALID));

		} else {
			int verifyCode = (int) session.getAttribute("code");
			if (code.equals(Integer.toString(verifyCode))) {
				//生成token存session
				String tokenName = Token.getTokenString(session);
				response.setToken(tokenName);
			} else {
				response.setError(new Error(AppConstants.VERIFY_CODE_FAIL_CODE, AppConstants.VERIFY_CODE_FAIL));
			}
		}
		return response;
	}

	/**
	 * 第四步：
	 * 重置密码
	 * @param mailOrTel,password
	 */
	@RequestMapping(value = "/resetPassword.do", method = RequestMethod.POST)
	public @ResponseBody MemberResponse resetPassword(@RequestParam("mailOrTel") String mailOrTel,
			@RequestParam("tokenName") String tokenName, @RequestParam("password") String password,
			HttpServletRequest servletRequest) throws Exception {
		MemberResponse response = new MemberResponse();
		HttpSession session = servletRequest.getSession();
		MemberDto memberDto = new MemberDto();
		MemberRequest request = new MemberRequest();
		boolean flag = isInteger(mailOrTel);
		//整数返回true,否则返回false
		//进行身份验证
		Long memberId = (Long) session.getAttribute("memberId");
		if (Token.isTokenStringValid(tokenName, servletRequest.getSession()) && memberId != null) {
			session.removeAttribute("memberId");
			if (flag == false) {
				memberDto.setMail(mailOrTel);
				request.setMemberDto(memberDto);
			} else {
				memberDto.setTel(mailOrTel);
				request.setMemberDto(memberDto);
			}
			request.getMemberDto().setPassword(MD5Util.encodeMD5String(password));
			response = memberFacade.updatePassword(request);
		} else {
			response.setError(new Error(AppConstants.UPDATE_PASSWORD_CODE, AppConstants.UPDATE_PASSWORD));
		}
		return response;
	}
}
