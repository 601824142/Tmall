package com.wan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wan.pojo.Product;
import com.wan.pojo.PropertyValue;
import com.wan.service.IProductService;
import com.wan.service.IPropertyValueService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */
@RestController
public class PropertyValueController {

    @Reference
    private IProductService productService;

    @Reference
    private IPropertyValueService propertyValueService;


    /**
     * 获取相对应的商品属性值集合
     * @param pid
     * @return
     */
    @GetMapping("/products/{pid}/propertyValues")
    public List<PropertyValue> list(@PathVariable("pid") int pid) {
        //通过商品ID获取到商品对象
        Product product = productService.getProduct(pid);
        //对该商品的属性值进行初始化
        propertyValueService.init(product);
        //获取该商品的全部属性值
        List<PropertyValue> propertyValues = propertyValueService.list(product);
        //并返回
        return propertyValues;
    }

    /**
     * 修改保存商品的属性值
     * @param bean
     * @return
     */
    @PutMapping("/propertyValues")
    public Object update(@RequestBody PropertyValue bean) {
        //将修改后的属性值对象,调用服务,保存修改
        propertyValueService.update(bean);
        return bean;
    }

}
