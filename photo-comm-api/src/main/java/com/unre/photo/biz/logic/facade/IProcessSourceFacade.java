package com.unre.photo.biz.logic.facade;

import com.unre.photo.biz.request.ProcessSourceRequest;
import com.unre.photo.biz.response.ProcessSourceResponse;

public interface IProcessSourceFacade {
	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ProcessSourceResponse queryProcessSource(ProcessSourceRequest request) throws Exception;

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ProcessSourceResponse findProcessSourceById(ProcessSourceRequest request) throws Exception;

	/**
	 * @param id
	 * @throws Exception
	 */
	public ProcessSourceResponse deleteProcessSource(Long id) throws Exception;
	
	/**
	 * 更新ProcessSource
	 * 
	 * @param request
	 * 
	 * @return boolean
	 * @throws BusinessException
	 */
	public ProcessSourceResponse updateProcessSource(ProcessSourceRequest request) throws Exception;
}
