package com.unre.photo.biz.logic.core.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unre.photo.biz.logic.core.IBalanceTraceBiz;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.comm.dal.dao.BalanceMapper;
import com.unre.photo.comm.dal.dao.BalanceTraceMapper;
import com.unre.photo.comm.dal.dao.MemberMapper;
import com.unre.photo.comm.dal.dao.MemberLevelItemMapper;
import com.unre.photo.comm.dal.model.BalanceTrace;
import com.unre.photo.comm.dal.model.Balance;
import com.unre.photo.comm.dal.model.MemberLevelItem;
import com.unre.photo.comm.dal.model.Member;
import com.unre.photo.util.ModelUtil;
import com.unre.photo.biz.dto.BalanceTraceDto;
import com.unre.photo.biz.exception.BusinessException;

@Service
public class BalanceTraceImpl implements IBalanceTraceBiz {

	@Autowired
	private BalanceMapper balanceMapper;
	@Autowired
	private BalanceTraceMapper balanceTraceMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private MemberLevelItemMapper memberLevelItemMapper;
	
	//
	// 充值/退钱时插入流水表
	//
	@Override
	@Transactional(rollbackFor = BusinessException.class)
	public void insertUpdateBalanceTrace(BalanceTraceDto balanceTraceDto) {

		BalanceTrace balanceTrace = ModelUtil.dtoToModel(balanceTraceDto, BalanceTrace.class);
		String strBalanceTraceType = balanceTrace.getTransType();
		
		switch(strBalanceTraceType){
		case AppConstants.BALANCE_TRACE_TYPE_RECHARGE:
		case AppConstants.BALANCE_TRACE_TYPE_REFUNDS:
			updateMemberLevel(balanceTrace);
			updateBalance(balanceTrace);
			insertBalanceTrace(balanceTrace);
			break;
		default:
			break;
		}
	}

	// 更新余额表
	private void updateBalance(BalanceTrace balanceTrace){
		Balance balance = balanceMapper.selectByMemberID(balanceTrace.getMemberId());
		
		if(balance != null){
			BigDecimal amount = balance.getAmount();

			amount = amount.add(balanceTrace.getAmount());
			balance.setAmount(amount);
			balanceMapper.updateByPrimaryKey(balance);
		}
		else{
			balance = new Balance();
			balance.setMemberId(balanceTrace.getMemberId());
			balance.setAmount(balanceTrace.getAmount());
			balance.setFreezeAmount(BigDecimal.ZERO);
			balanceMapper.insertSelective(balance);
		}
	}
	
	// 更新会员等级：更新会员等级，插入会员等级历史表
	private void updateMemberLevel(BalanceTrace balanceTrace){
		
		Long memnberID = balanceTrace.getMemberId();
		
		Member member = memberMapper.selectByPrimaryKey(memnberID);
		
		if(member.getSetType().equals(AppConstants.MEMBER_LEVEL_SET_TYPE_AUTOMATIC)){
			BigDecimal amountSum = balanceTraceMapper.selectAmountSumByMemberID(memnberID);
			
			if(amountSum != null){
				amountSum = amountSum.add(balanceTrace.getAmount());
			}else {
				amountSum = balanceTrace.getAmount();
			}
			MemberLevelItem memberLevelItem = memberLevelItemMapper.selectByAmount(amountSum);
			
			String memberLevelValue = memberLevelItem.getValue();
			
			if(!memberLevelValue.equalsIgnoreCase(member.getLevel())){
				
				member.setLevel(memberLevelValue);
				memberMapper.updateByPrimaryKeySelective(member);
				
				// insert and update member level table
			}
		}
	}
	
	// 插入流水表
	private void insertBalanceTrace(BalanceTrace balanceTrace){
		
		Long memberID = balanceTrace.getMemberId();
		BalanceTrace balanceTraceLatest = balanceTraceMapper.selectLastestRecordByMemberID(memberID);
		
		if (balanceTraceLatest != null){
			// 余额=最新记录的余额+录入金额（正金额：充值；负金额：退钱）
			BigDecimal balance = balanceTraceLatest.getBalance().add(balanceTrace.getAmount());
			balanceTrace.setBalance(balance);
		} else {
			balanceTrace.setBalance(balanceTrace.getAmount());
		}
		// 交易时间：暂定系统时间；需要画面上设定
		balanceTrace.setTransTime(new Date());

		balanceTraceMapper.insertSelective(balanceTrace);
	}
}