package com.unre.photo.comm.dal.dao;

import java.util.List;

import com.unre.photo.comm.dal.model.Member;

public interface MemberMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Member record);

	int insertSelective(Member record);

	Member selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Member record);

	int updateByPrimaryKey(Member record);

	// --------------------------------
	List<Member> selectBySelective(Member record);

	// 登录
	Member queryLoginUsers(Member member);
	
	List<Member> selectByTelOrMail(Member record);


}