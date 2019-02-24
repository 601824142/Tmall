package com.wan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wan.pojo.Category;
import com.wan.service.ICategoryService;
import com.wan.service.IProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/2/24
 */
@RestController
public class ForeRESTController {

    @Reference
    private ICategoryService categoryService;

    @Reference
    private IProductService productService;

    @GetMapping("/forehome")
    public Object home() {
        //查询全部的商品分类
        List<Category> cs= categoryService.queryAllCateGory();
        //调用服务,为多个分类填充产品集合
        productService.fill(cs);
        //为多个分类填充推荐产品集合,就是把分类下的产品集合,按照8个一行拆成多行,以便显示
        productService.fillByRow(cs);
        //从产品中删除所属分类,解耦和
        categoryService.removeCategoryFromProduct(cs);
        //返回已经解耦和的首页展示商品分类下的商品集合
        return cs;
    }



}
