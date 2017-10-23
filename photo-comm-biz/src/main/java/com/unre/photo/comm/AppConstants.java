package com.unre.photo.comm;

public class AppConstants {

	public final static String SYSTEM_ERROR_CODE = "10000001"; //系统异常
	public final static String SYSTEM_ERROR_MESSAGE = "系统异常";

	public final static String SUCCESS_CODE = "1"; //成功标记
	public final static String FAIL_CODE = "-1"; //失败标记

	public final static String SFILE_INIT = "0"; //未处理
	public final static String SFILE_UPLOAD_COMPLETE = "1"; //上传完成
	public final static String SFILE_UPLOAD_FAIL = "2"; //上传失败
	public final static String SFILE_PROCESSING = "3"; //处理中
	public final static String SFILE_PROCESS_FAIL = "4"; //处理失败
	public final static String SFILE_PROCESS_COMPLETE = "5"; //处理完成

	//已删除
	public final static String SET_DELETE = "1";
	public final static String SUCCESS_MESSAGE = "删除成功";
	public final static String FAIL_MESSAGE = "删除失败";
	public final static String BENACO_PRIVATE_KEY = "3c7c6941-2204-4ee7-a4b5-0981e0e6e09c";
	public final static String BENACO_HOST = "https://beta.benaco.com";
	public final static String BENACO_BASEPATH = "/api/beta";
	//public final static String BENACO_BASEPATH = "/api";
	public final static String BENACO_NEW = "/scans/new";
	public final static String BENACO_SCAN = "/scans/id";
	public final static String BENACO_STATUS = "status";
	public final static String BENACO_PROCESSPOINTS = "points";

	public final static String BENACO_STATUS_FAILED = "failed";
	public final static String BENACO_STATUS_COMPLETED = "completed";

	//public final static String PHOTOPATH="C:/img_upload_2D/";
	//public final static String PARAMOPATH="C:/img_upload_3D/";
	//public final static String THUMB_PATH="E:/Tomcat-8.0.26/webapps/thumb_image/";
	 public final static String THUMB_PATH = "/home/unre/project/photo-scan/Tomcat-8.0.26/webapps/thumb_image/";
	 public final static String PHOTOPATH = "/home/unre/project/photo-scan/2D_img_upload/";
	 public final static String PARAMOPATH = "/home/unre/project/photo-scan/3D_img_upload/";

	public final static String LOGIN_NAME = "admin";

	public final static String HTTP_RETURN_CODE_200 = "200";

	// 平面照片
	public final static String ORDER_TYPE_PHOTO = "0";
	// 全景照片
	public final static String ORDER_TYPE_PANORAMA = "1";
	// 线下消费
	public final static String ORDER_TYPE_OFFLINE = "2";

	// 流水类型：充值
	public final static String BALANCE_TRACE_TYPE_RECHARGE = "1";
	// 流水类型：退钱
	public final static String BALANCE_TRACE_TYPE_REFUNDS = "2";
	// 流水类型：线上消费
	public final static String BALANCE_TRACE_TYPE_ONLINE = "3";
	// 流水类型：线下消费
	public final static String BALANCE_TRACE_TYPE_OFFLINE = "4";

	public final static String ORDER_STATUS_INIT = "0";
	public final static String ORDER_STATUS_PROCESSING = "1";
	public final static String ORDER_STATUS_COMPLETED = "2";
	public final static String ORDER_STATUS_FAILED = "3";
	public final static String PANORAMA_UNSTITCH = "0";
	public final static String PANORAMA_STITCHING = "1";
	public final static String PANORAMA_STITCHED = "2";
	public final static String PANORAMA_STITCH_FAIL = "3";

	// 系统自动设定会员级别（默认）
	public final static String MEMBER_LEVEL_SET_TYPE_AUTOMATIC = "0";
	// 手动设定会员级别
	public final static String MEMBER_LEVEL_SET_TYPE_MANUAL = "1";

	//会员 1001
	public final static String QUERY_LOGIN_USERID_ERROR_CODE = "10010001";
	public final static String QUERY_LOGIN_USERID_ERROR_MESSAGE = "该用户不存在！";
	public final static String QUERY_LOGIN_USERLOING_ERROR_CODE = "10010002";
	public final static String QUERY_LOGIN_USERLOING_ERROR_MESSAGE = "该用户已登录！";
	public final static String MEMBER_NOT_LOGIN_ERROR_CODE = "10010003";
	public final static String MEMBER_NOT_LOGIN_ERROR_MESSAGE = "该用户未登录！";
	public final static String FIND_MEMBER_BY_ID_CODE = "10010004";
	public final static String FIND_MEMBER_BY_ID_MESSAGE = "根据id查找失败";
	public final static String UPDATE_PASSWORD_CODE = "10010005";
	public final static String UPDATE_PASSWORD_MESSAGE = "修改密码失败";

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
	public final static String QUERY_ADD_AUTOMATICITY_TYPE = "自动";
	public final static String QUERY_ADD_MANUALOPERATION_TYPE = "手动";
	public final static String QUERY_ADD_SETTYPE = "0";
	public final static String QUERY_ADD_SETTYPE_RATION = "1";

	//photo 10040000
	//Panorama Engine 
	public final static String PENGINE_CREATE_SCAN_ERROR_CODE = "10040001";
	public final static String PENGINE_CREATE_SCAN_ERROR_MESSAGE = "创建SCAN失败！";

