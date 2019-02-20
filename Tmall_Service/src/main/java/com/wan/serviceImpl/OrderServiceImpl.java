package com.wan.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wan.dao.OrderDao;
import com.wan.pojo.Order;
import com.wan.pojo.OrderItem;
import com.wan.pojo.Page4Navigator;
import com.wan.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * @Author 万星明
 * @Date 2019/2/20
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderDao orderDao;

    /**
     * 分页查询订单集合
     * @param start
     * @param size
     * @param navigatePages
     * @return
     */
    @Override
    public Page4Navigator<Order> list(int start, int size, int navigatePages) {
        //创建排序规则
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        //通过JPA自带的PageRequest分页请求,创建Pageable分页对象
        Pageable pageable = PageRequest.of(start, size, sort);
        //通过内置方法查询分页
        Page pageFromJPA =orderDao.findAll(pageable);
        //返回分页包装类
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }


    @Override
    public void removeOrderFromOrderItem(List<Order> orders) {
        //对订单集合进行遍历,解耦
        for (Order order : orders) {
            removeOrderFromOrderItem(order);
        }
    }

    @Override
    public void removeOrderFromOrderItem(Order order) {
        System.out.println("判断订单详情是否为空："+order.getOrderItems());
        //对单个订单,获取其中的订单详情集合
        List<OrderItem> orderItems= order.getOrderItems();

        if (orderItems!=null){
            //从订单详情处着手,解耦和与订单的关系
            for (OrderItem orderItem : orderItems) {
                //即:设置为空就好
                orderItem.setOrder(null);
            }
        }

    }


    @Override
    public Order get(int oid) {
        //通过订单的ID,获取订单信息
        Optional<Order> optional = orderDao.findById(oid);
        return optional.get();
    }


    @Override
    public void update(Order bean) {
        //保存修改订单实例
        orderDao.save(bean);
    }
}
