package com.wan.serviceImpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.wan.dao.ProductDao;
import com.wan.pojo.Category;
import com.wan.pojo.Page4Navigator;
import com.wan.pojo.Product;
import com.wan.service.ICategoryService;
import com.wan.service.IProductImageService;
import com.wan.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
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

    @Reference
    private IProductImageService productImageService;

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


    /**
     * 为分类填充产品集合
     * @param category
     */
    @Override
    public void fill(Category category) {
        //查询分类下的全部产品
        List<Product> products = listByCategory(category);
        //设置首张图片
        productImageService.setFirstProductImages(products);
        //将商品加入商品分类
        category.setProducts(products);
    }


    /**
     * 为多个分类填充产品集合
     * @param categories
     */
    @Override
    public void fill(List<Category> categories) {
        //循环商品分类,设置商品分类的产品图片
        for (Category category : categories) {
                fill(category);
        }
    }


    /**
     * 为多个分类填充推荐产品集合，即把分类下的产品集合，按照8个为一行，拆成多行，以利于后续页面上进行显示
     * @param categories
     */
    @Override
    public void fillByRow(List<Category> categories) {
        //定义分类下的产品,每行为8个
        int productNumberEachRow = 8;
        //循环商品分类集合
        for (Category category : categories) {
            //获得分类下的商品集合
            List<Product> products =  category.getProducts();
            //新建一个集合,装分类下首页显示的商品
            List<List<Product>> productsByRow =  new ArrayList<>();
            //循环商品分类下的商品集合,然后每8个为一行
            for (int i = 0; i < products.size(); i+=productNumberEachRow) {
                //定义大小
                int size = i+productNumberEachRow;
                //判断如果每次循环商品容量小于i+8以后,那么将商品容量给size
                size= size>products.size()?products.size():size;
                //将分类下的商品集合以8为长截取,给每一行的商品集合
                List<Product> productsOfEachRow =products.subList(i, size);
                //将截取出来的商品集合,放入每一行的显示商品集合中
                productsByRow.add(productsOfEachRow);
            }
            //将分类下的商品集合设置到分类的属性下
            category.setProductsByRow(productsByRow);
        }
    }


    /**
     * 查询某个分类下的所有产品
     * @param category
     * @return
     */
    @Override
    public List<Product> listByCategory(Category category) {
        //返回根据分类查询全部的商品
        return productDao.findByCategoryOrderById(category);
    }
}
