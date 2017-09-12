package com.unre.photo.biz.logic.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unre.photo.biz.logic.core.IBalanceTraceBiz;
import com.unre.photo.biz.logic.facade.IBalanceTraceFacade;
import com.unre.photo.biz.request.BalanceTraceRequest;
import com.unre.photo.biz.response.BalanceTraceResponse;

/**
 * @author TDH
 *
 */
@Service
public class BalanceTraceFacadeImpl implements IBalanceTraceFacade {

	@Autowired
	private IBalanceTraceBiz balanceTraceBiz;

	@Override
	public BalanceTraceResponse insertBalanceTrace(BalanceTraceRequest request) throws Exception {
		BalanceTraceResponse response = new BalanceTraceResponse();
		
		balanceTraceBiz.insertUpdateBalanceTrace(request.getBalanceTraceDto());
		
		return response;
	}
}
