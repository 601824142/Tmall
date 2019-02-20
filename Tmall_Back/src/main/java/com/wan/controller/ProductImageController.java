package com.wan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wan.pojo.Product;
import com.wan.pojo.ProductImage;
import com.wan.service.ICategoryService;
import com.wan.service.IProductImageService;
import com.wan.service.IProductService;
import com.wan.util.Constact;
import com.wan.util.ImageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */

@RestController
public class ProductImageController {

    @Value("${ProductImage.uploadSrc}")
    private String ProductImageSrc;

    @Reference
    private IProductService productService;

    @Reference
    private IProductImageService productImageService;

    @Reference
    private ICategoryService categoryService;


    @GetMapping("/products/{pid}/productImages")
    public List<ProductImage> list(@RequestParam("type")String type, @PathVariable("pid")int pid){
        //通过商品ID查询商品信息
        Product product = productService.getProduct(pid);

        //判断传入的类型
        if(Constact.type_single.equals(type)) {
            //查询简单图片集合
            List<ProductImage> singles =  productImageService.listSingleProductImages(product);
            return singles;
        }else if (Constact.type_detail.equals(type)){
            //查询详情图片集合
            List<ProductImage> details = productImageService.listDetailProductImages(product);
            return details;
        }else {
            //否则返回一个新的集合
            return new ArrayList<>();
        }
    }



    @PostMapping("/productImages")
    public Object add(int pid, String type, MultipartFile image, HttpServletRequest request){

        //新建商品图片对象
        ProductImage productImage = new ProductImage();
        //通过ID查询商品
        Product product = productService.getProduct(pid);
        //给图片设置商品对象属性
        productImage.setProduct(product);
        //设置图片的格式
        productImage.setType(type);
        //调用服务,添加图片
        ProductImage saveProductImage = productImageService.add(productImage);

        //准备文件夹
        String folder = ProductImageSrc;
        if(Constact.type_single.equals(productImage.getType())){
            System.out.println("添加简单图片："+folder);
            folder +="productSingle";
            System.out.println("添加简单图片："+folder);
        } else{
            folder +="productDetail";
            System.out.println("添加详情图片："+folder);
        }
        System.out.println("测试商品图片上传地址："+request.getServletContext().getRealPath(folder));

        //新建文件夹对象,将刚刚准备好的文件夹路径作为文件对象参数
        File imageFolder = new File(folder);
        System.out.println("1测试："+imageFolder.getAbsolutePath());
        //创建图片
        File file = new File(imageFolder,saveProductImage.getId()+".jpg");
        System.out.println("2测试："+file.getAbsolutePath());
        //获取文件名
        String fileName = file.getName();
        System.out.println("文件名为："+fileName);

        //判断文件的父文件夹是否存在
        if(!file.getParentFile().exists()){
            //如果不存在,创建
            file.getParentFile().mkdirs();
        }

        //写入商品图片
        try {
                //使用MultipartFile类的transferTo方法可以将接收到的文件写入指定的文件中
                image.transferTo(file);
                //获取文件,将其返回为图片
                BufferedImage img = ImageUtil.change2JPG(file);
                //写入图片,并设置格式、路径
                ImageIO.write(img, "jpg", file);
        } catch (IOException e) {
                 e.printStackTrace();
        }

        if (Constact.type_single.equals(productImage.getType())){
            String imageFolder_small= ProductImageSrc+"productSingle_small";
            String imageFolder_middle= ProductImageSrc+"productSingle_middle";
            System.out.println("小图地址："+imageFolder_small+",中图地址："+imageFolder_middle);

            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.getParentFile().mkdirs();
            f_middle.getParentFile().mkdirs();
            ImageUtil.resizeImage(file, 56, 56, f_small);
            ImageUtil.resizeImage(file, 217, 190, f_middle);
        }

        //返回图片对象
        return productImage;
    }


    @DeleteMapping("/productImages/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request){
        //通过ID先查询商品图片对象
        ProductImage bean = productImageService.get(id);
        //调用扶贫删除
        productImageService.delete(id);
        //定义文件路径
        String folder = ProductImageSrc;
        //判断商品图片类型,添加路径
        if(Constact.type_single.equals(bean.getType())){
            System.out.println("删除简单图片："+folder);
            folder +="productSingle";
            System.out.println("删除简单图片："+folder);
        } else{
            folder +="productDetail";
            System.out.println("删除详情图片："+folder);
        }

        File  imageFolder= new File(folder);
        File file = new File(imageFolder,bean.getId()+".jpg");
        String fileName = file.getName();
        file.delete();

        if(Constact.type_single.equals(bean.getType())){
            String imageFolder_small= ProductImageSrc+"productSingle_small";
            String imageFolder_middle= ProductImageSrc+"productSingle_middle";
            File f_small = new File(imageFolder_small, fileName);
            File f_middle = new File(imageFolder_middle, fileName);
            f_small.delete();
            f_middle.delete();
        }

        return null;
    }







}
