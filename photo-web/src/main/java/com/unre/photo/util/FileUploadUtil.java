package com.unre.photo.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import com.unre.photo.comm.AppConstants;

public class FileUploadUtil {
	public static List<File> Upload(MultipartFile[] files,String number,Long merberId) throws IOException{
		List<File> fileUrlList = new ArrayList<File>(); //用来保存文件路径，
		String strFilePath = (AppConstants.NUMBER_MESSAGE_3D).equals(number) ? AppConstants.PARAMOPATH
				: AppConstants.PHOTOPATH;
		String strNowTime = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
		strFilePath = strFilePath + merberId.toString() + File.separator + strNowTime;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isEmpty()) {
				System.out.println("上传文件[" + i + "]为空");
			} else {
				FileUtils.copyInputStreamToFile(files[i].getInputStream(),
						new File(strFilePath, files[i].getOriginalFilename()));
				File f = new File(strFilePath + File.separator + files[i].getOriginalFilename());
				fileUrlList.add(f);
			}
		}
		return fileUrlList;
		
		
	}
}
