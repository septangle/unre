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

import com.unre.photo.biz.dto.ProcessDto;
import com.unre.photo.biz.dto.ProcessSourceDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.core.IProcessBiz;
import com.unre.photo.biz.logic.core.IProcessSourceBiz;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.comm.dal.dao.ProcessMapper;
import com.unre.photo.comm.dal.model.Process;
import com.unre.photo.util.HttpClientResponse;
import com.unre.photo.util.HttpClientUtil;
import com.unre.photo.util.ModelUtil;

import net.sf.json.JSONObject;

@Service("Process")
public class ProcessImpl implements IProcessBiz {

	@Autowired
	private ProcessMapper processMapper;

	@Autowired
	private IProcessSourceBiz processSourceBiz;

	private static final Log LOGGER = LogFactory.getLog(ProcessImpl.class);

	@Override
	public ProcessDto findProcessById(Long processId) throws BusinessException {
		ProcessDto ProcessDto = null;

		try {
			Process Process = processMapper.selectByPrimaryKey(processId);
			ProcessDto = ModelUtil.modelToDto(Process, ProcessDto.class);
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_FIND_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_FIND_ERROR_CODE, AppConstants.SCAN_FIND_ERROR_MESSAGE);
		}
		return ProcessDto;
	}

	@Override
	public List<ProcessDto> queryProcess(ProcessDto processDto) throws BusinessException {
		List<ProcessDto> processoList = new ArrayList<ProcessDto>();
		try {
			Process Process = ModelUtil.dtoToModel(processDto, Process.class);
			List<Process> ProcessList = processMapper.selectBySelective(Process);
			if (!CollectionUtils.isEmpty(ProcessList)) {
				for (Process p : ProcessList) {
					processoList.add(ModelUtil.modelToDto(p, ProcessDto.class));
				}
			}
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_QUERY_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_QUERY_ERROR_CODE, AppConstants.SCAN_QUERY_ERROR_MESSAGE);
		}
		return processoList;
	}

	@SuppressWarnings("unused")
	@Override
	public ProcessDto addProcess(ProcessDto processDto) throws BusinessException {
		ProcessDto retPhotoDto = null;
		try {
			Process process = ModelUtil.dtoToModel(processDto, Process.class);
			int i = processMapper.insertSelective(process);
			Long id = process.getId();
			retPhotoDto = this.findProcessById(id);
		} catch (Exception e) {
			LOGGER.error(AppConstants.SCAN_ADD_ERROR_CODE, e);
			throw new BusinessException(AppConstants.SCAN_ADD_ERROR_CODE, AppConstants.SCAN_ADD_ERROR_MESSAGE);
		}
		return retPhotoDto;
	}

	@Override
	public boolean updateProcess(ProcessDto processDto) throws BusinessException {
		boolean flg = false;
		try {
			Process Process = ModelUtil.dtoToModel(processDto, Process.class);
			int number = processMapper.updateBySelective(Process);
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
	public boolean updateProcessByBenacoId(ProcessDto processDto) throws BusinessException {
		boolean flg = false;
		try {
			//1.先查出来
			Process pScanParm = new Process();
			pScanParm.setBenacoScanId(processDto.getBenacoScanId());
			List<Process> pScanList = processMapper.selectBySelective(pScanParm);
			if (pScanList.size() == 0 || pScanList.size() > 1) {
				throw new BusinessException(AppConstants.SCAN_BENACO_SCAN_ID_ERROR_CODE,
						AppConstants.SCAN_BENACO_SCAN_ID_ERROR_MESSAGE);
			}
			Process pScan = pScanList.get(0);
			pScan.setStatus(AppConstants.SFILE_PROCESSING);
			//2.后更新scan状态
			int i = processMapper.updateProcessByBenacoId(pScan);
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
			int delScan = processMapper.deleteByPrimaryKey(id);
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
			Process pScanParm = new Process();
			pScanParm.setBenacoScanId(benacoScanId);
			List<Process> pScanList = processMapper.selectBySelective(pScanParm);
			if (pScanList.size() == 0 || pScanList.size() > 1) {
				throw new BusinessException(AppConstants.SCAN_BENACO_SCAN_ID_ERROR_CODE,
						AppConstants.SCAN_BENACO_SCAN_ID_ERROR_MESSAGE);
			}
			Process pScan = pScanList.get(0);
			pScan.setStatus(AppConstants.SFILE_UPLOAD_COMPLETE);
			int i = processMapper.updateProcessByBenacoId(pScan);

			//2. 新增scan item
			for (File f : imageFiles) {
				String imageFullPath = f.getPath() + f.getName();
				ProcessSourceDto pScanItemDto = new ProcessSourceDto();
				pScanItemDto.setBenacoScanId(benacoScanId);
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

	public void queryStatus() {
		Process p = new Process();
		List<Process> processList = processMapper.selByStatus();
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
                    processMapper.upByStatus(p);
				}else if ("completed".equals(status)) {
					p.setBenacoScanId(processList.get(i).getBenacoScanId());
					p.setStatus("5");
					processMapper.upByStatus(p);
				}
			}
		}

	}
}
