package com.unre.photo.biz.logic.core.impl;

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unre.photo.biz.logic.core.IOrderEngineBiz;
import com.unre.photo.biz.dto.OrderDto;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.comm.dal.dao.OrderMapper;
import com.unre.photo.comm.dal.dao.GoodsMapper;
import com.unre.photo.comm.dal.dao.BalanceMapper;
import com.unre.photo.comm.dal.dao.BalanceTraceMapper;
import com.unre.photo.comm.dal.dao.MemberMapper;
import com.unre.photo.comm.dal.dao.MemberLevelItemMapper;
import com.unre.photo.comm.dal.model.Order;
import com.unre.photo.comm.dal.model.Balance;
import com.unre.photo.comm.dal.model.BalanceTrace;
import com.unre.photo.comm.dal.model.Member;
import com.unre.photo.comm.dal.model.MemberLevelItem;
import com.unre.photo.util.HttpClientResponse;
import com.unre.photo.util.HttpClientUtil;
import com.unre.photo.util.ModelUtil;

import net.sf.json.JSONObject;

@Service("OrderProcess")
public class OrderEngine implements IOrderEngineBiz {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private BalanceMapper balanceMapper;
	@Autowired
	private BalanceTraceMapper balanceTraceMapper;
	@Autowired
	private MemberMapper memberMapper;
	@Autowired
	private MemberLevelItemMapper memberLevelItemMapper;
	@Autowired
	private GoodsMapper goodsMapper;
	
	// when the process is completed or failed;
	// update order;insert balance trace; update balance
	@Override
	public void updateOrderAndBalance() {
		
		List<Order> processList = orderMapper.selectUnclosedOrder();
		
		for (int i = 0; i < processList.size(); i++) {
			
			String strStatus = GetProcessStatusByScanID(processList.get(i).getBenacoScanId());
			
			if (!strStatus.isEmpty()) {
				
				if (strStatus.equals(AppConstants.BENACO_STATUS_COMPLETED)
					|| strStatus.equals(AppConstants.BENACO_STATUS_FAILED)) {
					
					Balance balance = balanceMapper.selectByMemberID(processList.get(i).getMemberId());
					// update order status and actual amount
					UpdateOrder(processList.get(i), strStatus);
					// insert new balance trace if the process is completed
					InsertBalanceTrace(processList.get(i), balance, strStatus);
					// update member's balance if the process is completed
					UpdateBalance(processList.get(i), balance, strStatus);
				}
			}
		}
	}
	
	// when process is beginning, update order;update balance;
	@Override
	public void updateOrderAndBalance(OrderDto orderDto) {
		Order order = ModelUtil.dtoToModel(orderDto, Order.class);
		
		UpdateOrder(order);
		UpdateBalance(order);
	}
	
	// offline trade
	// when trade is open,insert order,update balance and freeze
	// when trade is closed, update order,update balance,insert trace
	@Override
	public void updateInsertOrderByOfflineTrade(OrderDto orderDto) {
		Order order = ModelUtil.dtoToModel(orderDto, Order.class);
		
		if(order.getStatus().equalsIgnoreCase(AppConstants.ORDER_STATUS_PROCESSING)){
			//
			//
			//
			orderMapper.insert(order);
			UpdateBalance(order);
		} else if(order.getStatus().equalsIgnoreCase(AppConstants.ORDER_STATUS_COMPLETED)
				|| order.getStatus().equalsIgnoreCase(AppConstants.ORDER_STATUS_FAILED)){
			//
			//
			//
			UpdateOrderByOfflineTrade(order);
			UpdateBalanceByOfflineTrade(order);
			InsertBalanceTraceByOfflineTrade(order);
		}
	}
	
	// Update order when offline process is completed or failed
	private void UpdateBalanceByOfflineTrade(Order order){
		//
	}
	
	// Update order when offline process is completed or failed
	private void UpdateOrderByOfflineTrade(Order order){
	//	Member member = memberMapper.selectByPrimaryKey(order.getMemberId());
	//	MemberLevelItem memberLevelItem = memberLevelItemMapper.selectByValue(member.getLevel());
		BigDecimal actualPrice = 
				goodsMapper.selectByPrimaryKey(order.getGoodsId()).getPrice();
				//.multiply(memberLevelItem.getRebate());
		
		order.setGoodsActualPrice(actualPrice);
		order.setTotalAmount(actualPrice.multiply(new BigDecimal(order.getGoodsNum())));
		order.setActualAmount(order.getTotalAmount());
		
		orderMapper.updateByPrimaryKeySelective(order);
	}
	
	// 
	private void InsertBalanceTraceByOfflineTrade(Order order){
		
	}
	
	// Update balance when process started
	private void UpdateBalance(Order order){
		Balance balance = balanceMapper.selectByMemberID(order.getMemberId());
		// freeze amount
		BigDecimal amount = balance.getAmount().subtract(order.getTotalAmount());
		BigDecimal freezeAmount = balance.getFreezeAmount().add(order.getTotalAmount());
		
		balance.setAmount(amount);
		balance.setFreezeAmount(freezeAmount);
		
		balanceMapper.updateByPrimaryKeySelective(balance);
	}
	
	// update member's balance if the process is completed or failed
	private void UpdateBalance(Order order, Balance balance, String status){
		BigDecimal amount = balance.getAmount();
		BigDecimal freezeAmount = balance.getFreezeAmount();
		
		if (status.equalsIgnoreCase(AppConstants.BENACO_STATUS_COMPLETED)
				|| status.equalsIgnoreCase(AppConstants.BENACO_STATUS_FAILED)) {
			
			// if status is completed or failed
			// amount = amount + freeze - actual
			amount = amount.add(order.getTotalAmount()).subtract(order.getActualAmount());
			// freeze = freeze amount - odder's freeze
			freezeAmount = freezeAmount.subtract(order.getTotalAmount());
		} else {
			// when start processing
			// amount = amount - order's freeze amount
			amount = amount.subtract(order.getTotalAmount());
			// freeze amount plus
			freezeAmount = freezeAmount.add(order.getTotalAmount());
		}
		
		balance.setAmount(amount);
		balance.setFreezeAmount(freezeAmount);
		
		balanceMapper.updateByPrimaryKeySelective(balance);
	}
	
