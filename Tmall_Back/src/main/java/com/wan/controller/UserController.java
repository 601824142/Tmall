package com.wan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wan.pojo.Page4Navigator;
import com.wan.pojo.User;
import com.wan.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */

@RestController
public class UserController {

    @Reference
    private IUserService userService;


    @GetMapping("/users")
    public Page4Navigator<User> list(@RequestParam(value = "start", defaultValue = "0") int start,
                                                      @RequestParam(value = "size", defaultValue = "5") int size) {
        //如果初始页小于0,则将初始页设置为0,否则设置为传进来的初始页
        start = start<0?0:start;
        //调用服务分页查询商品分类信息,默认显示页码数量为3
        Page4Navigator<User> page = userService.list(start,size,5);
        return page;
    }


}
