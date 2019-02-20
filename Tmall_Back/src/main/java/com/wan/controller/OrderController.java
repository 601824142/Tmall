package com.wan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wan.pojo.Order;
import com.wan.pojo.Page4Navigator;
import com.wan.service.IOrderItemService;
import com.wan.service.IOrderService;
import com.wan.util.Constact;
import com.wan.util.Result;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/2/20
 */
@RestController
public class OrderController {

    @Reference
    private IOrderService orderService;

    @Reference
    private IOrderItemService orderItemService;

    /**
     * 分页查询订单
     * @param start
     * @param size
     * @return
     */
    @GetMapping("/order")
    public Page4Navigator<Order> list(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5") int size){

        System.out.println("进入订单控制:"+start+","+size);
        //如果初始页小于0,则将初始页设置为0,否则设置为传进来的初始页
        start = start<0?0:start;
        //调用服务分页查询商品分类信息,默认显示页码数量为5
        Page4Navigator<Order> page =orderService.list(start, size, 5);
        System.out.println("订单控制调用服务返回:"+page.getContent());
        //对订单集合做批量的设置订单详情等工作
        List<Order> orders = orderItemService.fill(page.getContent());
        System.out.println("设置完订单详情后,返回的订单集合:"+orders);
        //对订单集合进行批量解耦
        orderService.removeOrderFromOrderItem(page.getContent());
        //将订单集合放回页面对象
        page.setContent(orders);
        System.out.println("设置完订单详情后,页面订单:"+page.getContent());
        //返回页面
        return page;
    }


    /**
     * 修改订单的状态
     * @param oid
     * @return
     */
    @PutMapping("deliveryOrder/{oid}")
    public Object deliveryOrder(@PathVariable int oid) {
        //通过订单ID,查询单个订单
        Order o = orderService.get(oid);
        //设置发货日期
        o.setDeliveryDate(new Date());
        //设置订单的状态
        o.setStatus(Constact.waitConfirm);
        orderService.update(o);
        return Result.success();
    }


}
