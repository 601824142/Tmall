package com.wan.service;

import com.wan.pojo.Page4Navigator;
import com.wan.pojo.Product;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */
public interface IProductService {

    //添加商品
    void addProduct(Product product);

    //根据ID删除商品
    void deleteProduct(int id);

    //根据ID获取商品
    Product getProduct(int id);

    //更新商品
    void  updateProduct(Product product);

    //对应的商品分类,分页查询全部商品,参数:(商品分类ID,页码,每页记录数,显示的页码数量)
    Page4Navigator<Product> findProductListByPage(int cid, int start, int size, int navigatePages);

}
