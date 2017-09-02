package com.unre.photo.framework.validation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unre.photo.biz.logic.facade.IMemberFacade;
import com.unre.photo.biz.response.ValidationH5Response;
import com.unre.photo.framework.servlet.ResettableStreamHttpServletRequest;

public class UserTokenAccessValidator implements IValidator {

	@SuppressWarnings("unused")
	private static final Log LOGGER = LogFactory.getLog(UserTokenAccessValidator.class);

	@Autowired
	private IMemberFacade memberFacade;

	@Override
	public ValidationH5Response validate(ResettableStreamHttpServletRequest request, Object handler) {
		ValidationH5Response vH5Response = new ValidationH5Response();
		//TODO 
		return vH5Response;
	}

	public IMemberFacade getmemberFacade() {
		return memberFacade;
	}

	public void setmemberFacade(IMemberFacade memberFacade) {
		this.memberFacade = memberFacade;
	}

}
