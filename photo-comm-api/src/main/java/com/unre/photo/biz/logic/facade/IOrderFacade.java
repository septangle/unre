package com.unre.photo.biz.logic.facade;

import com.unre.photo.biz.request.OrderRequest;
import com.unre.photo.biz.response.OrderResponse;

public interface IOrderFacade {


	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public OrderResponse findProcessById(OrderRequest request) throws Exception;


	/**
	 * 更新Process
	 * 
	 * @param request
	 * 
	 * @return ProcessResponse
	 * @throws BusinessException
	 */
	public OrderResponse updateOrder(OrderRequest request) throws Exception;
	
	/**
	 * 查询当前用户场景
	 * @param memberid
	 * 
	 * @return list
	 */
	public OrderResponse queryStatus(OrderRequest request) throws Exception;
	

}
