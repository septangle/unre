package com.unre.photo.biz.logic.facade;

import com.unre.photo.biz.request.OrderRequest;
import com.unre.photo.biz.request.PanoramaRequest;
import com.unre.photo.biz.response.OrderResponse;
import com.unre.photo.biz.response.PanoramaResponse;

public interface IPanoramaFacade {
	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public PanoramaResponse queryProcessSource(PanoramaRequest request) throws Exception;

	/**
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public PanoramaResponse findProcessSourceById(PanoramaRequest request) throws Exception;

	
	/**
	 * 更新Panorama
	 * 
	 * @param request
	 * 
	 * @return boolean
	 * @throws BusinessException
	 */
	public PanoramaResponse updatePanorama(PanoramaRequest request) throws Exception;
}
