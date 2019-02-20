package com.wan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wan.pojo.Page4Navigator;
import com.wan.pojo.Product;
import com.wan.service.IProductImageService;
import com.wan.service.IProductService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */

@RestController
public class ProductController {

    @Reference
    private IProductService productService;

    @Reference
    private IProductImageService productImageService;


    /**
     * 查询商品分页数据
     * @param cid
     * @param start
     * @param size
     * @return
     */
    @GetMapping("/categories/{cid}/products")
    public Page4Navigator<Product> list(@PathVariable("cid") int cid,
                                         @RequestParam(value = "start",defaultValue = "0") int start,
                                         @RequestParam(value = "size",defaultValue = "5") int size){

        //如果传进来的页码小于0,则将页码至为0,查询第一页的数据
        start = start<0?0:start;
        //调用服务查询分页数据
        Page4Navigator<Product> page = productService.findProductListByPage(cid, start, size, 5);

        System.out.println("看看路径："+page.getContent());
        //设置产品的图片
        List<Product> productList = productImageService.setFirstProductImages(page.getContent());

        //将设置完图片的分页集合放回分页类返回
        page.setContent(productList);

        //返回分页的数据
        return page;
    }


    /**
     * 通过ID获取商品
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/products/{id}")
    public Product get(@PathVariable("id") int id) throws Exception {
        Product bean=productService.getProduct(id);
        return bean;
    }

    /**
     * 添加商品,并返回商品对象
     * @param product
     * @return
     * @throws Exception
     */
    @PostMapping("/products")
    public Object add(@RequestBody Product product) throws Exception {
        productService.addProduct(product);
        return product;
    }

    /**
     * 根据商品ID,删除商品
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        productService.deleteProduct(id);
        return null;
    }

    /**
     * 修改商品
     * @param product
     * @return
     * @throws Exception
     */
    @PutMapping("/products")
    public Object update(@RequestBody Product product) throws Exception {
        productService.updateProduct(product);
        return product;
    }


}
