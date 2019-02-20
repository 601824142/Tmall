package com.wan.service;

import com.wan.pojo.Order;
import com.wan.pojo.Page4Navigator;

import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/2/20
 */
public interface IOrderService {

    //订单分页查询
    Page4Navigator<Order> list(int start, int size, int navigatePages);

    //将每个订单中的订单详情与外界订单的联系给断掉,否则会无限循环,导致报错
    void removeOrderFromOrderItem(List<Order> orders);

    //将单个订单中的订单详情与外界订单的联系给断掉
    void removeOrderFromOrderItem(Order order);

    //通过ID查询订单
    Order get(int oid);

    //修改订单
    void update(Order bean);

}
