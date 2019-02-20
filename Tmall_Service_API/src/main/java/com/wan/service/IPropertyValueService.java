package com.wan.service;

import com.wan.pojo.Product;
import com.wan.pojo.Property;
import com.wan.pojo.PropertyValue;

import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */
public interface IPropertyValueService {

    //修改更新产品属性
    void update(PropertyValue bean);

    //初始化产品属性
    void init(Product product);

    //通过属性名和产品获取相应的产品的属性值
    PropertyValue getByPropertyAndProduct(Product product, Property property);

    //通过产品查询对应的属性值
    List<PropertyValue> list(Product product);

}
