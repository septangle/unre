package com.unre.photo.biz.logic.core;

import com.unre.photo.biz.dto.OrderDto;

/**
 * 登录用户信息查询、校验
 * @author TDH
 */
public interface IOrderEngineBiz {

	/**
	 * 
	 * 
	 * @param  
	 *  
	 * @return 
	 * @throws Exception
	 */
	public void updateOrderAndBalance();
	
	/**
	 * 
	 * 
	 * @param  
	 *  
	 * @return 
	 * @throws Exception
	 */
	public void updateOrderAndBalance(OrderDto orderDto) throws Exception;
	
	/**
	 * 
	 * 
	 * @param  
	 *  
	 * @return 
	 * @throws Exception
	 */
	public void updateInsertOrderByOfflineTrade(OrderDto orderDto) throws Exception;
}
