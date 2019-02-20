package com.wan.dao;

import com.wan.pojo.Category;
import com.wan.pojo.Property;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/2/18
 */
public interface PropertyDao extends JpaRepository<Property,Integer> {

    //根据分类查询,并进行分页,第二个参数为支持分页参数
    Page<Property> findByCategory(Category category, Pageable pageable);

    //通过分类获取所有属性集合的方法
    List<Property> findByCategory(Category category);

}