	// insert new balance trace if the process is completed
	private void InsertBalanceTrace(Order order, Balance balance, String status){
		if(status.equalsIgnoreCase(AppConstants.BENACO_STATUS_COMPLETED)){
			BalanceTrace balanceTrace = new BalanceTrace();
			
			balanceTrace.setMemberId(order.getMemberId());
			balanceTrace.setTransNo(order.getId());
			switch(order.getType()){
			case AppConstants.ORDER_TYPE_PHOTO:
			case AppConstants.ORDER_TYPE_PANORAMA:
				balanceTrace.setTransType(AppConstants.BALANCE_TRACE_TYPE_ONLINE);
				break;
			case AppConstants.ORDER_TYPE_OFFLINE:
				balanceTrace.setTransType(AppConstants.BALANCE_TRACE_TYPE_OFFLINE);
				break;
			default:
				balanceTrace.setTransType(AppConstants.BALANCE_TRACE_TYPE_OFFLINE);
			}
			balanceTrace.setAmount(order.getActualAmount());
			// balance trace's balance = balance's amount + balance's freeze amount
			balanceTrace.setBalance(balance.getAmount().add(balance.getFreezeAmount()));
			balanceTrace.setRemark(order.getDescription());
			
			balanceTraceMapper.insert(balanceTrace);
		}
	}
	
	// Update order when online process started  应付订单
	private void UpdateOrder(Order order){
		Member member = memberMapper.selectByPrimaryKey(order.getMemberId());
		MemberLevelItem memberLevelItem = memberLevelItemMapper.selectByValue(member.getLevel());
		BigDecimal actualPrice = 
				goodsMapper.selectByPrimaryKey(order.getGoodsId()).getPrice().multiply(memberLevelItem.getRebate());
		
		order.setGoodsActualPrice(actualPrice);
		order.setTotalAmount(actualPrice.multiply(new BigDecimal(order.getGoodsNum())));
		order.setActualAmount(order.getTotalAmount());
		
		// set status
		switch(order.getType()){
		case AppConstants.ORDER_TYPE_PHOTO:
			order.setStatus(AppConstants.ORDER_STATUS_PROCESSING);
			break;
		case AppConstants.ORDER_TYPE_PANORAMA:
			order.setStatus(AppConstants.ORDER_STATUS_PROCESSING);
			break;
		default:
			order.setStatus(AppConstants.ORDER_STATUS_PROCESSING);
		}
		
		orderMapper.updateByPrimaryKeySelective(order);
	}
	
	// Update order when process completed or failed
	public boolean UpdateOrder(Order order, String status){
		
		Integer iProcessPoints = 0;
		// set status
		order.setStatus(status.equals(AppConstants.BENACO_STATUS_COMPLETED) ?
				AppConstants.SFILE_PROCESS_COMPLETE : AppConstants.SFILE_PROCESS_FAIL);
		// set number of processed points
		iProcessPoints = GetProcessPointsByScanID(order.getBenacoScanId());
		order.setGoodsNum(iProcessPoints);
		// set actual amount after the process completed or failed
		order.setActualAmount(order.getGoodsActualPrice().multiply(new BigDecimal(iProcessPoints)));
		
		orderMapper.updateByPrimaryKeySelective(order);
		return false;
	}
	
	// Get process status by scanID
	public String GetProcessStatusByScanID(String scanID) {
		Map<String, Object> mpParams = new HashMap<String, Object>();
		mpParams.put("key", AppConstants.BENACO_PRIVATE_KEY);
		JSONObject jsParams = JSONObject.fromObject(mpParams);

		String strPhotoUrl = AppConstants.BENACO_HOST 
				+ AppConstants.BENACO_SCAN
				+ "/" + scanID + "/" 
				+ AppConstants.BENACO_STATUS;
		
		HttpClientResponse hcResponse = HttpClientUtil.doPost(strPhotoUrl, jsParams);
		
		String strStatus = "";
		if(AppConstants.HTTP_RETURN_CODE_200.equals(hcResponse.getCode())){
			strStatus = JSONObject.fromObject(hcResponse.getContext()).getString(AppConstants.BENACO_STATUS);
		}

		return strStatus;
	}
	
	// Get number of points processed
	private Integer GetProcessPointsByScanID(String scanID) {
		Map<String, Object> mpParams = new HashMap<String, Object>();
		mpParams.put("key", AppConstants.BENACO_PRIVATE_KEY);
		JSONObject jsParams = JSONObject.fromObject(mpParams);

		String strPhotoUrl = AppConstants.BENACO_HOST 
				+ AppConstants.BENACO_SCAN
				+ "/" + scanID + "/" 
				+ AppConstants.BENACO_PROCESSPOINTS;
		
		HttpClientResponse hcResponse = HttpClientUtil.doPost(strPhotoUrl, jsParams);
		
		Integer iProcessPoints = 0;
		if(AppConstants.HTTP_RETURN_CODE_200.equals(hcResponse.getCode())){
			JSONObject result = JSONObject.fromObject(hcResponse.getContext());
			iProcessPoints = result.getInt(AppConstants.BENACO_PROCESSPOINTS);
		}
		
		return iProcessPoints;
	}
}