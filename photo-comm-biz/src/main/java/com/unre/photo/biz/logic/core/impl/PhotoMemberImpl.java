package com.unre.photo.biz.logic.core.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unre.photo.biz.dto.PhotoMemberDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.core.IPhotoMemberBiz;
import com.unre.photo.comm.dal.model.PhotoMember;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.unre.photo.comm.AppConstants;
import com.unre.photo.comm.dal.dao.PhotoMemberMapper;
import com.unre.photo.util.ModelUtil;
import org.apache.commons.collections.CollectionUtils;

@Service
public class PhotoMemberImpl implements IPhotoMemberBiz {

	@Autowired
	private PhotoMemberMapper photoMemberMapper;

	private static final Log LOGGER = LogFactory.getLog(PhotoMemberImpl.class);

	@Override
	public PhotoMemberDto findPhotoMemberById(Long photoMemberId) throws BusinessException {
		PhotoMemberDto photoMemberDto = null;

		try {
			PhotoMember photoMember = photoMemberMapper.selectByPrimaryKey(photoMemberId);
			if (photoMember == null) {
				throw new BusinessException(AppConstants.QUERY_LOGIN_USERID_ERROR_CODE,
						AppConstants.QUERY_LOGIN_USERID_ERROR_MESSAGE);
			}
			photoMemberDto = ModelUtil.modelToDto(photoMember, PhotoMemberDto.class);
		} catch (Exception e) {
			LOGGER.error(AppConstants.QUERY_LOGIN_USERID_ERROR_CODE, e);
			throw new BusinessException(AppConstants.QUERY_LOGIN_USERID_ERROR_CODE,
					AppConstants.QUERY_LOGIN_USERID_ERROR_MESSAGE);
		}
		return photoMemberDto;
	}

	@Override
	public List<PhotoMemberDto> queryPhotoMember(PhotoMemberDto photoMemberDto) throws BusinessException {
		List<PhotoMemberDto> photoMemberDtoList = new ArrayList<PhotoMemberDto>();
		try {
			PhotoMember photoMember = ModelUtil.dtoToModel(photoMemberDto, PhotoMember.class);
			List<PhotoMember> photoMemberList = photoMemberMapper.selectBySelective(photoMember);
			if (!CollectionUtils.isEmpty(photoMemberList)) {
				for (PhotoMember p : photoMemberList) {
					photoMemberDtoList.add(ModelUtil.modelToDto(p, PhotoMemberDto.class));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return photoMemberDtoList;
	}

	// 注册
	@Override
	public PhotoMemberDto addPhotoMember(PhotoMemberDto photoMemberDto) throws BusinessException {
		PhotoMember photomember = new PhotoMember();
		photomember.setTel(photoMemberDto.getTel());
		photomember.setMail(photoMemberDto.getMail());
		List<PhotoMember> photoMemberslist = photoMemberMapper.selectByTelOrMail(photomember);
		if (photoMemberslist.size() > 0) {
			for (int i = 0; i < photoMemberslist.size(); i++) {
				PhotoMember photoMember = photoMemberslist.get(i);
				if (photoMember.getTel().equals(photoMemberDto.getTel())) {
					throw new BusinessException(AppConstants.QUERY_ADD_TEL_ERROR_CODE,
							AppConstants.QUERY_ADD_TEL_ERROR_MESSAGE);
				} else if (photoMember.getMail().equals(photoMemberDto.getMail())) {
					throw new BusinessException(AppConstants.QUERY_ADD_MAIL_ERROR_CODE,
							AppConstants.QUERY_ADD_MAIL_ERROR_MESSAGE);
				}
			}
		}
		try {
			PhotoMember photomembers = ModelUtil.dtoToModel(photoMemberDto, PhotoMember.class);
			photoMemberMapper.insertSelective(photomembers);
			Long id = photomembers.getId();
			photoMemberDto = findPhotoMemberById(id);
			if (photoMemberDto == null) {
				throw new BusinessException(AppConstants.QUERY_ADD_USER_ERROR_CODE,
						AppConstants.QUERY_ADD_USER_ERROR_MESSAGE);
			}
		} catch (Exception e) {
			LOGGER.error(AppConstants.QUERY_ADD_USER_ERROR_CODE, e);
			throw new BusinessException(AppConstants.QUERY_ADD_USER_ERROR_CODE,
					AppConstants.QUERY_ADD_USER_ERROR_MESSAGE);
		}
		return photoMemberDto;

	}

	// 登录
	@Override
	public PhotoMemberDto queryLoginUsers(PhotoMemberDto photoMemberDto) throws BusinessException {
		PhotoMember photomember;
		try {
			PhotoMember photoMembers = ModelUtil.dtoToModel(photoMemberDto, PhotoMember.class);
			photomember = photoMemberMapper.queryLoginUsers(photoMembers);
			if (photomember == null) {
				throw new BusinessException(AppConstants.QUERY_LOGIN_USER_ERROR_CODE,
						AppConstants.QUERY_LOGIN_USER_ERROR_MESSAGE);
			}
			photoMemberDto = ModelUtil.modelToDto(photomember, PhotoMemberDto.class);
		} catch (Exception e) {
			LOGGER.error(AppConstants.QUERY_LOGIN_USER_ERROR_CODE, e);
			throw new BusinessException(AppConstants.QUERY_LOGIN_USER_ERROR_CODE,
					AppConstants.QUERY_LOGIN_USER_ERROR_MESSAGE);
		}
		return photoMemberDto;
	}

	@Override
	public void updatePhotoMember(PhotoMemberDto photoMemberDto) throws BusinessException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePhotoMember(Long id) throws BusinessException {
		// TODO Auto-generated method stub

	}

}
