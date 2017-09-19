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
    
    /***********************************/
    //自定义查询
    List<Member> selectBySelective(Member record);
    
    //登录
    Member queryLoginUser(Member record);
    
    // get all member
    List<Member> queryAllMember();
    
    //验证邮箱和手机号是否唯一
    List<Member> selectByTelOrMail(Member record);
    
}