package com.unre.photo.biz.logic.core.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unre.photo.biz.dto.MemberDto;
import com.unre.photo.biz.dto.MemberInformationDto;
import com.unre.photo.biz.dto.PriceDto;
import com.unre.photo.biz.exception.BusinessException;
import com.unre.photo.biz.logic.core.IMemberBiz;
import com.unre.photo.comm.dal.model.Balance;
import com.unre.photo.comm.dal.model.Goods;
import com.unre.photo.comm.dal.model.Member;
import com.unre.photo.comm.dal.model.MemberInformation;
import com.unre.photo.comm.dal.model.MemberLevelItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.unre.photo.comm.AppConstants;
import com.unre.photo.comm.dal.dao.BalanceMapper;
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
	private BalanceMapper balanceMapper;

	@Autowired
	private MemberLevelItemMapper memberItemMapper;

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
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error(AppConstants.FIND_MEMBER_BY_ID_CODE, e);
			throw new BusinessException(AppConstants.FIND_MEMBER_BY_ID_CODE, AppConstants.FIND_MEMBER_BY_ID_MESSAGE);
		}
		return MemberDto;
	}

	@Override
	public List<MemberDto> queryMember(MemberDto memberDto) throws BusinessException {
		List<MemberDto> MemberDtoList = new ArrayList<MemberDto>();
		try {
			Member member = ModelUtil.dtoToModel(memberDto, Member.class);
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
		try {
			Member memberParm = new Member();
			memberParm.setTel(memberDto.getTel());
			memberParm.setMail(memberDto.getMail());
			List<Member> memberList = memberMapper.selectByTelOrMail(memberParm);//手机号、邮箱唯一校验
			if (memberList.size() > 0) {
				if (memberList.get(0).getTel().equals(memberDto.getTel())) {
					throw new BusinessException(AppConstants.QUERY_ADD_TEL_ERROR_CODE,
							AppConstants.QUERY_ADD_TEL_ERROR_MESSAGE);
				} else if (memberList.get(0).getMail().equals(memberDto.getMail())) {
					throw new BusinessException(AppConstants.QUERY_ADD_MAIL_ERROR_CODE,
							AppConstants.QUERY_ADD_MAIL_ERROR_MESSAGE);
				}
			}
			//系统自动设置会员级别
			memberDto.setLevel(AppConstants.MEMBER_LEVEL_DEFAULT);
			Member member = ModelUtil.dtoToModel(memberDto, Member.class);
			memberMapper.insertSelective(member);
			Long id = member.getId();
			memberDto = findMemberById(id);
			if (memberDto == null) {
				throw new BusinessException(AppConstants.QUERY_ADD_USER_ERROR_CODE,
						AppConstants.QUERY_ADD_USER_ERROR_MESSAGE);
			}
			//插入一条记录到balance表
			Balance balance = new Balance();
			balance.setMemberId(memberDto.getId());
			balance.setVersion(AppConstants.VERSION);
			balanceMapper.insertSelective(balance);
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error(AppConstants.QUERY_ADD_USER_ERROR_MESSAGE);
			throw new BusinessException(AppConstants.SYSTEM_ERROR_CODE, AppConstants.SYSTEM_ERROR_MESSAGE);
		}
		return memberDto;
	}

	// 登录
	@Override
	public MemberDto queryLoginUser(MemberDto memberDto) throws BusinessException {
		Member memberParam;
		try {
			memberParam = ModelUtil.dtoToModel(memberDto, Member.class);
			List<Member> memberList = memberMapper.selectBySelective(memberParam);
			if (memberList.size() == 0 && memberList.size() > 1) {//判断该用户信息是否存在
				throw new BusinessException(AppConstants.QUERY_LOGIN_USER_ERROR_CODE,
						AppConstants.QUERY_LOGIN_USER_ERROR_MESSAGE);
			}
			Member member = memberList.get(0);
			memberDto = ModelUtil.modelToDto(member, MemberDto.class);
		} catch (BusinessException e) {
			LOGGER.error(e.getMessage());
			throw e;
		} catch (Exception e) {
			LOGGER.error(AppConstants.QUERY_LOGIN_USER_ERROR_CODE, e);
			throw new BusinessException(AppConstants.QUERY_LOGIN_USER_ERROR_CODE,
					AppConstants.QUERY_LOGIN_USER_ERROR_MESSAGE);
		}
		return memberDto;
	}

	@Override
	public boolean updateMember(MemberDto MemberDto) throws BusinessException {
		boolean flag = false;
		try {
			Member member = ModelUtil.dtoToModel(MemberDto, Member.class);
			int i = memberMapper.updateByPrimaryKeySelective(member);
			if (i != 0) {
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public void deleteMember(Long id) throws BusinessException {

	}

	//查询当前会员单价
	@Override
	public PriceDto calculateMerberPrice(MemberDto memberDto) throws BusinessException {
		//根据Member表中level，查询MemberLevelItem表中折扣率
		MemberLevelItem memberLevelItem = memberItemMapper.selectByValue(memberDto.getLevel());
		//查询Goods表单价
		Goods good = goodMapper.selectByPrimaryKey(AppConstants.GOODS_ID_BENACO);
		PriceDto priceDto = new PriceDto();
		//单价=单价*折扣率
		priceDto.setPrice(memberLevelItem.getRebate().multiply(good.getPrice()).doubleValue());
		//放入PriceDto中
		return priceDto;
	}

	// query all member
	@Override
	public List<MemberDto> queryAllMember() throws BusinessException {
		Member member = null;
		List<MemberDto> memberDtoList = new ArrayList<MemberDto>();
		List<Member> memberList = memberMapper.selectBySelective(member);
		if (memberList != null) {
			for (Member members : memberList) {
				memberDtoList.add(ModelUtil.modelToDto(members, MemberDto.class));
			}
		}
		return memberDtoList;
	}

	//修改密码
	@Override
	public boolean updatePassword(MemberDto memberDto) throws BusinessException {
		boolean flag = false;
		try {
			Member member = ModelUtil.dtoToModel(memberDto, Member.class);
			int i = memberMapper.updatePassword(member);
			if (i == 0) {
				throw new BusinessException(AppConstants.UPDATE_PASSWORD_CODE, AppConstants.UPDATE_PASSWORD_MESSAGE);
			}
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(AppConstants.UPDATE_PASSWORD_CODE, AppConstants.UPDATE_PASSWORD_MESSAGE);
		}
		return flag;

	}

	@Override
	public MemberInformationDto getMemberInfomation(MemberDto memberDto) throws BusinessException {
		MemberInformationDto memberInfomationDto = null;
		try {
			Member member = ModelUtil.dtoToModel(memberDto, Member.class);
			MemberInformation memberInfomation = memberMapper.selectMemberInfo(member);
			memberInfomationDto = ModelUtil.modelToDto(memberInfomation, MemberInformationDto.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(AppConstants.QUERY_LOGIN_USERID_ERROR_CODE,
					AppConstants.QUERY_LOGIN_USERID_ERROR_MESSAGE);
		}
		return memberInfomationDto;
	}

}
