package com.wan.serviceImpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.wan.dao.ProductImageDao;
import com.wan.pojo.Product;
import com.wan.pojo.ProductImage;
import com.wan.service.IProductImageService;
import com.wan.service.IProductService;
import com.wan.util.Constact;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */
@Service
public class ProductImageServiceImpl implements IProductImageService {

    @Autowired
    private ProductImageDao productImageDao;

    @Reference
    private IProductService productService;


    /**
     * 添加商品图片
     * @param bean
     */
    @Override
    public ProductImage add(ProductImage bean) {
        return productImageDao.save(bean);
    }


    /**
     * 删除图片
     * @param id
     */
    @Override
    public void delete(int id) {
        productImageDao.deleteById(id);
    }


    /**
     * 根据ID查询图片
     * @param id
     * @return
     */
    @Override
    public ProductImage get(int id) {
        Optional<ProductImage> optional = productImageDao.findById(id);
        return optional.get();
    }


    /**
     * 查询单个图片集合
     * @param product
     * @return
     */
    @Override
    public List<ProductImage> listSingleProductImages(Product product) {
        return productImageDao.findByProductAndTypeOrderByIdDesc(product, Constact.type_single);
    }


    /**
     * 查询详情图片的集合
     * @param product
     * @return
     */
    @Override
    public List<ProductImage> listDetailProductImages(Product product) {
        return productImageDao.findByProductAndTypeOrderByIdDesc(product, Constact.type_detail);
    }


    /**
     *
     * @param product
     */
    @Override
    public Product setFirstProductImage(Product product) {
        //查询单个图片集合
        List<ProductImage> singleImages = listSingleProductImages(product);
        //如果单个商品图片集合不为空
        if(!singleImages.isEmpty()){
            System.out.println("给产品设置图片："+singleImages.get(0));
            product.setFirstProductImage(singleImages.get(0));
        }else{
            product.setFirstProductImage(new ProductImage());
            //这样做是考虑到产品还没有来得及设置图片，但是在订单后台管理里查看订单项的对应产品图片。
        }

        return product;
    }



    @Override
    public List<Product> setFirstProductImages(List<Product> products) {

         //批量进行主图片的设置
        for (Product product : products){
            System.out.println("'批量设置商品图片："+product.toString());
            setFirstProductImage(product);
        }

        return products;
    }


}
