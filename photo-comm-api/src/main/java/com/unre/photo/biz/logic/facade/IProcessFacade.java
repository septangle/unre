package com.unre.photo.biz.logic.facade;

import com.unre.photo.biz.request.ProcessRequest;
import com.unre.photo.biz.response.ProcessResponse;

public interface IProcessFacade {

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ProcessResponse queryProcess(ProcessRequest request) throws Exception;

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public ProcessResponse findProcessById(ProcessRequest request) throws Exception;

	/**
	 * @param id
	 * @throws Exception
	 */
	public ProcessResponse deleteProcess(ProcessRequest request) throws Exception;

	/**
	 * 更新Process
	 * 
	 * @param request
	 * 
	 * @return ProcessResponse
	 * @throws BusinessException
	 */
	public ProcessResponse updateProcess(ProcessRequest request) throws Exception;

}
