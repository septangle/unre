package com.unre.photo.biz.logic.core.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unre.photo.biz.logic.core.IBalanceBiz;
import com.unre.photo.comm.dal.dao.BalanceMapper;
import com.unre.photo.comm.dal.model.Balance;
import com.unre.photo.util.ModelUtil;
import com.unre.photo.biz.dto.BalanceDto;

@Service
public class BalanceImpl implements IBalanceBiz {

	@Autowired
	private BalanceMapper balanceMapper;
	
	//
	// 
	//
	@Override
	public BalanceDto selectBalance(BalanceDto balanceDto) {
		
		Balance balance = balanceMapper.selectByMemberID(balanceDto.getMemberId());
		
		return ModelUtil.modelToDto(balance, BalanceDto.class);
	}

}