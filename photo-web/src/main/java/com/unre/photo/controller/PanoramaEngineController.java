package com.unre.photo.controller;

import java.io.File;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import com.unre.photo.biz.logic.facade.IPanoramaEngineFacade;
import com.unre.photo.biz.request.PanoramaEngineRequest;
import com.unre.photo.biz.response.PanoramaEngineResponse;
import com.unre.photo.biz.response.PhotoScanItemResponse;
import com.unre.photo.util.PhotoUrl;
import com.wordnik.swagger.annotations.ApiImplicitParam;
import com.wordnik.swagger.annotations.ApiImplicitParams;
import com.wordnik.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/engine")
public class PanoramaEngineController extends BaseController<PanoramaEngineController> {

	@Autowired
	private IPanoramaEngineFacade panoramaEngineFacade;

	@Autowired
	private PhotoUrl photoUrl;

	@ApiOperation(value = "创建scanID", httpMethod = "POST", response = PhotoScanItemResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "panoramaEngineDto.title", value = "scan名称", required = true, dataType = "string"), })
	@RequestMapping(value = "/createScan.do", method = RequestMethod.POST)
	public @ResponseBody PanoramaEngineResponse createScan(@RequestBody PanoramaEngineRequest request,
			HttpServletRequest servletRequest) throws Exception {
		request.getPanoramaEngineDto().setApiKey(photoUrl.getKey());
		request.getPanoramaEngineDto().setApiBaseUrl(photoUrl.getUrl());
		return panoramaEngineFacade.createScan(request);
	}

	@ApiImplicitParams({
			@ApiImplicitParam(name = "panoramaEngineDto.benacoScanId", value = "scanid名称", required = true, dataType = "string"),
			@ApiImplicitParam(name = "panoramaEngineDto.files", value = "files", required = true, dataType = "List<File>"), })
	@RequestMapping(value = "/addPhotos.do", method = RequestMethod.POST)
	public @ResponseBody PanoramaEngineResponse addPhotos(@RequestBody PanoramaEngineRequest request,
			HttpServletRequest servletRequest) throws Exception {
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
				String path = "E:/springUpload/" + fileName;
				// 创建文件实例
				File tempFile = new File(path); //文件保存路径为pathRoot + path
				if (!tempFile.getParentFile().exists()) {
					tempFile.getParentFile().mkdir();
				}

				if (!tempFile.exists()) {
					tempFile.mkdir();
				}
				fileUrlList.add(tempFile);

			}
			request.getPanoramaEngineDto().setFiles(fileUrlList);
		}
		return panoramaEngineFacade.addPhotos(request);

	}

	@ResponseBody
	@RequestMapping(value = "/startProcessing.do", method = RequestMethod.POST)
	public PanoramaEngineResponse startProcessing(@RequestBody PanoramaEngineRequest request,
			HttpServletRequest servletRequest) throws Exception {
		request.getPanoramaEngineDto().setApiKey(photoUrl.getKey());
		request.getPanoramaEngineDto().setApiBaseUrl(photoUrl.getUrl());
		return panoramaEngineFacade.startProcessing(request);
	}

	@ResponseBody
	@RequestMapping(value = "/queryScanStatus.do", method = RequestMethod.POST)
	public PanoramaEngineResponse queryScanStatus(@RequestBody PanoramaEngineRequest request,
			HttpServletRequest servletRequest) throws Exception {
		request.getPanoramaEngineDto().setApiKey(photoUrl.getKey());
		request.getPanoramaEngineDto().setApiBaseUrl(photoUrl.getUrl());
		return panoramaEngineFacade.queryScanStatus(request);
	}
}
