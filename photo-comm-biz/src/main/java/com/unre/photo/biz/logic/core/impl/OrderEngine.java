package com.unre.photo.biz.logic.core.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unre.photo.biz.logic.core.IOrderEngineBiz;
import com.unre.photo.biz.dto.OrderDto;
import com.unre.photo.biz.exception.BusinessException;
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
import com.unre.photo.util.JsonUtil;
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

		List<Order> orderList = orderMapper.selectUnclosedOrder();

		for (Order order : orderList) {

			String strStatus = GetProcessStatusByScanID(order.getBenacoScanId());

			if (!strStatus.isEmpty()) {

				if (strStatus.equals(AppConstants.BENACO_STATUS_COMPLETED)
						|| strStatus.equals(AppConstants.BENACO_STATUS_FAILED)) {

					Balance balance = balanceMapper.selectByMemberID(order.getMemberId());
					updateOrderAndBalance(order, balance, strStatus);
				}
			}
		}
	}

	//
	@Transactional(rollbackFor = BusinessException.class)
	private void updateOrderAndBalance(Order order, Balance balance, String status) {
		UpdateOrder(order, status);
		// insert new balance trace if the process is completed
		InsertBalanceTrace(order, balance, status);
		// update member's balance if the process is completed
		UpdateBalance(order, balance, status);
	}

	// when process is beginning, update order;update balance;
	@Override
	@Transactional(rollbackFor = BusinessException.class)
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

		if (order.getStatus().equalsIgnoreCase(AppConstants.ORDER_STATUS_PROCESSING)) {
			//
			//
			//
			orderMapper.insert(order);
			UpdateBalance(order);
		} else if (order.getStatus().equalsIgnoreCase(AppConstants.ORDER_STATUS_COMPLETED)
				|| order.getStatus().equalsIgnoreCase(AppConstants.ORDER_STATUS_FAILED)) {
			//
			//
			//
			UpdateOrderByOfflineTrade(order);
			UpdateBalanceByOfflineTrade(order);
			// InsertBalanceTraceByOfflineTrade(order);
		}
	}

	
	// Update order when offline process is completed or failed
	private void UpdateBalanceByOfflineTrade(Order order) {
		//
	}

	// Update order when offline process is completed or failed
	private void UpdateOrderByOfflineTrade(Order order) {
		//	Member member = memberMapper.selectByPrimaryKey(order.getMemberId());
		//	MemberLevelItem memberLevelItem = memberLevelItemMapper.selectByValue(member.getLevel());
		BigDecimal actualPrice = goodsMapper.selectByPrimaryKey(order.getGoodsId()).getPrice();
		//.multiply(memberLevelItem.getRebate());

		order.setGoodsActualPrice(actualPrice);
		order.setTotalAmount(actualPrice.multiply(new BigDecimal(order.getGoodsNum())));
		order.setActualAmount(order.getTotalAmount());

		orderMapper.updateByPrimaryKeySelective(order);
	}
