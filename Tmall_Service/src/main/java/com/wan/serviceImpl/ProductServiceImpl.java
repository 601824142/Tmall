package com.wan.serviceImpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.wan.dao.ProductDao;
import com.wan.pojo.Category;
import com.wan.pojo.Page4Navigator;
import com.wan.pojo.Product;
import com.wan.service.ICategoryService;
import com.wan.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductDao productDao;

    @Reference
    private ICategoryService categoryService;

    @Override
    public void addProduct(Product product) {
        productDao.save(product);
    }

    @Override
    public void deleteProduct(int id) {
        productDao.deleteById(id);
    }

    @Override
    public Product getProduct(int id) {
        Optional<Product> optional = productDao.findById(id);
        return optional.get();
    }

    @Override
    public void updateProduct(Product product) {
        productDao.saveAndFlush(product);
    }

    @Override
    public Page4Navigator<Product> findProductListByPage(int cid, int start, int size, int navigatePages) {
        //通过商品ID查询商品
        Category cateGory = categoryService.getCateGory(cid);

        //创建排序器,通过商品属性ID,倒序排列
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        //通过JPA内置分页,创建分页请求
        Pageable pageable = PageRequest.of(start, size, sort);
        //关联查询,查询对应商品分类下的属性,并进行分页
        Page<Product> propertyPage = productDao.findByCategory(cateGory, pageable);
        //创建分页包装类对象
        Page4Navigator<Product> page4Navigator = new Page4Navigator<>(propertyPage, navigatePages);

        //返回分页包装类对象
        return page4Navigator;
    }
}
