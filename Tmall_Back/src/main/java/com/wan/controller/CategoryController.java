package com.wan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wan.pojo.Category;
import com.wan.pojo.Page4Navigator;
import com.wan.service.ICategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 万星明
 * @Date 2019/2/16
 */
@RestController
public class CategoryController {

    @Reference
    private ICategoryService categoryService;

    /**
     * 调用服务,查询全部商品分类信息返回给页面,不包含分页
     * @return
     */
//    @GetMapping("/categories")
//    public List<Category> queryAllCateGory(){
//        List<Category> categoryList = categoryService.queryAllCateGory();
//        System.out.println("控制—查询分类信息"+categoryList);
//        return categoryList;
//    }



    @GetMapping("/categories")
    public Page4Navigator<Category> queryAllCateGory(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5")int size){
        //如果初始页小于0,则将初始页设置为0,否则设置为传进来的初始页
        start = start<0?0:start;

        //调用服务分页查询商品分类信息,默认显示页码数量为3
        Page4Navigator<Category> page = categoryService.queryAllCateGory(start, size, 5);

        System.out.println("控制—查询分类信息分页"+page);
        //返回分页包装类对象
        return page;
    }



}