	public final static String PENGINE_ADD_PHOTOS_ERROR_CODE = "10040002";
	public final static String PENGINE_ADD_PHOTOS_ERROR_MESSAGE = "上传SCAN图片失败！";

	public final static String PENGINE_START_PROCESS_ERROR_CODE = "10040003";
	public final static String PENGINE_START_PROCESS_ERROR_MESSAGE = "SCAN开始处理失败！";

	public final static String PENGINE_START_PHOTO_PROCESS_ERROR_CODE = "10040004";
	public final static String PENGINE_START_PHOTO_PROCESS_ERROR_MESSAGE = "2D照片处理失败！";

	public final static String PENGINE_START_PANORAMA_PROCESS_ERROR_CODE = "10040005";
	public final static String PENGINE_START_PANORAMA_PROCESS_ERROR_MESSAGE = "全景照片处理失败！";

	public final static String PENGINE_QUERY_SCAN_STATUS_ERROR_CODE = "10040006";
	public final static String PENGINE_QUERY_SCAN_STATUS_ERROR_MESSAGE = "查询SCAN状态失败！";

	public final static String PENGINE_PREVIEW_SCAN_STATUS_ERROR_CODE = "10040007";
	public final static String PENGINE_PREVIEW_SCAN_STATUS_ERROR_MESSAGE = "生程SCAN PRVIEW URL失败！";

	public final static String ADD_PHOTO_STITCH_COMPLETED_CODE = "10040008";
	public final static String ADD_PHOTO_STITCH_COMPLETED_MESSAGE = "拼接照片上传失败:照片数量不匹配";

	public final static int HEIGHT = 400;
	public final static int WIDTH = 400;

	public static String DEFAULT_PREVFIX = "thumb_";
	public static Boolean DEFAULT_FORCE = true;

	//Photo Scan 10050000
	public final static String SCAN_FIND_ERROR_CODE = "10050001";
	public final static String SCAN_FIND_ERROR_MESSAGE = "根据ID查询Scan失败!";

	public final static String SCAN_QUERY_ERROR_CODE = "10050002";
	public final static String SCAN_QUERY_ERROR_MESSAGE = "查询Order失败!";

	public final static String SCAN_ADD_ERROR_CODE = "10050003";
	public final static String SCAN_ADD_ERROR_MESSAGE = "新增Scan失败!";

	public final static String UPDATE_ORDER_ERROR_CODE = "10050004";
	public final static String UPDATE_ORDER_ERROR_MESSAGE = "更新Order失败!";

	public final static String SCAN_SAVE_IMAGE_ERROR_CODE = "10050005";
	public final static String SCAN_SAVE_IMAGE_ERROR_MESSAGE = "保存图片信息失败!";

	public final static String SCAN_BENACO_SCAN_ID_ERROR_CODE = "10050006";
	public final static String SCAN_BENACO_SCAN_ID_ERROR_MESSAGE = "benaco scan id 异常!";

	public final static String SCAN__DELETE_SCAN_ID_ERROR_CODE = "10050007";
	public final static String SCAN__DELETE_SCAN_ID_ERROR_MESSAGE = "scan id 异常!";

	//Photo Scan Item
	public final static String SCANITEM_UPDATE_ERROR_CODE = "10060004";
	public final static String SCANITEM_UPDATE_ERROR_MESSAGE = "更新is_Deleted失败!";

	//Goods
	public final static Long GOODS_ID_BENACO = (long) 1;
	public final static String FINDGOODSBYID_ERROR_CODE = "1007001";
	public final static String FINDGOODSBYID_ERROR_MESSAGE = "该商品ID不存在";

	//Panorama  panorama_type
	public final static String PANORAMA_STITCH_INIT = "0";
	public final static String PANORAMA_STITCH_STATUS_PROCESSING = "1";
	public final static String PANORAMA_STITCH_STATUS_COMPLETED = "2";
	public final static String PANORAMA_STITCH_STATUS_FAILED = "3";

	public final static String ADD_PHOTOS_ERROR_CODE = "1007005";
	public final static String ADD_PHOTOS_ERROR_MESSAGE = "点数与传入照片数量不匹配";

	public final static String NUMBER_MESSAGE_3D = "1";

	public final static String UPDATE_PANORAMA_BY_ORDERID_CODE = "1007005";
	public final static String UPDATE_PANORAMA_BY_ORDERID_MESSAGE = "更新拼接照片失败";

	//自动设置会员级别
	public final static String MEMBER_LEVEL_DEFAULT = "3";

	//order
	public final static String REMOVE_ORDER_CODE = "10080001";
	public final static String REMOVE_ORDER_MESSAGE = "删除订单失败!";

	//Balance
	public final static Long VERSION = (long) 0;

	//sendCode
	public final static String SEND_CODE_MAIL_SUCCESS = "邮件发送成功";
	public final static String SEND_CODE_MAIL_FAIL = "邮件发送失败";
	public final static String SEND_CODE_TEL_SUCCESS = "短信发送成功";
	public final static String SEND_CODE_TEL_FAIL = "短信发送失败";

	public final static String VERIFY_CODE_MAIL_SUCCESS = "邮件验证成功";
	public final static String VERIFY_CODE_MAIL_FAIL = "邮件验证失败";
	public final static String VERIFY_CODE_TEL_SUCCESS = "短信验证成功";
	public final static String VERIFY_CODE_TEL_FAIL = "短信验证失败";

	public final static String SUBJECT = "盎锐全景平台-修改密码保护验证";

}
