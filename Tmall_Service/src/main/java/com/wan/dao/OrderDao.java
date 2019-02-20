package com.wan.dao;

import com.wan.pojo.Order;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author 万星明
 * @Date 2019/2/20
 */
public interface OrderDao extends JpaRepository<Order,Integer> {
}
