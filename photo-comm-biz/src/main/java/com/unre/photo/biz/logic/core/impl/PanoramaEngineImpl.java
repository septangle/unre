package com.unre.photo.biz.logic.core.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unre.photo.biz.dto.OrderDto;
import com.unre.photo.biz.dto.PanoramaDto;
import com.unre.photo.biz.dto.PanoramaEngineDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.core.IOrderBiz;
import com.unre.photo.biz.logic.core.IPanoramaBiz;
import com.unre.photo.biz.logic.core.IPanoramaEngineBiz;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.util.HttpClientResponse;
import com.unre.photo.util.HttpClientUtil;
import com.unre.photo.util.JsonUtil;

import net.sf.json.JSONObject;

@Service
public class PanoramaEngineImpl implements IPanoramaEngineBiz {

	private static final Log LOGGER = LogFactory.getLog(PanoramaEngineImpl.class);

	@Autowired
	private IOrderBiz orderBizImpl;
	@Autowired
	private IPanoramaBiz panoramaBizImpl;

	@Override
	public PanoramaEngineDto createScan(PanoramaEngineDto panoramaEngineDto) throws Exception {

		String benacoScanId;
		PanoramaEngineDto retPanEngineDto = new PanoramaEngineDto();
		retPanEngineDto.setFiles(panoramaEngineDto.getFiles());
		retPanEngineDto.setApiBaseUrl(panoramaEngineDto.getApiBaseUrl());
		retPanEngineDto.setApiKey(panoramaEngineDto.getApiKey());
		retPanEngineDto.setUid(panoramaEngineDto.getUid());
		try {
			//1. 调用BenacoAPI生成scan id
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("key", panoramaEngineDto.getApiKey());
			params.put("title", panoramaEngineDto.getTitle());
			JSONObject json = JSONObject.fromObject(params);
			String newScanUrl = panoramaEngineDto.getApiBaseUrl() + "new";
			//scanId = HttpClientUtil.doPost(newScanUrl, json);
			HttpClientResponse hcResponse = HttpClientUtil.doPost(newScanUrl, json);
			//TODO ""bf6e0901-21c4-4363-9429-de168e0d943a"" 返回对了一对括号
			benacoScanId = hcResponse.getContext();
			if (benacoScanId.startsWith("\""))
				benacoScanId = benacoScanId.substring(1);
			if (benacoScanId.endsWith("\""))
				benacoScanId = benacoScanId.substring(0, benacoScanId.length() - 1);
		
			//返回 benaco scan id
			panoramaEngineDto.setBenacoScanId(benacoScanId);
			retPanEngineDto.setBenacoScanId(benacoScanId);
		} catch (Exception e) {
			LOGGER.error(AppConstants.PENGINE_CREATE_SCAN_ERROR_CODE, e);
			throw new BusinessException(AppConstants.PENGINE_CREATE_SCAN_ERROR_CODE,
					AppConstants.PENGINE_CREATE_SCAN_ERROR_MESSAGE);
		}
		return retPanEngineDto;
	}

	@SuppressWarnings("unused")
	@Override
	public PanoramaEngineDto addPhotos(PanoramaEngineDto panoramaEngineDto) throws Exception {

		PanoramaEngineDto retPanEngineDto = new PanoramaEngineDto();

		try {
			//1. 上传文件至benaco服务器
			String benacoScanId = panoramaEngineDto.getBenacoScanId();
			String addPhotosUrl = panoramaEngineDto.getApiBaseUrl() + "id/" + benacoScanId + "/add-photos";
			List<File> imageFiles = panoramaEngineDto.getFiles();
			HttpClientResponse hcResponse = HttpClientUtil.doPostMultipart(addPhotosUrl,
					panoramaEngineDto.getBenacoScanId(), imageFiles);

			//2. 更新状态     创建订单
			OrderDto orderDto = new OrderDto();
			orderDto.setBenacoScanId(benacoScanId);
			orderDto.setMemberId(panoramaEngineDto.getUid());
			orderDto.setDescription(panoramaEngineDto.getTitle());
			OrderDto order=orderBizImpl.addOrder(orderDto);
			//3.添加图片路径
			orderBizImpl.saveUploadedImages(benacoScanId, imageFiles);
			retPanEngineDto.setOrderId(order.getId());
		} catch (Exception e) {
			LOGGER.error(AppConstants.PENGINE_ADD_PHOTOS_ERROR_CODE, e);
			throw new BusinessException(AppConstants.PENGINE_ADD_PHOTOS_ERROR_CODE,
					AppConstants.PENGINE_ADD_PHOTOS_ERROR_MESSAGE);
		}

		return retPanEngineDto;
	}

