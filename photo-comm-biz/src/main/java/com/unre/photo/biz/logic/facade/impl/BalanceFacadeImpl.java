package com.unre.photo.biz.logic.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unre.photo.biz.logic.core.IBalanceBiz;
import com.unre.photo.biz.logic.facade.IBalanceFacade;
import com.unre.photo.biz.request.BalanceRequest;
import com.unre.photo.biz.response.BalanceResponse;
import com.unre.photo.biz.dto.BalanceDto;

/**
 * @author TDH
 *
 */
@Service
public class BalanceFacadeImpl implements IBalanceFacade {

	@Autowired
	private IBalanceBiz balanceBiz;

	@Override
	public BalanceResponse selectBalance(BalanceRequest request) throws Exception {
		BalanceResponse response = new BalanceResponse();
		
		BalanceDto balanceDto = balanceBiz.selectBalance(request.getBalanceDto());
		response.setBalanceDto(balanceDto);
		
		return response;
	}
}
