package com.unre.photo.biz.logic.facade;

import com.unre.photo.biz.request.OrderRequest;
import com.unre.photo.biz.response.ProcessResponse;

public interface IProcessFacade {

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ProcessResponse queryProcess(OrderRequest request) throws Exception;

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ProcessResponse findProcessById(OrderRequest request) throws Exception;

	/**
	 * @param id
	 * @throws Exception
	 */
	public ProcessResponse deleteProcess(OrderRequest request) throws Exception;

	/**
	 * 更新Process
	 * 
	 * @param request
	 * 
	 * @return ProcessResponse
	 * @throws BusinessException
	 */
	public ProcessResponse updateProcess(OrderRequest request) throws Exception;

}