	@Override
	public boolean startBenacoProcess(PanoramaEngineDto panoramaEngineDto) throws Exception {
		boolean retFlg = false;
		try {
			
			//1.调用benaco处理接口
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("key", panoramaEngineDto.getApiKey());
			JSONObject json = JSONObject.fromObject(params);
			String benacoScanId = panoramaEngineDto.getBenacoScanId();
			String addPhotosUrl = panoramaEngineDto.getApiBaseUrl() + "id/" + benacoScanId + "/start-processing";
			HttpClientResponse hcResponse = HttpClientUtil.doPost(addPhotosUrl, json);

			String retCode = hcResponse.getCode();
			//2. 更新scan表中scanid对应记录的状态

			if ("200".equals(retCode)) {
				OrderDto orderDto = new OrderDto();
				orderDto.setBenacoScanId(benacoScanId);
				orderDto.setMemberId(panoramaEngineDto.getUid());
				orderDto.setId(panoramaEngineDto.getOrderId());
				orderBizImpl.updateOrderByBenacoId(orderDto);
				retFlg = true;
			}

		} catch (Exception e) {
			LOGGER.error(AppConstants.PENGINE_START_PROCESS_ERROR_MESSAGE, e);
			throw new BusinessException(AppConstants.PENGINE_START_PROCESS_ERROR_CODE,
					AppConstants.PENGINE_START_PROCESS_ERROR_MESSAGE);
		}
		return retFlg;
	}

	@Override
	public boolean startPhotoProcess(PanoramaEngineDto pEngineDto) throws Exception {
		boolean retFlg = false;
		try {
			//1.查询全景表中未进行2D照片拼接的记录
			PanoramaDto panDtoParm = new PanoramaDto();
			panDtoParm.setOrderId(pEngineDto.getOrderId());
			List<PanoramaDto> PanDtoList = panoramaBizImpl.queryUnStitchProcessSource(panDtoParm);
			//2.调用2D照片拼接插件
			for(PanoramaDto panDto : PanDtoList) {
				
			}
			//3.更新PanoramaDto表记录状态
			
			retFlg = true;
		} catch (Exception e) {
			LOGGER.error(AppConstants.PENGINE_START_PHOTO_PROCESS_ERROR_MESSAGE, e);
			throw new BusinessException(AppConstants.PENGINE_START_PHOTO_PROCESS_ERROR_CODE,
					AppConstants.PENGINE_START_PHOTO_PROCESS_ERROR_MESSAGE);
		}
		return retFlg;
	}

