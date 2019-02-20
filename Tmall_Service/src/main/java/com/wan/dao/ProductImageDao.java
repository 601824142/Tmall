package com.wan.dao;

import com.wan.pojo.Product;
import com.wan.pojo.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */
public interface ProductImageDao extends JpaRepository<ProductImage,Integer> {

    //通过商品和类型倒序查询图片信息
    public List<ProductImage> findByProductAndTypeOrderByIdDesc(Product product, String type);

}
