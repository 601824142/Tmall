package com.wan.dao;

import com.wan.pojo.Order;
import com.wan.pojo.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/2/20
 */
public interface OrderItemDao extends JpaRepository<OrderItem,Integer> {

    //通过订单查询订单详情集合
    List<OrderItem> findByOrderOrderByIdDesc(Order order);

}
