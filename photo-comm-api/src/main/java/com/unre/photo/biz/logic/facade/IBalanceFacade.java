package com.unre.photo.biz.logic.facade;

import com.unre.photo.biz.request.BalanceRequest;
import com.unre.photo.biz.response.BalanceResponse;

public interface IBalanceFacade {

	/**
	 * 
	 * 
	 * @param request
	 * 
	 * @return 
	 * @throws BusinessException
	 */
	public BalanceResponse selectBalance(BalanceRequest request) throws Exception;
}
