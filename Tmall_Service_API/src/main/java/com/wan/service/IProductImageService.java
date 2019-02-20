package com.wan.service;

import com.wan.pojo.Product;
import com.wan.pojo.ProductImage;

import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */
public interface IProductImageService {

    //添加图片
    ProductImage add(ProductImage bean);

    //通过ID删除图片
    void delete(int id);

    //获取图片对象
    ProductImage get(int id);

    //查询单个商品的图片集合
    List<ProductImage> listSingleProductImages(Product product);

    //查询图片详情
    List<ProductImage> listDetailProductImages(Product product);

    //设置商品的第一张图片
    Product setFirstProductImage(Product product);

    //批量设置商品集合的第一张图片
    List<Product> setFirstProductImages(List<Product> products);

}
