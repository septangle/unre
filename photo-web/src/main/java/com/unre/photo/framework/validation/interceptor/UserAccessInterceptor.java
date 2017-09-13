package com.unre.photo.framework.validation.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.unre.photo.biz.response.ValidationH5Response;
import com.unre.photo.framework.servlet.ResettableStreamHttpServletRequest;
import com.unre.photo.framework.validation.IValidator;
import com.unre.photo.util.JsonUtil;

public class UserAccessInterceptor extends HandlerInterceptorAdapter {

	List<IValidator> validators;

	ValidationH5Response vH5Response = new ValidationH5Response();

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		ValidationH5Response val5Response = null;
		boolean flg = true;
        
		//暂时忽略 DefaultMultipartHttpServletRequest的参数校验
		if (request instanceof DefaultMultipartHttpServletRequest)
			return true;

		for (IValidator validator : validators) {
			val5Response = validator.validate((ResettableStreamHttpServletRequest) request, handler);
			if (val5Response != null && val5Response.getError() != null
					&& StringUtils.isNotBlank(val5Response.getError().getCode())) {
				String returnStr = JsonUtil.toJson(val5Response);
				response.getWriter().write(returnStr);
				flg = false;
				break;
			}
		}
		return flg;
	}

	public List<IValidator> getValidators() {
		return validators;
	}

	public void setValidators(List<IValidator> validators) {
		this.validators = validators;
	}
}
