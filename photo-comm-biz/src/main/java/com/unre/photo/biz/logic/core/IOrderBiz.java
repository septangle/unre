package com.unre.photo.biz.logic.core;

import java.io.File;
import java.util.List;

import com.unre.photo.biz.dto.ImageInfoDto;
import com.unre.photo.biz.dto.OrderDto;
import com.unre.photo.biz.dto.SceneDto;
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
	 * @param imageInfoList --要保存的文件
	 * 
	 * @return boolean 
	 * @throws BusinessException
	 */
	public boolean saveUploadedImages(String benacoScanId, List<ImageInfoDto> imageInfoList, List<File> thumbFiles)
			throws BusinessException;

	/**
	 * 查询当前用户场景
	 * 
	 * @param memberId
	 * 
	 * @return list
	 */
	public List<SceneDto> queryMemberScene(OrderDto orderDto) throws BusinessException;

	/**
	 * 通过ID查询Order
	 * 
	 * @param ProcessId  --id
	 * @return ProcessDto --Dto
	 * 
	 * @throws BusinessException
	 */
	public OrderDto findOrder(OrderDto orderDto) throws BusinessException;

	/**	
	* @param orderDto
	* @return
	* @throws BusinessException
	*/
	public List<OrderDto> queryOrder(OrderDto orderDto) throws BusinessException;

	/**
	 * 更新Is_deleted
	 * 
	 * @param orderId  --id
	 * @return boolean
	 */
	public boolean removeOrder(OrderDto orderDto) throws BusinessException;

	/**
	 * 查询消费记录
	 * 
	 * @param memberId
	 */
	public List<OrderDto> findConsumeOrder(OrderDto orderDto) throws BusinessException;

	/**	
	 * @param orderDto
	 * @return
	 * @throws BusinessException
	 */
	public List<OrderDto> findStitchedProcessOrder(OrderDto orderDto) throws BusinessException;
	
	/**
	 * 根据id修改场景描述
	 * @param id
	 * @return
	 * @throws BusinessesException
	 */
    public boolean updateOrderById(OrderDto orderDto) throws BusinessException;
}
