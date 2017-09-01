package com.unre.photo.biz.logic.core;

import java.io.File;
import java.util.List;

import com.unre.photo.biz.dto.ProcessDto;
import com.unre.photo.biz.exception.BusinessException;

/**
 * 图片扫描处理记录
 * @author jyh
 */
public interface IProcessBiz {

	/**
	 * 通过ID查询Process
	 * 
	 * @param ProcessId  --id
	 * @return ProcessDto --Dto
	 * 
	 * @throws BusinessException
	 */
	public ProcessDto findProcessById(Long processId) throws BusinessException;

	/**
	 * 查询满足条件的Process
	 * 
	 * @param ProcessDto --Dto
	 * @return List
	 * 
	 * @throws BusinessException
	 */
	public List<ProcessDto> queryProcess(ProcessDto processDto) throws BusinessException;

	/**
	 * 新增Process
	 * 
	 * @param ProcessDto  
	 * @return ProcessDto
	 * @throws BusinessException
	 */
	public ProcessDto addProcess(ProcessDto processDto) throws BusinessException;

	/**
	 * 更新Process
	 * 
	 * @param ProcessDto --要更新的ProcessDto
	 * 
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean updateProcess(ProcessDto processDto) throws BusinessException;

	/**
	 * 更新Process
	 * 
	 * @param ProcessDto --要更新的ProcessDto
	 * 
	 * @return boolean
	 * @throws BusinessException
	 */
	public boolean updateProcessByBenacoId(ProcessDto ProcessDto) throws BusinessException;
	
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
	public void queryStatus();

}
