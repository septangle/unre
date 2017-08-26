package com.unre.photo.biz.logic.facade.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unre.photo.biz.dto.PhotoMemberDto;
import com.unre.photo.biz.logic.core.IPhotoMemberBiz;
import com.unre.photo.biz.logic.facade.IPhotoMemberFacade;
import com.unre.photo.biz.request.PhotoMemberRequest;
import com.unre.photo.biz.response.Error;
import com.unre.photo.biz.response.PhotoMemberResponse;
import com.unre.photo.comm.AppConstants;

/**
 * @author TDH
 *
 */
@Service
public class PhotoMemberFacadeImpl implements IPhotoMemberFacade {

	@Autowired
	private IPhotoMemberBiz photoMemberBiz;

	@Override
	public PhotoMemberResponse queryPhotoMember(PhotoMemberRequest request) throws Exception {
		List<PhotoMemberDto> photoMemberList = photoMemberBiz.queryPhotoMember(request.getPhotoMemberDto());
		PhotoMemberResponse response = new PhotoMemberResponse();
		response.setPhotoMemberDtoList(photoMemberList);
		return response;
	}

	@Override
	public PhotoMemberResponse findPhotoMemberById(PhotoMemberRequest request) throws Exception {
		PhotoMemberResponse response = new PhotoMemberResponse();
		PhotoMemberDto photoMemberParm = request.getPhotoMemberDto();
		if (photoMemberParm != null) {
			PhotoMemberDto photoMemberDto = photoMemberBiz.findPhotoMemberById(photoMemberParm.getId());
			response.setPhotoMemberDto(photoMemberDto);
		}
		return response;
	}

	@Override
	public void deletePhotoMember(Long id) throws Exception {

	}

	@Override
	public void updatePhotoMember(PhotoMemberRequest request) throws Exception {

	}

	// 登录
	@Override
	public PhotoMemberResponse login(PhotoMemberRequest request) throws Exception {
        PhotoMemberResponse response = new PhotoMemberResponse();
		response = new PhotoMemberResponse();
		PhotoMemberDto photoMemberDto = photoMemberBiz.queryLoginUsers(request.getPhotoMemberDto());
		response.setPhotoMemberDto(photoMemberDto);
		return response;

	}

	//注册
	@Override
	public PhotoMemberResponse register(PhotoMemberRequest request) throws Exception {
		PhotoMemberResponse response = new PhotoMemberResponse();
			response = new PhotoMemberResponse();
			PhotoMemberDto photoMemberDto = photoMemberBiz.addPhotoMember(request.getPhotoMemberDto());
			response.setPhotoMemberDto(photoMemberDto);
           return response;
	}

}
