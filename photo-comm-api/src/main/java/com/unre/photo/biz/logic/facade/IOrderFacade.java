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
	public OrderResponse findCurrMemberPanorama(OrderRequest request) throws Exception;
	/**
	 * 更新Order_isDeleted
	 * 
	 * @param request
	 * @return OrderRespnose
	 */
	public OrderResponse removeOrderById(OrderRequest request) throws Exception;
	
	/**
	 * 查询order 条件查询
	 * 
	 * @param request
	 * @return list
	 */
	public OrderResponse queryOrder(OrderRequest reqeust) throws Exception;
	

}
