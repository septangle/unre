package com.unre.photo.comm.dal.dao;

import java.util.List;

import com.unre.photo.comm.dal.model.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    
    int updateBySelective(Order record);
    
    int updateOrderByBenacoId(Order record);
    
    List<Order> selectBySelective(Order record);
    
    //处理中的order
    List<Order> selectProcessedOrder();
    
    int updateStatus(Order record);
    
    //查询当前用户场景
    List<Order> SelStatus(Order record);
    
    //根据benonId查询订单
    Order SelOrder (Order record);
    
    // select unclosed orders 
    List<Order> selectUnclosedOrder();
    
    //更新订单金额
    int updateByAmount(Order record);
    
    //根据memberId查询订单
    Order selMemberId(Long id);

}