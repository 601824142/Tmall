package com.wan.dao;

import com.wan.pojo.Product;
import com.wan.pojo.Property;
import com.wan.pojo.PropertyValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */
public interface PropertyValueDao extends JpaRepository<PropertyValue,Integer> {

    //根据产品查询
    List<PropertyValue> findByProductOrderByIdDesc(Product product);

    //根据产品和属性获取PropertyValue对象
    PropertyValue getByPropertyAndProduct(Property property, Product product);

}
