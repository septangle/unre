package com.unre.photo.biz.logic.core;

import com.unre.photo.biz.dto.BalanceDto;
import com.unre.photo.biz.exception.BusinessException;

/**
 * balance trace
 * @author Zhang Xiaofeng
 */
public interface IBalanceBiz {
	/**
	 * 
	 * 
	 * @param
	 * 
	 * @return 
	 * @throws BusinessException
	 */
	public BalanceDto selectBalance(BalanceDto balanceDto) throws BusinessException;
}