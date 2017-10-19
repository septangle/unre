package com.unre.photo.comm.dal.dao;

import java.util.List;

import com.unre.photo.comm.dal.model.Member;
import com.unre.photo.comm.dal.model.MemberInformation;

public interface MemberMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);
    
    /****************自定义查询*******************/

    List<Member> selectBySelective(Member record);
    
    //验证邮箱和手机号是否唯一
    List<Member> selectByTelOrMail(Member record);
    
    MemberInformation selectMemberInfo(Member record);
    
    //修改密码
    int updatePassword(Member record);;
    
}