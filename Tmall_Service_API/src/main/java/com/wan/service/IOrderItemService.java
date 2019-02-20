package com.wan.service;

import com.wan.pojo.Order;
import com.wan.pojo.OrderItem;

import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/2/20
 */
public interface IOrderItemService {

    //对订单集合做批量的设置订单详情等工作
    List<Order> fill(List<Order> orders);

    //针对单个订单进行处理
    Order fill(Order order);

    //通过订单查询订单详情集合
    List<OrderItem> listByOrder(Order order);

}
