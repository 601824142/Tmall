package com.wan.serviceImpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.wan.dao.OrderItemDao;
import com.wan.pojo.Order;
import com.wan.pojo.OrderItem;
import com.wan.service.IOrderItemService;
import com.wan.service.IProductImageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/2/20
 */
@Service
public class OrderItemServiceImpl implements IOrderItemService {

    @Autowired
    private OrderItemDao orderItemDao;

    @Reference
    private IProductImageService productImageService;

    /**
     * 对订单集合做批量的设置订单详情等工作
     * @param orders
     */
    @Override
    public List<Order> fill(List<Order> orders) {
        //循环调用对单个订单进行处理
        for (Order order : orders) {
               fill(order);
        }

        return orders;
    }


    /**
     * 针对单个订单进行处理
     * @param order
     */
    @Override
    public Order fill(Order order) {
        //1、通过订单查询订单的详情(方法在下面)
        List<OrderItem> orderItems = listByOrder(order);

        //定义总价格
        float total = 0;
        //定义总数
        int totalNumber = 0;
        //对订单详情进行循环遍历
        for (OrderItem orderItem : orderItems) {
            //计算总价(订单详情中的数量*订单详情中产品的单价)
            total += orderItem.getNumber()*orderItem.getProduct().getPromotePrice();
            //计算总数
            totalNumber += orderItem.getNumber();
            //设置主要图片
            productImageService.setFirstProductImage(orderItem.getProduct());
        }

        //设置总价给订单
        order.setTotal(total);
        //设置订单详情集合
        order.setOrderItems(orderItems);
        //设置总数
        order.setTotalNumber(totalNumber);

        return order;
    }

    /**
     * 根据订单查询订单详情,并排序
     * @param order
     * @return
     */
    @Override
    public List<OrderItem> listByOrder(Order order) {
        return orderItemDao.findByOrderOrderByIdDesc(order);
    }
}
