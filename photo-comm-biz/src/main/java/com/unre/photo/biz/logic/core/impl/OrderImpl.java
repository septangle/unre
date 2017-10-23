package com.unre.photo.biz.logic.core.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unre.photo.biz.dto.ImageInfoDto;
import com.unre.photo.biz.dto.OrderDto;
import com.unre.photo.biz.dto.PanoramaDto;
import com.unre.photo.biz.dto.SceneDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.core.IOrderBiz;
import com.unre.photo.biz.logic.core.IPanoramaBiz;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.comm.dal.dao.OrderMapper;
import com.unre.photo.comm.dal.model.Order;
import com.unre.photo.util.ModelUtil;

@Service("Process")
public class OrderImpl implements IOrderBiz {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private IPanoramaBiz panoramaBiz;

	private static final Log LOGGER = LogFactory.getLog(OrderImpl.class);

	@Override
	public OrderDto findOrderById(Long orderId) throws BusinessException {
		OrderDto orderDto = new OrderDto();

		try {
			Order order = orderMapper.selectByPrimaryKey(orderId);
			orderDto = ModelUtil.modelToDto(order, OrderDto.class);
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_FIND_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_FIND_ERROR_CODE, AppConstants.SCAN_FIND_ERROR_MESSAGE);
		}
		return orderDto;
	}

	@SuppressWarnings("unused")
	@Override
	public OrderDto addOrder(OrderDto orderDto) throws BusinessException {
		OrderDto retPhotoDto = null;
		try {
			Order order = ModelUtil.dtoToModel(orderDto, Order.class);
			int i = orderMapper.insertSelective(order);
			Long id = order.getId();
			retPhotoDto = this.findOrderById(id);
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_ADD_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_ADD_ERROR_CODE, AppConstants.SCAN_ADD_ERROR_MESSAGE);
		}
		return retPhotoDto;
	}

	@Override
	public boolean updateOrder(OrderDto orderDto) throws BusinessException {
		boolean flg = false;
		try {
			Order order = ModelUtil.dtoToModel(orderDto, Order.class);
			int number = orderMapper.updateBySelective(order);
			if (number == 0) { // flag == 1 操作成功,否则操作失败
				throw new BusinessException(AppConstants.UPDATE_ORDER_ERROR_CODE,
						AppConstants.UPDATE_ORDER_ERROR_MESSAGE);
			}
			flg = true;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(AppConstants.UPDATE_ORDER_ERROR_CODE, e);
			throw new BusinessException(AppConstants.UPDATE_ORDER_ERROR_CODE, AppConstants.UPDATE_ORDER_ERROR_MESSAGE);
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

	@Override
	@Transactional(rollbackFor = BusinessException.class)
	public boolean saveUploadedImages(String benacoScanId, List<ImageInfoDto> imageInfoList,List<File> thumbFiles) throws BusinessException {
		boolean flg = false;
		try {
			//1.更新scan状态
			Order orderParm = new Order();
			orderParm.setBenacoScanId(benacoScanId);
			List<Order> orderList = orderMapper.selectBySelective(orderParm);
			if (orderList.size() == 0 || orderList.size() > 1) {
				throw new BusinessException(AppConstants.SCAN_BENACO_SCAN_ID_ERROR_CODE,
						AppConstants.SCAN_BENACO_SCAN_ID_ERROR_MESSAGE);
			}
			Order order = orderList.get(0);
			//2. 新增scan item
			int count=0;
			for (ImageInfoDto imageInfo : imageInfoList) {
				String imageFullPath = imageInfo.getFullPath();
				PanoramaDto pScanItemDto = new PanoramaDto();
				pScanItemDto.setOrderId(order.getId());
				pScanItemDto.setImagePath(imageFullPath);
				pScanItemDto.setThumbImagePath(thumbFiles.get(count).toString());
				pScanItemDto.setStitchStatus(AppConstants.PANORAMA_STITCH_STATUS_COMPLETED);
				panoramaBiz.addProcessSource(pScanItemDto);
				count++;
			}
			flg = true;
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_SAVE_IMAGE_ERROR_MESSAGE, e);
			throw new BusinessException(AppConstants.SCAN_SAVE_IMAGE_ERROR_CODE,
					AppConstants.SCAN_SAVE_IMAGE_ERROR_MESSAGE);
		}

		return flg;
	}

	public List<SceneDto> queryMemberScene(OrderDto orderDto) throws BusinessException {
		List<SceneDto> sceneDtoList = new ArrayList<SceneDto>();
		try {
			//通用方法，判断Description字段是否存在，进行模糊查询
			if (orderDto.getStatus()!=null) {
				orderDto.setDescription(orderDto.getStatus());
			}
			Order order = ModelUtil.dtoToModel(orderDto, Order.class);
			List<Order> orderList = orderMapper.selectGetMemberScene(order);
			if (!CollectionUtils.isEmpty(orderList)) {
				for (Order orderParam : orderList) {
					SceneDto sceneDto= new SceneDto();
					sceneDto.setOrderId(orderParam.getId());
					sceneDto.setStatus(orderParam.getStatus());
					sceneDto.setBenacoScanId(orderParam.getBenacoScanId());
					sceneDto.setDescription(orderParam.getDescription());
					//取每个Panorama对象的第一张缩略图
					if (orderParam.getPanorama().get(0).getThumbImagePath()!=null) {
						sceneDto.setThumbImagePath(orderParam.getPanorama().get(0).getThumbImagePath());
             		}
					sceneDto.setCreateTime(orderParam.getCreateTime());
					sceneDtoList.add(sceneDto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(AppConstants.SCAN_QUERY_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_QUERY_ERROR_CODE, AppConstants.SCAN_QUERY_ERROR_MESSAGE);
		}
		return sceneDtoList;
	}

	@Override
	public OrderDto findOrder(OrderDto orderDto) throws BusinessException {

		try {
			Order order = ModelUtil.dtoToModel(orderDto, Order.class);
			Order orderParam = orderMapper.selectOrder(order);
			orderDto = ModelUtil.modelToDto(orderParam, OrderDto.class);
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_FIND_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_FIND_ERROR_CODE, AppConstants.SCAN_FIND_ERROR_MESSAGE);
		}
		return orderDto;
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
			e.printStackTrace();
			LOGGER.error(AppConstants.SCAN_QUERY_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_QUERY_ERROR_CODE, AppConstants.SCAN_QUERY_ERROR_MESSAGE);
		}
		return orderDtoList;
	}

	@Override
	public boolean removeOrder(OrderDto orderDto) throws BusinessException {
		boolean flag = false;
		try {
			Order orderParam = orderMapper.selectByPrimaryKey(orderDto.getId());//查出当前版本号
			orderDto.setIsDeleted(AppConstants.SET_DELETE);//设置已删除
			orderDto.setVersion(orderParam.getVersion());
			Order order = ModelUtil.dtoToModel(orderDto, Order.class);
			int i = orderMapper.updateBySelective(order);
			if (i == 0) {
				throw new BusinessException(AppConstants.REMOVE_ORDER_CODE,
						AppConstants.REMOVE_ORDER_MESSAGE);
			}
			flag = true;
		} catch(BusinessException e){
			LOGGER.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error(AppConstants.REMOVE_ORDER_CODE, e);
			throw new BusinessException(AppConstants.REMOVE_ORDER_CODE,
					AppConstants.REMOVE_ORDER_MESSAGE);

		}
		return flag;
	}


}
