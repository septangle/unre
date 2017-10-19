package com.unre.photo.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import com.unre.photo.biz.dto.ImageInfoDto;
import com.unre.photo.comm.AppConstants;

public class FileUploadUtil {
	public static List<ImageInfoDto> upload(MultipartFile[] files,String number,Long merberId) throws IOException{
		List<ImageInfoDto> imageInfoList = new ArrayList<ImageInfoDto>(); //用来保存文件路径，
		String strFilePath = (AppConstants.NUMBER_MESSAGE_3D).equals(number) ? AppConstants.PARAMOPATH
				: AppConstants.PHOTOPATH;
		String strNowTime = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
		strFilePath = strFilePath + merberId.toString() + File.separator + strNowTime;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isEmpty()) {
				System.out.println("上传文件[" + i + "]为空");
			} else {
				//1.拷贝图片至服务器
				FileUtils.copyInputStreamToFile(files[i].getInputStream(),
						new File(strFilePath, files[i].getOriginalFilename()));
				
				//File f = new File(strFilePath + File.separator + files[i].getOriginalFilename());
				
				ImageInfoDto imageInfo = new ImageInfoDto();
				imageInfo.setFullPath(strFilePath + File.separator + files[i].getOriginalFilename());
				//2.形成图片信息集合
				imageInfoList.add(imageInfo);
			}
		}
		return imageInfoList;
		
		
	}
	
	
	public static List<File> UploadThumbFile(List<ImageInfoDto> fileList,Long memberId){  
		List<File> thumbUrlList = new ArrayList<File>(); //用来保存缩略图路径，
		String strNowTime = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
		String pic_path=AppConstants.THUMB_PATH + strNowTime;
		File files  = new File(pic_path);
		if  (!files .exists()  && !files .isDirectory())      
		{       
		    files .mkdir();    
		}
        for (int i = 0; i < fileList.size(); i++) {
        	String filePath =fileList.get(i).getFullPath();
			File file = new ImageUtil().thumbnailImage(filePath, AppConstants.WIDTH, AppConstants.HEIGHT,AppConstants.DEFAULT_PREVFIX, AppConstants.DEFAULT_FORCE,files.toString());
			thumbUrlList.add(new File(strNowTime+File.separator+file.getName())); 
        }
		return thumbUrlList;
		
		
		
	}
}
