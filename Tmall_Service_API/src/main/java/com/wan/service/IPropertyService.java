package com.wan.service;

import com.wan.pojo.Category;
import com.wan.pojo.Page4Navigator;
import com.wan.pojo.Property;

import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/2/18
 */
public interface IPropertyService {

    //添加属性
    void addProperty(Property property);

    //根据ID删除属性
    void deleteProperty(int id);

    //根据ID获取属性
    Property getProperty(int id);

    //更新商品属性
    void  updateProperty(Property property);

    //分页查询全部属性,参数:(商品分类ID,页码,每页记录数,显示的页码数量)
    Page4Navigator<Property> findPropertyListByPage(int cid,int start,int size,int navigatePages);

    //通过分类获取所有属性集合的方法
    List<Property> findAllPropertyByCategory(Category category);

}
