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

}
