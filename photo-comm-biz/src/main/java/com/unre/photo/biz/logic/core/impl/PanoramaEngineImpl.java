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

import com.unre.photo.biz.dto.ImageInfoDto;
import com.unre.photo.biz.dto.OrderDto;
import com.unre.photo.biz.dto.PanoramaDto;
import com.unre.photo.biz.dto.PanoramaEngineDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.core.IOrderBiz;
import com.unre.photo.biz.logic.core.IPanoramaBiz;
import com.unre.photo.biz.logic.core.IPanoramaEngineBiz;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.comm.dal.dao.PhotoMapper;
import com.unre.photo.comm.dal.model.Photo;
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
	@Autowired
	private OrderEngine orderEngine;
	@Autowired
	private PhotoMapper photoMapper;

	@Override
	public PanoramaEngineDto createScan(PanoramaEngineDto panoramaEngineDto) throws Exception {

		String benacoScanId;
		PanoramaEngineDto retPanEngineDto = new PanoramaEngineDto();
		retPanEngineDto.setImageInfoList(panoramaEngineDto.getImageInfoList());
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

	@Override
	public PanoramaEngineDto addPhotos(PanoramaEngineDto panoramaEngineDto) throws Exception {

		PanoramaEngineDto retPanEngineDto = new PanoramaEngineDto();

		try {
			//1. 取得参数benocoId、imageFiles、number
			String benacoScanId = panoramaEngineDto.getBenacoScanId();
			List<ImageInfoDto> imageFiles = panoramaEngineDto.getImageInfoList();
			List<File> thumbFiles=panoramaEngineDto.getFileThumb();
			String number = panoramaEngineDto.getNumber();
			int panoramanumber = imageFiles.size() / Integer.parseInt(number);//panorama照片数量
			//2. 更新状态     创建订单
			OrderDto orderDto = new OrderDto();
			orderDto.setBenacoScanId(benacoScanId);
			orderDto.setMemberId(panoramaEngineDto.getUid());
			orderDto.setDescription(panoramaEngineDto.getTitle());
			orderDto.setGoodsNum(panoramanumber);//图片数量除以点数
			orderDto.setGoodsId(AppConstants.GOODS_ID_BENACO);//商品id
			OrderDto order = orderBizImpl.addOrder(orderDto);

			//3.添加图片
			if ((AppConstants.NUMBER_MESSAGE_3D).equals(number)) {//3D
				orderBizImpl.saveUploadedImages(benacoScanId, imageFiles,thumbFiles);
			} else {//2D
				int numberOfOnepoint = Integer.parseInt(number);//点数
				if (imageFiles.size() % numberOfOnepoint != 0) {
					throw new BusinessException(AppConstants.ADD_PHOTOS_ERROR_CODE,
							AppConstants.ADD_PHOTOS_ERROR_MESSAGE);
				}

				PanoramaDto panoramaDto = new PanoramaDto();
				panoramaDto.setOrderId(order.getId());
				panoramaDto.setStitchStatus(AppConstants.PANORAMA_STITCH_INIT);
				for (int i = 0; i < panoramanumber; i++) {
					// insert panorama table
					panoramaDto.setThumbImagePath(thumbFiles.get(i).toString());
					PanoramaDto pDto = panoramaBizImpl.addProcessSource(panoramaDto);
        			for (int j = 0; j < numberOfOnepoint; j++) {
						// insert photo table
						Photo photo = new Photo();
						photo.setPanoramaId(pDto.getId());
						photo.setImagePath(imageFiles.get(i * numberOfOnepoint + j).getFullPath());
						photo.setOrderId(order.getId());
						photo.setThumbImagePath(imageFiles.get(i * numberOfOnepoint + j).getFullPath());
						photoMapper.insertSelective(photo);
					}
				}
			}
			//调用updateOrderAndBalance 更新order信息  
			orderEngine.updateOrderAndBalance(order);
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
			String addPhotosUrl = panoramaEngineDto.getApiBaseUrl() + benacoScanId + "/start-processing";
			HttpClientResponse hcResponse = HttpClientUtil.doPost(addPhotosUrl, json);

			String retCode = hcResponse.getCode();
			//2. 更新scan表中scanid对应记录的状态
           	if ("200".equals(retCode)) {
				retFlg = true;
			}

		} catch (Exception e) {
			LOGGER.error(AppConstants.PENGINE_START_PROCESS_ERROR_MESSAGE, e);
			throw new BusinessException(AppConstants.PENGINE_START_PROCESS_ERROR_CODE,
					AppConstants.PENGINE_START_PROCESS_ERROR_MESSAGE);
		}
		return retFlg;
	}

	@SuppressWarnings("unused")
	@Override
	public boolean startPhotoProcess(PanoramaEngineDto pEngineDto) throws Exception {
		boolean retFlg = false;
		try {
			//1.查询全景表中未进行2D照片拼接的记录
			PanoramaDto panDtoParm = new PanoramaDto();
			panDtoParm.setOrderId(pEngineDto.getOrderId());
			List<PanoramaDto> PanDtoList = panoramaBizImpl.queryUnStitchProcessSource(panDtoParm);
			//2.调用2D照片拼接插件
			for (PanoramaDto panDto : PanDtoList) {

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

			OrderDto orderParm = new OrderDto();
			orderParm.setStatus(AppConstants.ORDER_STATUS_INIT);
			List<OrderDto> orderList = orderBizImpl.queryOrder(orderParm);
			for (OrderDto order : orderList) {
				String benacoScanId = order.getBenacoScanId();
				//2.查询全景表中 2D照片拼接完成的 + 订单状态为：未处理 的记录
				PanoramaDto panDtoParm = new PanoramaDto();
				panDtoParm.setOrderId(pEngineDto.getOrderId());
				List<PanoramaDto> panDtoList = panoramaBizImpl.queryStitchedProcessSource(panDtoParm);

				if (panDtoList == null || panDtoList.size() == 0) {
					continue;
				}

				boolean ignore = false;
				for (PanoramaDto panDto : panDtoList) {//order下面的Panorama全部是拼接成功则上传Benaco服务器
					if (!AppConstants.PANORAMA_STITCHED.equals(panDto.getStitchStatus())) {
						ignore = true;
					}
				}
				if (ignore)
					continue;

				//3.生成 imageFiles
				List<File> imageFiles = new ArrayList<File>();
				for (PanoramaDto panDto : panDtoList) {
					File f = new File(panDto.getImagePath());
					imageFiles.add(f);
				}

				//4.更新订单状态为 "处理中"
				orderParm = new OrderDto();
				orderParm.setId(order.getId());
				orderParm.setStatus(AppConstants.ORDER_STATUS_PROCESSING);
				orderParm.setVersion(order.getVersion());
				orderBizImpl.updateOrder(orderParm);

				//5.调用Benaco 3D照片上传接口
				String addPhotosUrl = pEngineDto.getApiBaseUrl() + benacoScanId + "/add-photos";
				long start = System.currentTimeMillis();
				HttpClientResponse hcResponse = HttpClientUtil.doPostMultipart(addPhotosUrl, pEngineDto.getApiKey(),
						imageFiles);
				long end = System.currentTimeMillis();
				System.out.println("调用Benaco add-photos 耗时==" + (end - start) / 1000 + " 秒");
				if (!"200".equals(hcResponse.getCode())) {
					return false;
				}

				//TODO Benaco 处理上传图片需要一点时间，下面的运行早了会导致失败
				//Thread.sleep(4*60*1000);

				//6.调用Benaco process接口
				pEngineDto.setBenacoScanId(order.getBenacoScanId());
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

	@Override
	public boolean addPhotoStitchCompleted(PanoramaEngineDto panoramaEngineDto) throws Exception {
		boolean flag = false;
		PanoramaDto panoramaDto = new PanoramaDto();
		//取file添加至list
		List<ImageInfoDto> imageInfoList = panoramaEngineDto.getImageInfoList();
		panoramaDto.setOrderId(panoramaEngineDto.getOrderId());
		try {
			//1、查询Panorama中id
			List<PanoramaDto> panoramaList = panoramaBizImpl.queryProcessSource(panoramaDto);
			//2、判断imageList与panoramaList大小
			if (imageInfoList.size() != panoramaList.size()) {
				throw new BusinessException(AppConstants.ADD_PHOTO_STITCH_COMPLETED_CODE,
						AppConstants.ADD_PHOTO_STITCH_COMPLETED_MESSAGE);
			}//3、循环添加至Panorama表
			for (int i = 0; i < panoramaList.size(); i++) {
				panoramaDto.setId(panoramaList.get(i).getId());
				//取imagesList.get(i)，count++
				ImageInfoDto imageInfo = imageInfoList.get(i);
				panoramaDto.setImagePath(imageInfo.getFullPath());
				panoramaDto.setStitchStatus(AppConstants.PANORAMA_STITCHED);
				panoramaBizImpl.updatePanoramaByPrimaryKey(panoramaDto);
			}
			flag = true;
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error(AppConstants.ADD_PHOTO_STITCH_COMPLETED_CODE, e);
			throw new BusinessException(AppConstants.ADD_PHOTO_STITCH_COMPLETED_CODE,
					AppConstants.ADD_PHOTO_STITCH_COMPLETED_MESSAGE);
		}
		return flag;
	}

}
