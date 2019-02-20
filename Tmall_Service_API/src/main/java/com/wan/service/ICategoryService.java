package com.wan.service;

import com.wan.pojo.Category;
import com.wan.pojo.Page4Navigator;

import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/2/16
 */
public interface ICategoryService {

    //查询全部商品分类
    List<Category> queryAllCateGory();

    //查询全部商品分类,进行分页
    Page4Navigator<Category> queryAllCateGory(int start, int size, int navigatePages);

    //添加商品分类信息
    Category addCateGory(Category category);

    //删除商品分类信息
    void deleteCateGory(int id);

    //通过ID查询商品分类信息
    Category getCateGory(int id);

    //修改商品分类信息
    void updateCateGory(Category category);

    //1、从产品中删除所属分类,解耦和
    void removeCategoryFromProduct(List<Category> categories);

    //2、从产品中删除所属分类,解耦和
    void removeCategoryFromProduct(Category category);

}
