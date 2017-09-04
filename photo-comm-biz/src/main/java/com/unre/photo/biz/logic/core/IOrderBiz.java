package com.unre.photo.biz.logic.core;

import java.io.File;
import java.util.List;

import com.unre.photo.biz.dto.OrderDto;
import com.unre.photo.biz.exception.BusinessException;

/**
 * 订单（线上图片处理/线下消费）处理记录
 * @author jyh
 */
public interface IOrderBiz {

	/**
	 * 通过ID查询Order
	 * 
	 * @param ProcessId  --id
	 * @return ProcessDto --Dto
	 * 
	 * @throws BusinessException
	 */
	public OrderDto findOrderById(Long orderId) throws BusinessException;


	/**
	 * 新增Process
	 * 
	 * @param OrderDto  
	 * @return ProcessDto
	 * @throws BusinessException
	 */
	public OrderDto addOrder(OrderDto orderDto) throws BusinessException;

	/**
	 * 更新Process
	 * 
	 * @param OrderDto --要更新的ProcessDto
	 * 
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean updateOrder(OrderDto orderDto) throws BusinessException;

	/**
	 * 更新Process
	 * 
	 * @param ProcessDto --要更新的ProcessDto
	 * 
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean updateOrderByBenacoId(OrderDto ProcessDto) throws BusinessException;
	
	/**
	 * 删除Process
	 * 
	 * @param id --要删除的Process ID
	 * 
	 * @return boolean 
	 * @throws BusinessException
	 */
	public boolean deleteProcess(Long id) throws BusinessException;
	
	/**
	 * 保存已经成功上传至benaco的图片
	 * 
	 * @param benacoScanId
	 * @param imageFiles --要保存的文件
	 * 
	 * @return boolean 
	 * @throws BusinessException
	 */
	public boolean saveUploadedImages(String benacoScanId,List<File> imageFiles) throws BusinessException;
	

	/**
	 * 更新status信息
	 * 
	 */
	public void updateStatus();
	
	/**
	 * 查询当前用户场景
	 * 
	 * @param memberId
	 * 
	 * @return list
	 */
	public List<OrderDto> querySelStatus(OrderDto orderDto) throws BusinessException;

}
