package com.wan.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wan.dao.CategoryDao;
import com.wan.pojo.Category;
import com.wan.pojo.Page4Navigator;
import com.wan.pojo.Product;
import com.wan.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

/**
 * @Author 万星明
 * @Date 2019/2/16
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    @Autowired
    private CategoryDao categoryDao;


    /**
     * 查询全部商品分类,并倒序排列
     * @return
     */
    @Override
    public List<Category> queryAllCateGory() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");//通过ID倒序排列
        return categoryDao.findAll(sort);
    }


    /**
     * 查询全部商品分类,并倒序排序且传递给分页包装类,进行分页
     * @param start
     * @param size
     * @param navigatePages
     * @return
     */
    @Override
    public Page4Navigator<Category> queryAllCateGory(int start, int size, int navigatePages) {
        Sort sort = new Sort(Sort.Direction.DESC, "id");//通过ID倒序排列

        //通过JPA自带的PageRequest分页请求,创建Pageable分页对象
        Pageable pageable = PageRequest.of(start, size, sort);
        //通过Dao层从JPA中获取分页好的商品分类集合
        Page pageFromJPA = categoryDao.findAll(pageable);

        System.out.println("分页包装类调用成功:"+pageFromJPA);

        //将分页的商品分类集合传给包装类,通过包装类设置显示页码数navigatePages
        Page4Navigator<Category> page4Navigator = new Page4Navigator<Category>(pageFromJPA, navigatePages);
        //返回分页包装类
        return page4Navigator;
    }


    /**
     * 添加商品分类信息
     * @param category
     */
    @Override
    public Category addCateGory(Category category) {
        Category saveCateGory = categoryDao.saveAndFlush(category);
        return saveCateGory;
    }


    /**
     * 删除商品分类信息
     * @param id
     */
    @Override
    public void deleteCateGory(int id) {
        categoryDao.deleteById(id);
    }


    /**
     * 通过ID获取商品分类信息
     * @param id
     * @return
     */
    @Override
    public Category getCateGory(int id) {
        Optional<Category> categoryOptional = categoryDao.findById(id);
        return categoryOptional.get();
    }


    /**
     * 修改商品分类信息
     * @param category
     */
    @Override
    public void updateCateGory(Category category) {
        categoryDao.saveAndFlush(category);
    }


    /**
     * 解耦和,解除商品分类和具体商品的关系
     * @param categories
     */
    @Override
    public void removeCategoryFromProduct(List<Category> categories) {
        for (Category category : categories) {
            removeCategoryFromProduct(category);
        }
    }

    /**
     * 解耦和,解除商品分类和具体商品的关系
     * @param category
     */
    @Override
    public void removeCategoryFromProduct(Category category) {
        //通过商品分类获取该分类下的全部商品信息
        List<Product> products = category.getProducts();
        //如果商品集合不为空,则遍历
        if (null!=products){
            //则遍历商品集合,将商品对象中的商品分类属性至为空
            for (Product product : products) {
                product.setCategory(null);
            }
        }

        //将商品分类下的推荐商品列表至为空,解耦
        List<List<Product>> productsByRow = category.getProductsByRow();
        //判断推荐商品是否为空
        if (null!=productsByRow){
            for (List<Product> productList : productsByRow) {
                for (Product pd : productList) {
                    pd.setCategory(null);
                }
            }
        }

    }


}
