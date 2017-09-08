package com.unre.photo.controller;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.facade.IPanoramaEngineFacade;
import com.unre.photo.biz.request.PanoramaEngineRequest;
import com.unre.photo.biz.response.PanoramaEngineResponse;
import com.unre.photo.comm.AppConstants;
import com.unre.photo.util.PhotoUrl;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;

@Controller
@RequestMapping("/engine")
public class PanoramaEngineController extends BaseController<PanoramaEngineController> {

	@Autowired
	private IPanoramaEngineFacade panoramaEngineFacade;//photo upload

	@Autowired
	private PhotoUrl photoUrl; //key、url


	/**
	 * 新建场景（上传全景照片）
	 * @param request
	 * @return resp
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(name = "panoramaEngineDto.title", value = "scan名称", required = true, dataType = "string"),
			@ApiImplicitParam(name = "panoramaEngineDto.files", value = "files", required = true, dataType = "List<File>") })
	@RequestMapping(value = "/addPanoramas.do", method = RequestMethod.POST)
	public @ResponseBody PanoramaEngineResponse addPhotos(@RequestBody PanoramaEngineRequest request,
			HttpServletRequest servletRequest) throws Exception {
		HttpSession session = servletRequest.getSession();
		Long id = (Long) session.getAttribute("ID");
		//查询当前用户
		if (id == null)
			throw new BusinessException(AppConstants.MEMBER_NOT_LOGIN_ERROR_CODE,
					AppConstants.MEMBER_NOT_LOGIN_ERROR_MESSAGE);

		request.getPanoramaEngineDto().setUid(id);
		request.getPanoramaEngineDto().setApiKey(photoUrl.getKey());
		request.getPanoramaEngineDto().setApiBaseUrl(photoUrl.getUrl());
		// 实例化一个文件解析器
		@SuppressWarnings("unused")
		CommonsMultipartResolver coMultiResolver = new CommonsMultipartResolver(
				servletRequest.getSession().getServletContext());
		// 判断request请求中是否有文件上传
		List<File> listFiles = request.getPanoramaEngineDto().getFiles();
		if (listFiles.size() > 0 && !listFiles.isEmpty()) {
			List<File> fileUrlList = new ArrayList<File>(); //用来保存文件路径，
			// 获得文件
			List<File> files = request.getPanoramaEngineDto().getFiles();
			for (File file : files) {
				// 获得原始文件名
				String fileName = file.getName();
				// 项目下相对路径
				String path = photoUrl.getPath() + fileName;
				// 创建文件实例
				File tempFile = new File(path); 
				if (!tempFile.getParentFile().exists()) {
					tempFile.getParentFile().createNewFile();
				}
                //判断文件是否存在
				if (!tempFile.exists()) {
					tempFile.createNewFile();
				}
				//将文件添加到list集合
				fileUrlList.add(tempFile);
			}
			request.getPanoramaEngineDto().setFiles(fileUrlList);
		}
		return panoramaEngineFacade.addPhotos(request);
	}

	/**
	 * 处理照片
	 * @param request
	 * @return resp
	 */
	@ApiImplicitParams({
			@ApiImplicitParam(name = "panoramaEngineDto.benacoScanId", value = "Benaco Scan Id", required = true, dataType = "string"), })
	@RequestMapping(value = "/processPanoramas.do", method = RequestMethod.POST)
	public @ResponseBody PanoramaEngineResponse startProcessing(@RequestBody PanoramaEngineRequest request,
			HttpServletRequest servletRequest) throws Exception {
		request.getPanoramaEngineDto().setApiKey(photoUrl.getKey());
		request.getPanoramaEngineDto().setApiBaseUrl(photoUrl.getUrl());
		return panoramaEngineFacade.startProcessing(request);
	}

	/*@ApiImplicitParams({
			@ApiImplicitParam(name = "panoramaEngineDto.benacoScanId", value = "Benaco Scan Id", required = true, dataType = "string"), })
	@RequestMapping(value = "/queryScanStatus.do", method = RequestMethod.POST)
	public @ResponseBody PanoramaEngineResponse queryScanStatus(@RequestBody PanoramaEngineRequest request,
			HttpServletRequest servletRequest) throws Exception {
		request.getPanoramaEngineDto().setApiKey(photoUrl.getKey());
		request.getPanoramaEngineDto().setApiBaseUrl(photoUrl.getUrl());
		return panoramaEngineFacade.queryScanStatus(request);
	}*/
}
