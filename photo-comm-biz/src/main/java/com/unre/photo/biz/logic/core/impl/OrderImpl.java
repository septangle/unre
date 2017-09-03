package com.unre.photo.biz.logic.core.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unre.photo.biz.dto.OrderDto;
import com.unre.photo.biz.dto.PanoramaDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.core.IOrderBiz;
import com.unre.photo.biz.logic.core.IPanoramaBiz;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.comm.dal.dao.OrderMapper;
import com.unre.photo.comm.dal.model.Order;
import com.unre.photo.util.HttpClientResponse;
import com.unre.photo.util.HttpClientUtil;
import com.unre.photo.util.ModelUtil;

import net.sf.json.JSONObject;

@Service("Process")
public class OrderImpl implements IOrderBiz {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private IPanoramaBiz processSourceBiz;

	private static final Log LOGGER = LogFactory.getLog(OrderImpl.class);

	@Override
	public OrderDto findOrderById(Long processId) throws BusinessException {
		OrderDto ProcessDto = null;

		try {
			Order Process = orderMapper.selectByPrimaryKey(processId);
			ProcessDto = ModelUtil.modelToDto(Process, OrderDto.class);
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_FIND_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_FIND_ERROR_CODE, AppConstants.SCAN_FIND_ERROR_MESSAGE);
		}
		return ProcessDto;
	}

	@Override
	public List<OrderDto> queryOrder(OrderDto orderDto) throws BusinessException {
		List<OrderDto> orderDtoList = new ArrayList<OrderDto>();
		try {
			Order order = ModelUtil.dtoToModel(orderDto, Order.class);
			List<Order> orderList = orderMapper.selectBySelective(order);
			if (!CollectionUtils.isEmpty(orderList)) {
				for (Order p : orderList) {
					orderDtoList.add(ModelUtil.modelToDto(p, OrderDto.class));
				}
			}
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_QUERY_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_QUERY_ERROR_CODE, AppConstants.SCAN_QUERY_ERROR_MESSAGE);
		}
		return orderDtoList;
	}

	@SuppressWarnings("unused")
	@Override
	public OrderDto addOrder(OrderDto processDto) throws BusinessException {
		OrderDto retPhotoDto = null;
		try {
			Order process = ModelUtil.dtoToModel(processDto, Order.class);
			int i = orderMapper.insertSelective(process);
			Long id = process.getId();
			retPhotoDto = this.findOrderById(id);
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_ADD_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_ADD_ERROR_CODE, AppConstants.SCAN_ADD_ERROR_MESSAGE);
		}
		return retPhotoDto;
	}

	@Override
	public boolean updateOrder(OrderDto processDto) throws BusinessException {
		boolean flg = false;
		try {
			Order Process = ModelUtil.dtoToModel(processDto, Order.class);
			int number = orderMapper.updateBySelective(Process);
			if (number == 0) { // flag == 1 操作成功,否则操作失败
				throw new BusinessException(AppConstants.SCAN_UPDATE_ERROR_CODE,
						AppConstants.SCAN_UPDATE_ERROR_MESSAGE);
			}
			flg = true;
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_UPDATE_ERROR_MESSAGE, e);
			throw new BusinessException(AppConstants.SCAN_UPDATE_ERROR_CODE, AppConstants.SCAN_UPDATE_ERROR_MESSAGE);
		}
		return flg;
	}

	@Override
	public boolean updateOrderByBenacoId(OrderDto processDto) throws BusinessException {
		boolean flg = false;
		try {
			//1.先查出来
			Order pScanParm = new Order();
			pScanParm.setBenacoScanId(processDto.getBenacoScanId());
			List<Order> pScanList = orderMapper.selectBySelective(pScanParm);
			if (pScanList.size() == 0 || pScanList.size() > 1) {
				throw new BusinessException(AppConstants.SCAN_BENACO_SCAN_ID_ERROR_CODE,
						AppConstants.SCAN_BENACO_SCAN_ID_ERROR_MESSAGE);
			}
			Order pScan = pScanList.get(0);
			pScan.setStatus(AppConstants.SFILE_PROCESSING);
			
			//2.后更新scan状态
			int i = orderMapper.updateOrderByBenacoId(pScan);
			if (i != 1) { // i == 1 操作成功,否则操作失败
				throw new BusinessException(AppConstants.SCAN_UPDATE_ERROR_CODE,
						AppConstants.SCAN_UPDATE_ERROR_MESSAGE);
			}
			flg = true;
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_UPDATE_ERROR_MESSAGE, e);
			throw new BusinessException(AppConstants.SCAN_UPDATE_ERROR_CODE, AppConstants.SCAN_UPDATE_ERROR_MESSAGE);
		}

		return flg;
	}

	@Override
	public boolean deleteProcess(Long id) throws BusinessException {
		boolean flg = false;
		try {
			int delScan = orderMapper.deleteByPrimaryKey(id);
			if (delScan == 1) {
				flg = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(AppConstants.SCAN__DELETE_SCAN_ID_ERROR_CODE,
					AppConstants.SCAN__DELETE_SCAN_ID_ERROR_MESSAGE);
		}
		return flg;
	}

	@SuppressWarnings("unused")
	@Override
	@Transactional(rollbackFor = BusinessException.class)
	public boolean saveUploadedImages(String benacoScanId, List<File> imageFiles) throws BusinessException {
		boolean flg = false;
		try {
			//1.更新scan状态
			Order pScanParm = new Order();
			pScanParm.setBenacoScanId(benacoScanId);
			List<Order> pScanList = orderMapper.selectBySelective(pScanParm);
			if (pScanList.size() == 0 || pScanList.size() > 1) {
				throw new BusinessException(AppConstants.SCAN_BENACO_SCAN_ID_ERROR_CODE,
						AppConstants.SCAN_BENACO_SCAN_ID_ERROR_MESSAGE);
			}
			Order pScan = pScanList.get(0);
			pScan.setStatus(AppConstants.SFILE_UPLOAD_COMPLETE);
			int i = orderMapper.updateOrderByBenacoId(pScan);

			//2. 新增scan item
			for (File f : imageFiles) {
				String imageFullPath = f.getPath() + f.getName();
				PanoramaDto pScanItemDto = new PanoramaDto();
			//	pScanItemDto.setBenacoScanId(benacoScanId);
				pScanItemDto.setImagePath(imageFullPath);
				processSourceBiz.addProcessSource(pScanItemDto);
			}
			flg = true;

		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_SAVE_IMAGE_ERROR_MESSAGE, e);
			throw new BusinessException(AppConstants.SCAN_SAVE_IMAGE_ERROR_CODE,
					AppConstants.SCAN_SAVE_IMAGE_ERROR_MESSAGE);
		}

		return flg;
	}

	public void updateStatus() {
		Order p = new Order();
		List<Order> processList = orderMapper.selectUnclosedOrder();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("key", "3c7c6941-2204-4ee7-a4b5-0981e0e6e09c");
		JSONObject json = JSONObject.fromObject(params);
		for (int i = 0; i < processList.size(); i++) {
			String photoUrl = "https://beta.benaco.com/api/beta/scans/id/" + processList.get(i).getBenacoScanId() + "/status";
			HttpClientResponse hcResponse = HttpClientUtil.doPost(photoUrl, json);
			String httpRetCode = hcResponse.getCode();
			if ("200".equals(httpRetCode)) {
				String context = hcResponse.getContext();
				JSONObject result = JSONObject.fromObject(context);
				String status = result.getString("status");
				if ("failed".equals(status)) {
					p.setBenacoScanId(processList.get(i).getBenacoScanId());
                    p.setStatus("4");
                    orderMapper.updateStatus(p);
				}else if ("completed".equals(status)) {
					p.setBenacoScanId(processList.get(i).getBenacoScanId());
					p.setStatus("5");
					orderMapper.updateStatus(p);
				}
			}
		}

	}
}