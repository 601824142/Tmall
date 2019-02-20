package com.wan.dao;

import com.wan.pojo.Category;
import com.wan.pojo.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */
public interface ProductDao extends JpaRepository<Product,Integer> {

    //查询对应商品分类下的全部商品,并分页
    Page<Product> findByCategory(Category category, Pageable pageable);
}
