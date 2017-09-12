package com.unre.photo.biz.logic.facade;

import com.unre.photo.biz.request.BalanceTraceRequest;
import com.unre.photo.biz.response.BalanceTraceResponse;

public interface IBalanceTraceFacade {

	/**
	 * 
	 * 
	 * @param request
	 * 
	 * @return 
	 * @throws BusinessException
	 */
	public BalanceTraceResponse insertBalanceTrace(BalanceTraceRequest request) throws Exception;
}
