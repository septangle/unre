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
    
    
   /****************自定义查询*******************/
    int updateBySelective(Order record);
    
    int updateOrderByBenacoId(Order record);
    
    List<Order> selectBySelective(Order record);
    
    //处理中的order
    List<Order> selectProcessedOrder();
    
    int updateStatus(Order record);
    
    //查询当前用户所有场景
    List<Order> selectGetMemberScene(Order record);
    
    //根据benonId查询订单
    Order selectOrder (Order record);
    
    // select unclosed orders 
    List<Order> selectUnclosedOrder();
    
    //查询消费订单
    List<Order> selectConsumeOrder(Order record);
    
    
    //查询拼接照片完成的订单
    List<Order> selectStitchedProcessOrder(Order record);


}