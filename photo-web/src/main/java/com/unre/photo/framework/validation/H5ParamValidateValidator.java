package com.unre.photo.framework.validation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;

import com.unre.photo.biz.request.BaseRequest;
import com.unre.photo.biz.response.Error;
import com.unre.photo.biz.response.ValidationH5Response;
import com.unre.photo.framework.servlet.ResettableStreamHttpServletRequest;
import com.unre.photo.util.JsonUtil;
import com.unre.photo.util.RequestUtil;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;

public class H5ParamValidateValidator implements IValidator {

	private static final Log LOGGER = LogFactory.getLog(H5ParamValidateValidator.class);

	@SuppressWarnings("unchecked")
	@Override
	public ValidationH5Response validate(ResettableStreamHttpServletRequest request, Object handler) {
		ValidationH5Response vH5Response = new ValidationH5Response();
		try {
			
			if("GET".equals(request.getMethod())){//目前不支持对GET的校验
				return vH5Response;
			}
			
			request.setCharacterEncoding("UTF-8");
			if (handler instanceof HandlerMethod) {
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				Method method = handlerMethod.getMethod();
				//获取方法需要的参数数组
				Parameter[] params = method.getParameters();
				//方法的第一个参数，既XXXH5Requset参数。（如果方法有XXXH5Requset参数的话一定要放第一个）
				Parameter param = null;
				//前端传过来的请求json字符串转换后的对象,其实就是 XXXH5Request对象
				Object h5RequestObj = null;
				if (params != null && params.length > 0
						&& (BaseRequest.class).equals(params[0].getType().getSuperclass())) {
					param = params[0];
					//获取请求参数
					String json = RequestUtil.getRequestJsonDto((ResettableStreamHttpServletRequest) request).trim();
					if (json.startsWith("{") && json.endsWith("}")) {
						LOGGER.info("请求体==" + json);
						h5RequestObj = JsonUtil.toObject(json, param.getType());
						LOGGER.info("请求方法＝＝" + method.getName());
					} else {
						LOGGER.error("validateError-json格式错误");
						vH5Response.setError(new Error("validateError", "json格式错误"));
						return vH5Response;
					}
				} else {
					return vH5Response;
				}

				ApiImplicitParams apiImplicitParams = method.getAnnotation(ApiImplicitParams.class);
				if (apiImplicitParams != null) {
					if (h5RequestObj == null) {
						vH5Response.setError(new Error("validateError", "参数不能为空"));
						return vH5Response;
					}
					ApiImplicitParam[] apiParams = apiImplicitParams.value();
					if (apiParams != null) {
						for (int i = 0; i < apiParams.length; i++) {
							ApiImplicitParam apiParam = apiParams[i];
							if (apiParam.required()) {
								String apiParamName = apiParam.name();
								String[] apiParamNameArray = apiParamName.split("\\.");
								if (apiParamName.indexOf("[") == -1) {//Object
									String h5Return = "";
									try {
										h5Return = BeanUtils.getProperty(h5RequestObj, apiParam.name());
									} catch (Exception e) {
										LOGGER.error(e);
									}
									if (!StringUtils.isNotEmpty(h5Return)) {
										vH5Response.setError(new Error(apiParam.name(), apiParam.value() + "不能为空"));
										return vH5Response;
									}
								} else if (apiParamNameArray[0].indexOf("[") != -1) {//h5Request里面的List
									List<Object> list = new ArrayList<Object>();
									Field[] fields = h5RequestObj.getClass().getDeclaredFields();
									for (Field field : fields) {
										if (Collection.class.isAssignableFrom(field.getType())) {
											field.setAccessible(true);
											list = (List<Object>) field.get(h5RequestObj);
											break;
										}
									}
									if (list == null || list.isEmpty()) {
										vH5Response.setError(new Error(apiParam.name(), apiParam.value() + "不能为空"));
										return vH5Response;
									}
									for (int j = 0; j < list.size(); j++) {
										if (list.get(j) == null) {
											continue;
										}

										String h5Return = "";
										try {
											h5Return = BeanUtils.getProperty(h5RequestObj,
													apiParam.name().replace("[n]", "[" + j + "]"));
										} catch (Exception e) {
											LOGGER.error(e);
										}

										if (!StringUtils.isNotEmpty(h5Return)) {
											vH5Response.setError(new Error(apiParam.name(), apiParam.value() + "不能为空"));
											return vH5Response;
										}
									}
								} else if (apiParamNameArray[1] != null && apiParamNameArray[1].indexOf("[") != -1) {//dto里面的list
									String dtoName = apiParamNameArray[0];
									Field dtoField = h5RequestObj.getClass().getDeclaredField(dtoName);
									dtoField.setAccessible(true);
									Object dtoObj = dtoField.get(h5RequestObj);
									List<Object> list = new ArrayList<Object>();
									Field[] fields = dtoObj.getClass().getDeclaredFields();
									for (Field field : fields) {
										if (Collection.class.isAssignableFrom(field.getType())) {
											field.setAccessible(true);
											list = (List<Object>) field.get(dtoObj);
											break;
										}
									}
									if (list == null || list.isEmpty()) {
										vH5Response.setError(new Error(apiParam.name(), apiParam.value() + "不能为空"));
										return vH5Response;
									}
									for (int j = 0; j < list.size(); j++) {
										if (list.get(j) == null) {
											continue;
										}
										int index = apiParam.name().indexOf(".") + 1;
										String dtoProperty = apiParam.name().substring(index);

										String dtoReturn = "";
										try {
											dtoReturn = BeanUtils.getProperty(dtoObj,
													dtoProperty.replace("[n]", "[" + j + "]"));
										} catch (Exception e) {
											LOGGER.error(e);
										}

										if (!StringUtils.isNotEmpty(dtoReturn)) {
											vH5Response.setError(new Error(apiParam.name(), apiParam.value() + "不能为空"));
											return vH5Response;
										}
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			vH5Response.setError(new Error("validateError", "未知错误"));
			LOGGER.error("validateError", e);
		}
		return vH5Response;
	}
}
