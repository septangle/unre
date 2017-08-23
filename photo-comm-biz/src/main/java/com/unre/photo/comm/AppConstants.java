package com.unre.photo.comm;

public class AppConstants {

public final static String SYSTEM_ERROR_CODE = "10000001"; //系统异常
	
	public final static String SUCCESS_CODE = "1"; //成功标记
	public final static String FAIL_CODE = "-1"; //失败标记
	
	public final static String SFILE_INIT = "0"; //未处理
	public final static String SFILE_UPLOAD_COMPLETE = "1"; //上传完成
	public final static String SFILE_UPLOAD_FAIL = "2"; //上传失败
	public final static String SFILE_PROCESSING = "3"; //处理中
	public final static String SFILE_PROCESS_FAIL = "4"; //处理失败
	public final static String SCFILE_PROCESS_COMPLETE = "5"; //处理完成

	//会员 1001
	
	//login模块 1002
	public final static String QUERY_LOGIN_USER_ERROR_CODE = "10200001";
	public final static String QUERY_LOGIN_USER_ERROR_MESSAGE = "用户登录失败:账号或密码错误！";
	
	//register模块  1003
	public final static String QUERY_ADD_USER_ERROR_CODE = "10300001";
	public final static String QUERY_ADD_USER_ERROR_MESSAGE = "用户添加失败:输入信息不正确！";
	
	//photo 10040000
	//Panorama Engine 
	public final static String PENGINE_CREATE_SCAN_ERROR_CODE = "10040001";
	public final static String PENGINE_CREATE_SCAN_ERROR_MESSAGE = "创建SCAN失败！";
	
	public final static String PENGINE_ADD_PHOTOS_ERROR_CODE = "10040002";
	public final static String PENGINE_ADD_PHOTOS_ERROR_MESSAGE = "上传SCAN图片失败！";
	
	public final static String PENGINE_START_PROCESS_ERROR_CODE = "10040003";
	public final static String PENGINE_START_PROCESS_ERROR_MESSAGE = "SCAN开始处理失败！";
	
	public final static String PENGINE_QUERY_SCAN_STATUS_ERROR_CODE = "10040004";
	public final static String PENGINE_QUERY_SCAN_STATUS_ERROR_MESSAGE = "查询SCAN状态失败！";
	
	public final static String PENGINE_PREVIEW_SCAN_STATUS_ERROR_CODE = "10040005";
	public final static String PENGINE_PREVIEW_SCAN_STATUS_ERROR_MESSAGE = "生程SCAN PRVIEW URL失败！";
	
	
	//Photo Scan 10050000
	public final static String SCAN_FIND_ERROR_CODE = "10050001";
	public final static String SCAN_FIND_ERROR_MESSAGE = "根据ID查询Scan失败!";
	
	public final static String SCAN_QUERY_ERROR_CODE = "10050002";
	public final static String SCAN_QUERY_ERROR_MESSAGE = "查询Scan失败!";
	
	public final static String SCAN_ADD_ERROR_CODE = "10050003";
	public final static String SCAN_ADD_ERROR_MESSAGE = "新增Scan失败!";
	
	public final static String SCAN_UPDATE_ERROR_CODE = "10050004";
	public final static String SCAN_UPDATE_ERROR_MESSAGE = "更新Scan失败!";
	
	public final static String SCAN_SAVE_IMAGE_ERROR_CODE = "10050005";
	public final static String SCAN_SAVE_IMAGE_ERROR_MESSAGE = "保存图片信息失败!";
	
	//Photo Scan Item
	
	public final static String SCANITEM_UPDATE_ERROR_CODE = "10060004";
	public final static String SCANITEM_UPDATE_ERROR_MESSAGE = "更新ScanItem失败!";

	
}
