package com.unre.photo.biz.logic.core.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unre.photo.biz.dto.MemberDto;
import com.unre.photo.biz.dto.OrderDto;
import com.unre.photo.biz.dto.PanoramaDto;
import com.unre.photo.biz.dto.PriceDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.core.IMemberBiz;
import com.unre.photo.biz.logic.core.IOrderBiz;
import com.unre.photo.biz.logic.core.IPanoramaBiz;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.comm.dal.dao.BalanceMapper;
import com.unre.photo.comm.dal.dao.OrderMapper;
import com.unre.photo.comm.dal.dao.PanoramaMapper;
import com.unre.photo.comm.dal.model.Balance;
import com.unre.photo.comm.dal.model.Order;
import com.unre.photo.comm.dal.model.Panorama;
import com.unre.photo.util.ModelUtil;

@Service("Process")
public class OrderImpl implements IOrderBiz {

	@Autowired
	private OrderMapper orderMapper;

	@Autowired
	private IPanoramaBiz panoramaBiz;

	@Autowired
	private IMemberBiz memberBizImpl;

	@Autowired
	private BalanceMapper balanceMapper;

	@Autowired
	private PanoramaMapper panoramaMapper;

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
	public boolean updateOrder(OrderDto processDto) throws BusinessException {
		boolean flg = false;
		try {
			processDto.setIsDeleted(AppConstants.SET_DELETE);
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
	public boolean updateOrderByBenacoId(OrderDto orderDto) throws BusinessException {
		boolean flg = false;//uid    benoncaid

		try {
			//1.先查出来
			Order pScanParm = new Order();
			pScanParm.setBenacoScanId(orderDto.getBenacoScanId());
			List<Order> pScanList = orderMapper.selectBySelective(pScanParm);
			if (pScanList.size() == 0 || pScanList.size() > 1) {
				throw new BusinessException(AppConstants.SCAN_BENACO_SCAN_ID_ERROR_CODE,
						AppConstants.SCAN_BENACO_SCAN_ID_ERROR_MESSAGE);
			}
			Order pScan = pScanList.get(0);
			pScan.setStatus(AppConstants.SFILE_PROCESSING);
			//根据uid查询该会员信息
			MemberDto members = memberBizImpl.findMemberById(orderDto.getMemberId());
			//取Member中level等级  返回折扣后的单价
			PriceDto p = memberBizImpl.SelPriceById(members);
			//根据uid查询order表  查询出order_id
			Order o = orderMapper.SelOrder(pScanParm);
			Panorama ps = new Panorama();
			//将order_id 放入panorama表
			ps.setOrderId(o.getId());
			List<Panorama> plists = panoramaMapper.selectByPhotoCount(ps);
			//算出点数
			int fileSize = plists.size();
			//接收打折扣后单价
			Double price = p.getPrice();
			//点数*单价(折扣后单价)
			Double money = fileSize * price;
			//计算出应付金额，BigDecimal类型
			BigDecimal d1TobigDe = new BigDecimal(money);
			//应付金额，插入order表
			pScan.setTotalAmount(d1TobigDe);
			//折扣后单价，插入order表
			pScan.setGoodsActualPrice(new BigDecimal(price));
			//2.后更新scan状态
			int i = orderMapper.updateByAmount(pScan);
			if (i != 1) { // i == 1 操作成功,否则操作失败
				throw new BusinessException(AppConstants.SCAN_UPDATE_ERROR_CODE,
						AppConstants.SCAN_UPDATE_ERROR_MESSAGE);
			}
			//插入balance表  余额=余额-应付金额  冻结金额=冻结总额相加
			Balance balance = balanceMapper.selectByMemberID(orderDto.getMemberId());
			Balance bl = new Balance();
			BigDecimal b = balance.getAmount().subtract(d1TobigDe);//相减后的余额
			bl.setMemberId(orderDto.getMemberId());//会员ID
			bl.setAmount(b);//余额
			bl.setFreezeAmount(d1TobigDe.add(balance.getFreezeAmount()));//冻结金额
			balanceMapper.updateByPrimaryKeySelective(bl);
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

	@Override
	@Transactional(rollbackFor = BusinessException.class)
	public boolean saveUploadedImages(String benacoScanId, List<File> imageFiles) throws BusinessException {
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
			for (File f : imageFiles) {
				String imageFullPath = f.getPath();
				PanoramaDto pScanItemDto = new PanoramaDto();
				pScanItemDto.setOrderId(order.getId());
				pScanItemDto.setImagePath(imageFullPath);
				panoramaBiz.addProcessSource(pScanItemDto);
			}
			flg = true;
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_SAVE_IMAGE_ERROR_MESSAGE, e);
			throw new BusinessException(AppConstants.SCAN_SAVE_IMAGE_ERROR_CODE,
					AppConstants.SCAN_SAVE_IMAGE_ERROR_MESSAGE);
		}

		return flg;
	}

	@Override
	public List<OrderDto> querySelStatus(OrderDto orderDto) throws BusinessException {
		List<OrderDto> orderDtoList = new ArrayList<OrderDto>();
		try {
			Order order = ModelUtil.dtoToModel(orderDto, Order.class);
			List<Order> orderList = orderMapper.SelStatus(order);
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
	public OrderDto findOrder(OrderDto orderDto) throws BusinessException {

		try {
			Order order = ModelUtil.dtoToModel(orderDto, Order.class);
			Order orders = orderMapper.SelOrder(order);
			orderDto = ModelUtil.modelToDto(orders, OrderDto.class);
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_FIND_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_FIND_ERROR_CODE, AppConstants.SCAN_FIND_ERROR_MESSAGE);
		}
		return orderDto;
	}

}
