package com.unre.photo.biz.logic.core;

import com.unre.photo.biz.dto.BalanceTraceDto;
import com.unre.photo.biz.exception.BusinessException;

/**
 * balance trace
 * @author Zhang Xiaofeng
 */
public interface IBalanceTraceBiz {
	/**
	 * recharge/refunds/offline
	 * 
	 * @param
	 * 
	 * @return boolean
	 * @throws BusinessException
	 */
	public void insertUpdateBalanceTrace(BalanceTraceDto balanceTraceDto) throws BusinessException;
}