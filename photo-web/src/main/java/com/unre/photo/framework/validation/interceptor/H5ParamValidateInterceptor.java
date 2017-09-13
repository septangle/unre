package com.unre.photo.framework.validation.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.unre.photo.biz.response.ValidationH5Response;
import com.unre.photo.framework.servlet.ResettableStreamHttpServletRequest;
import com.unre.photo.framework.validation.IValidator;
import com.unre.photo.util.JsonUtil;

/**
 * 根据Swagger的@ApiImplicitParam注解中的required的值来判断请求中的参数是否必填
 * 另外，使用Swagger注解@ApiImplicitParam的时候， @ApiImplicitParam的name的值必须与xxxDto中的对应属性名相同，
 * xxxDto必须写上required = true,且必须放在@ApiImplicitParams里面的第一个。
 * 
 * @author john.ji
 *
 */

public class H5ParamValidateInterceptor extends HandlerInterceptorAdapter {

	private IValidator validator;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		ValidationH5Response val5Response = null;
		boolean flg = true;

		//暂时忽略 DefaultMultipartHttpServletRequest的参数校验
		if (request instanceof DefaultMultipartHttpServletRequest)
			return true;

		response.setContentType("application/json; charset=utf-8");

		val5Response = validator.validate((ResettableStreamHttpServletRequest) request, handler);
		if (val5Response != null && val5Response.getError() != null
				&& StringUtils.isNotBlank(val5Response.getError().getCode())) {
			String returnStr = JsonUtil.toJson(val5Response);
			response.getWriter().write(returnStr);
			flg = false;
		}
		//request.getSession().setAttribute("USERID", queryCurrentUserId(request));
		return flg;
	}

	@SuppressWarnings("unused")
	private long queryCurrentUserId(HttpServletRequest request) {
		int userId = 0;

		return userId;
	}

	public IValidator getValidator() {
		return validator;
	}

	public void setValidator(IValidator validator) {
		this.validator = validator;
	}
}