	@Override
	public boolean startPanoramaProcess(PanoramaEngineDto pEngineDto) throws Exception {
		boolean retFlg = false;
		try {

			List<OrderDto> orderList = orderBizImpl.queryOrder(null);
			for (OrderDto order : orderList) {
				String benacoScanId = order.getBenacoScanId();
				//2.查询全景表中 2D照片拼接完成的 + 订单状态为：未处理 的记录
				PanoramaDto panDtoParm = new PanoramaDto();
				panDtoParm.setOrderId(pEngineDto.getOrderId());
				List<PanoramaDto> panDtoList = panoramaBizImpl.queryUnStitchProcessSource(panDtoParm);

				if (panDtoList == null || panDtoList.size() == 0) {
					return true;
				}

				//3.生成 imageFiles
				List<File> imageFiles = new ArrayList<File>();
				for (PanoramaDto panDto : panDtoList) {
					File f = new File(panDto.getImagePath());
					imageFiles.add(f);
				}

				//4.调用Benaco 3D照片上传接口
				String addPhotosUrl = pEngineDto.getApiBaseUrl() + "id/" + benacoScanId + "/add-photos";
				HttpClientResponse hcResponse = HttpClientUtil.doPostMultipart(addPhotosUrl,
						pEngineDto.getBenacoScanId(), imageFiles);

				if (!"200".equals(hcResponse.getCode())) {
					return false;
				}
				//5.更新订单状态为 "处理中"
				OrderDto orderParm = new OrderDto();
				orderParm.setId(pEngineDto.getOrderId());
				orderParm.setStatus(AppConstants.ORDER_STATUS_PROCESSING);
				orderBizImpl.updateOrder(orderParm);

				//6.调用Benaco process接口
				this.startBenacoProcess(pEngineDto);

				//7.更新PanoramaDto表记录状态+订单状态
				panoramaBizImpl.updateAfterBenacoProcess(pEngineDto.getOrderId());
			}
			retFlg = true;
		} catch (Exception e) {
			LOGGER.error(AppConstants.PENGINE_START_PANORAMA_PROCESS_ERROR_MESSAGE, e);
			throw new BusinessException(AppConstants.PENGINE_START_PANORAMA_PROCESS_ERROR_CODE,
					AppConstants.PENGINE_START_PANORAMA_PROCESS_ERROR_MESSAGE);
		}
		return retFlg;
	}

	@Override
	public PanoramaEngineDto queryScanStatus(PanoramaEngineDto panoramaEngineDto) throws Exception {

		PanoramaEngineDto PEngineDto = new PanoramaEngineDto();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("key", panoramaEngineDto.getApiKey());
			JSONObject json = JSONObject.fromObject(params);
			String addPhotosUrl = panoramaEngineDto.getApiBaseUrl() + "id/" + panoramaEngineDto.getBenacoScanId()
					+ "/status";
			HttpClientResponse hcResponse = HttpClientUtil.doPost(addPhotosUrl, json);
			String httpRetCode = hcResponse.getCode();
			if ("200".equals(httpRetCode)) {
				String context = hcResponse.getContext();
				Map<String, Object> map = JsonUtil.toMap(context);
				PEngineDto.setScanStatus(map.get("status").toString());
			}
		} catch (Exception e) {
			LOGGER.error(AppConstants.PENGINE_QUERY_SCAN_STATUS_ERROR_CODE, e);
			throw new BusinessException(AppConstants.PENGINE_QUERY_SCAN_STATUS_ERROR_CODE,
					AppConstants.PENGINE_QUERY_SCAN_STATUS_ERROR_MESSAGE);
		}

		return PEngineDto;
	}

	@Override
	public PanoramaEngineDto previewScan(PanoramaEngineDto panoramaEngineDto) throws Exception {
		PanoramaEngineDto retPanEngineDto = new PanoramaEngineDto();
		try {
			String previewUrl = "https://beta.benaco.com/view/" + panoramaEngineDto.getBenacoScanId();
			retPanEngineDto.setPreviewUrl(previewUrl);
		} catch (Exception e) {
			LOGGER.error(AppConstants.PENGINE_PREVIEW_SCAN_STATUS_ERROR_CODE, e);
			throw new BusinessException(AppConstants.PENGINE_PREVIEW_SCAN_STATUS_ERROR_CODE,
					AppConstants.PENGINE_PREVIEW_SCAN_STATUS_ERROR_MESSAGE);
		}

		return retPanEngineDto;
	}

}
