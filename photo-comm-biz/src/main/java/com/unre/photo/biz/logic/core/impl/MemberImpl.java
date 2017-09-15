package com.unre.photo.biz.logic.core.impl;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unre.photo.biz.dto.MemberDto;
import com.unre.photo.biz.dto.PriceDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.core.IMemberBiz;
import com.unre.photo.comm.dal.model.Goods;
import com.unre.photo.comm.dal.model.Member;
import com.unre.photo.comm.dal.model.MemberLevelItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.unre.photo.comm.AppConstants;
import com.unre.photo.comm.dal.dao.GoodsMapper;
import com.unre.photo.comm.dal.dao.MemberLevelItemMapper;
import com.unre.photo.comm.dal.dao.MemberMapper;
import com.unre.photo.util.ModelUtil;
import org.apache.commons.collections.CollectionUtils;

@Service
public class MemberImpl implements IMemberBiz {

	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private GoodsMapper goodMapper;
	
	@Autowired
	private MemberLevelItemMapper   memberItemMapper;

	private static final Log LOGGER = LogFactory.getLog(MemberImpl.class);

	@Override
	public MemberDto findMemberById(Long memberId) throws BusinessException {
		MemberDto MemberDto = null;

		try {
			Member member = memberMapper.selectByPrimaryKey(memberId);
			if (member == null) {
				throw new BusinessException(AppConstants.QUERY_LOGIN_USERID_ERROR_CODE,
						AppConstants.QUERY_LOGIN_USERID_ERROR_MESSAGE);
			}
			MemberDto = ModelUtil.modelToDto(member, MemberDto.class);
		} catch (Exception e) {
			LOGGER.error(AppConstants.QUERY_LOGIN_USERID_ERROR_CODE, e);
			throw new BusinessException(AppConstants.QUERY_LOGIN_USERID_ERROR_CODE,
					AppConstants.QUERY_LOGIN_USERID_ERROR_MESSAGE);
		}
		return MemberDto;
	}

	@Override
	public List<MemberDto> queryMember(MemberDto MemberDto) throws BusinessException {
		List<MemberDto> MemberDtoList = new ArrayList<MemberDto>();
		try {
			Member member = ModelUtil.dtoToModel(MemberDto, Member.class);
			List<Member> memberList = memberMapper.selectBySelective(member);
			if (!CollectionUtils.isEmpty(memberList)) {
				for (Member p : memberList) {
					MemberDtoList.add(ModelUtil.modelToDto(p, MemberDto.class));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MemberDtoList;
	}

	// 注册
	@Override
	public MemberDto addMember(MemberDto memberDto) throws BusinessException {
		Member member = new Member();
		member.setTel(memberDto.getTel());
		member.setMail(memberDto.getMail());
		List<Member> memberslist = memberMapper.selectByTelOrMail(member);
		if (memberslist.size() > 0) {
			for (int i = 0; i < memberslist.size(); i++) {
				Member members = memberslist.get(i);
				if (members.getTel().equals(memberDto.getTel())) {
					throw new BusinessException(AppConstants.QUERY_ADD_TEL_ERROR_CODE,
							AppConstants.QUERY_ADD_TEL_ERROR_MESSAGE);
				} else if (members.getMail().equals(memberDto.getMail())) {
					throw new BusinessException(AppConstants.QUERY_ADD_MAIL_ERROR_CODE,
							AppConstants.QUERY_ADD_MAIL_ERROR_MESSAGE);
				}
			}
		}
		try {
			//系统自动设置会员级别
			memberDto.setLevel(AppConstants.MEMBER_LEVEL_DEFAULT);
			Member members = ModelUtil.dtoToModel(memberDto, Member.class);
			memberMapper.insertSelective(members);
			Long id = members.getId();
			memberDto = findMemberById(id);
			if (memberDto == null) {
				throw new BusinessException(AppConstants.QUERY_ADD_USER_ERROR_CODE,
						AppConstants.QUERY_ADD_USER_ERROR_MESSAGE);
			}
		} catch (Exception e) {
			LOGGER.error(AppConstants.QUERY_ADD_USER_ERROR_CODE, e);
			throw new BusinessException(AppConstants.QUERY_ADD_USER_ERROR_CODE,
					AppConstants.QUERY_ADD_USER_ERROR_MESSAGE);
		}
		return memberDto;
	}

	// 登录
	@Override
	public MemberDto queryLoginUsers(MemberDto MemberDto) throws BusinessException {
		Member member;
		try {
			Member members = ModelUtil.dtoToModel(MemberDto, Member.class);
			member = memberMapper.queryLoginUsers(members);
			if (member == null) {
				throw new BusinessException(AppConstants.QUERY_LOGIN_USER_ERROR_CODE,
						AppConstants.QUERY_LOGIN_USER_ERROR_MESSAGE);
			}
			Member ms = new Member();
			ms.setId(member.getId());
			ms.setStatus(AppConstants.QUERY_LOGIN_USER_STATUS_MESSAGE);
			memberMapper.updateByPrimaryKeySelective(ms);
			MemberDto = ModelUtil.modelToDto(member, MemberDto.class);
		} catch (Exception e) {
			LOGGER.error(AppConstants.QUERY_LOGIN_USER_ERROR_CODE, e);
			throw new BusinessException(AppConstants.QUERY_LOGIN_USER_ERROR_CODE,
					AppConstants.QUERY_LOGIN_USER_ERROR_MESSAGE);
		}
		return MemberDto;
	}

	@Override
	public void updateMember(MemberDto MemberDto) throws BusinessException {
		Member members = ModelUtil.dtoToModel(MemberDto, Member.class);
		memberMapper.updateByPrimaryKeySelective(members);
	}

	@Override
	public void deleteMember(Long id) throws BusinessException {
		// TODO Auto-generated method stub

	}

	//查询当前会员单价
	@Override
	public PriceDto SelPriceById(MemberDto memberDto) throws BusinessException {
		//根据Member表中level，查询MemberLevelItem表中折扣率
		MemberLevelItem item=memberItemMapper.selectByValue(memberDto.getLevel());
		//查询Goods表单价
    	Goods goods=goodMapper.selectByPrimaryKey(AppConstants.GOODS_ID_BENACO);
    	PriceDto p = new PriceDto();
    	//单价=单价*折扣率
    	p.setPrice(item.getRebate().multiply(goods.getPrice()).doubleValue());
    	//放入PriceDto中
    	return p ;
	}
	
	// query all member
	@Override
	public List<MemberDto> queryAllMember() throws BusinessException {
		
		List<MemberDto> memberDtoList = new ArrayList<MemberDto>();
		List<Member> memberList = memberMapper.queryAllMember();
		
		if (memberList != null) {
			for (Member member : memberList) {
				memberDtoList.add(ModelUtil.modelToDto(member, MemberDto.class));
			}
		}

		return memberDtoList;
	}

}
