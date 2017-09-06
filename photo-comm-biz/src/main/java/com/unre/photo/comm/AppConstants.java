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
	public final static String QUERY_LOGIN_USERID_ERROR_CODE = "10010001";
	public final static String QUERY_LOGIN_USERID_ERROR_MESSAGE = "该用户不存在！";
	public final static String QUERY_LOGIN_USERLOING_ERROR_CODE = "10010002";
	public final static String QUERY_LOGIN_USERLOING_ERROR_MESSAGE = "该用户已登录！";
	public final static String MEMBER_NOT_LOGIN_ERROR_CODE = "10010003";
	public final static String MEMBER_NOT_LOGIN_ERROR_MESSAGE = "该用户未登录！";

	//login模块 1002
	public final static String QUERY_LOGIN_USER_ERROR_CODE = "10020001";
	public final static String QUERY_LOGIN_USER_ERROR_MESSAGE = "用户登录失败:账号或密码错误！";
	
	//register模块  1003
	public final static String QUERY_ADD_USER_ERROR_CODE = "10030001";
	public final static String QUERY_ADD_USER_ERROR_MESSAGE = "用户添加失败:输入信息不正确！";
	public final static String QUERY_ADD_TEL_ERROR_CODE = "10030002";
	public final static String QUERY_ADD_TEL_ERROR_MESSAGE = "手机号已存在！";
	public final static String QUERY_ADD_MAIL_ERROR_CODE = "10030003";
	public final static String QUERY_ADD_MAIL_ERROR_MESSAGE = "邮箱已存在！";

	
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
	
	public final static String SCAN_BENACO_SCAN_ID_ERROR_CODE = "10050006";
	public final static String SCAN_BENACO_SCAN_ID_ERROR_MESSAGE = "benaco scan id 异常!";
	
	public final static String SCAN__DELETE_SCAN_ID_ERROR_CODE = "10050007";
	public final static String SCAN__DELETE_SCAN_ID_ERROR_MESSAGE = "scan id 异常!";
	
	
	//Photo Scan Item
	public final static String SCANITEM_UPDATE_ERROR_CODE = "10060004";
	public final static String SCANITEM_UPDATE_ERROR_MESSAGE = "更新ScanItem失败!";
	
	//Goods
	public final static Long GOODS_ID_BENACO = (long) 1;
	public final static String FINDGOODSBYID_ERROR_CODE = "1007001";
	public final static String FINDGOODSBYID_ERROR_MESSAGE = "该商品ID不存在";



	
}