/*
	// 
	private void InsertBalanceTraceByOfflineTrade(Order order) {

	}
*/
	// Update balance when process started
	private void UpdateBalance(Order order) {
		Balance balance = balanceMapper.selectByMemberID(order.getMemberId());
		// freeze amount
		BigDecimal amount = balance.getAmount().subtract(order.getTotalAmount());
		BigDecimal freezeAmount = balance.getFreezeAmount().add(order.getTotalAmount());

		balance.setAmount(amount);
		balance.setFreezeAmount(freezeAmount);

		balanceMapper.updateByPrimaryKeySelective(balance);
	}

	// update member's balance if the process is completed or failed
	private void UpdateBalance(Order order, Balance balance, String status) {
		if (status.equalsIgnoreCase(AppConstants.BENACO_STATUS_COMPLETED)
				|| status.equalsIgnoreCase(AppConstants.BENACO_STATUS_FAILED)) {

			BigDecimal amount = balance.getAmount();
			BigDecimal freezeAmount = balance.getFreezeAmount();
			
			// if status is completed or failed
			// amount = amount + freeze - actual
			amount = amount.add(order.getTotalAmount()).subtract(order.getActualAmount());
			// freeze = freeze amount - odder's freeze
			freezeAmount = freezeAmount.subtract(order.getTotalAmount());
			
			balance.setAmount(amount);
			balance.setFreezeAmount(freezeAmount);

			balanceMapper.updateByPrimaryKeySelective(balance);
		}
	}

	// insert new balance trace if the process is completed
	private void InsertBalanceTrace(Order order, Balance balance, String status) {
		if (status.equalsIgnoreCase(AppConstants.BENACO_STATUS_COMPLETED)) {
			BalanceTrace balanceTrace = new BalanceTrace();

			balanceTrace.setMemberId(order.getMemberId());
			balanceTrace.setTransNo(order.getId());
			balanceTrace.setTransTime(new Date());
			switch (order.getType()) {
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
			// balance trace's balance = balance's amount + balance's freeze amount - ordre's actual amount
			balanceTrace.setBalance(balance.getAmount().add(balance.getFreezeAmount().subtract(order.getActualAmount())));
			balanceTrace.setRemark(order.getDescription());

			balanceTraceMapper.insertSelective(balanceTrace);
		}
	}

	// Update order when online process started
	private void UpdateOrder(Order order) {
		Member member = memberMapper.selectByPrimaryKey(order.getMemberId());
		MemberLevelItem memberLevelItem = memberLevelItemMapper.selectByValue(member.getLevel());
		BigDecimal actualPrice = goodsMapper.selectByPrimaryKey(order.getGoodsId()).getPrice()
				.multiply(memberLevelItem.getRebate());

		order.setGoodsActualPrice(actualPrice);
		order.setTotalAmount(actualPrice.multiply(new BigDecimal(order.getGoodsNum())));
		System.out.println(order.getTotalAmount());
		order.setActualAmount(order.getTotalAmount());
		order.setStatus(AppConstants.ORDER_STATUS_INIT);

		/*
		// set status
		switch(order.getType()){
		case AppConstants.ORDER_TYPE_PHOTO:
			order.setStatus(AppConstants.ORDER_STATUS_INIT);
			break;
		case AppConstants.ORDER_TYPE_PANORAMA:
			order.setStatus(AppConstants.ORDER_STATUS_INIT);
			break;
		default:
			order.setStatus(AppConstants.ORDER_STATUS_INIT);
		}
		*/

		try {
			int i= orderMapper.updateBySelective(order);
			System.out.println(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Update order when process completed or failed
	private void UpdateOrder(Order order, String status) {

		// Integer iProcessPoints = 0;
		// set status
		order.setStatus(status.equals(AppConstants.BENACO_STATUS_COMPLETED) ? AppConstants.ORDER_STATUS_COMPLETED
				: AppConstants.ORDER_STATUS_FAILED);
		// set number of processed pointsstatus.equals(AppConstants.BENACO_STATUS_COMPLETED)
		// iProcessPoints = GetProcessPointsByScanID(order.getBenacoScanId());
		// order.setGoodsNum(iProcessPoints);
		// set actual amount after the process completed or failed
		// order.setActualAmount(order.getGoodsActualPrice().multiply(new BigDecimal(iProcessPoints)));
		if (status.equals(AppConstants.BENACO_STATUS_FAILED)){
			order.setActualAmount(BigDecimal.ZERO);
		}

		orderMapper.updateByPrimaryKey(order);
	}

	// Get process status by scanID
	private String GetProcessStatusByScanID(String scanID) {
		Map<String, Object> mpParams = new HashMap<String, Object>();
		mpParams.put("key", AppConstants.BENACO_PRIVATE_KEY);
		String strStatus = "";
		try {
			JSONObject jsParams = JSONObject.fromObject(mpParams);
			String strPhotoUrl = AppConstants.BENACO_HOST + AppConstants.BENACO_BASEPATH + AppConstants.BENACO_SCAN + "/" + scanID + "/"
					+ AppConstants.BENACO_STATUS;
			HttpClientResponse hcResponse = HttpClientUtil.doPost(strPhotoUrl, jsParams);
			if (AppConstants.HTTP_RETURN_CODE_200.equals(hcResponse.getCode())) {
				String context = hcResponse.getContext();
				Map<String, Object> map = JsonUtil.toMap(context);
				strStatus = map.get("status").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return strStatus;
	}

	// Get number of points processed
	private Integer GetProcessPointsByScanID(String scanID) {
		Map<String, Object> mpParams = new HashMap<String, Object>();
		mpParams.put("key", AppConstants.BENACO_PRIVATE_KEY);
		JSONObject jsParams = JSONObject.fromObject(mpParams);

		String strPhotoUrl = AppConstants.BENACO_HOST + AppConstants.BENACO_SCAN + "/" + scanID + "/"
				+ AppConstants.BENACO_PROCESSPOINTS;

		HttpClientResponse hcResponse = HttpClientUtil.doPost(strPhotoUrl, jsParams);

		Integer iProcessPoints = 0;
		if (AppConstants.HTTP_RETURN_CODE_200.equals(hcResponse.getCode())) {
			JSONObject result = JSONObject.fromObject(hcResponse.getContext());
			iProcessPoints = result.getInt(AppConstants.BENACO_PROCESSPOINTS);
		}

		return iProcessPoints;
	}
}