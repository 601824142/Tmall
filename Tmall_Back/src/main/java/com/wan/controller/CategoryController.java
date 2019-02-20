package com.wan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wan.pojo.Category;
import com.wan.pojo.Page4Navigator;
import com.wan.service.ICategoryService;
import com.wan.util.ImageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Author 万星明
 * @Date 2019/2/16
 */
@RestController
public class CategoryController {

    @Value("${Image.uploadSrc}")
    private String uploadSrc;

    @Reference
    private ICategoryService categoryService;

    /**
     * 调用服务,查询全部商品分类信息返回给页面,不包含分页
     * @return
     */
//    @GetMapping("/categories")
//    public List<Category> queryAllCateGory(){
//        List<Category> categoryList = categoryService.queryAllCateGory();
//        System.out.println("控制—查询分类信息"+categoryList);
//        return categoryList;
//    }


    /**
     * 调用服务,查询全部商品分类信息返回给页面,包含分页
     * @return
     */
    @GetMapping("/categories")
    public Page4Navigator<Category> queryAllCateGory(@RequestParam(value = "start", defaultValue = "0") int start, @RequestParam(value = "size", defaultValue = "5")int size){
        //如果初始页小于0,则将初始页设置为0,否则设置为传进来的初始页
        start = start<0?0:start;

        //调用服务分页查询商品分类信息,默认显示页码数量为5
        Page4Navigator<Category> page = categoryService.queryAllCateGory(start, size, 5);

        System.out.println("控制—查询分类信息分页"+page);
        //返回分页包装类对象
        return page;
    }


    /**
     * 添加商品分类信息
     * @param category
     * @param image
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/categories")
    public Object add(Category category, MultipartFile image, HttpServletRequest request) throws IOException {
        //调用服务添加分类信息
        Category cateGory = categoryService.addCateGory(category);
        //读取分类信息中的图片信息保存
        saveOrUpdateImageFile(cateGory, image, request);
        //返回添加的这个分类
        return category;
    }

    /**
     * 添加或修改图片文件
     */
    public void saveOrUpdateImageFile(Category cateGory, MultipartFile image, HttpServletRequest request)
            throws IOException {
        System.out.println("进入—添加或修改图片文件1:"+request.getServletContext().getRealPath("img/category"));
        File imageFolder= new File(uploadSrc);

        System.out.println("进入—添加或修改图片文件2:"+cateGory.getId());
        File file = new File(imageFolder,cateGory.getId()+".jpg");
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        image.transferTo(file);
        BufferedImage img = ImageUtil.change2JPG(file);
        ImageIO.write(img, "jpg", file);
    }


    /**
     * 删除商品分类信息
     * @param id
     * @param request
     * @return
     */
    @DeleteMapping("/categories/{id}")
    public String delete(@PathVariable("id") int id,HttpServletRequest request){
        //调用服务,删除商品分类
        categoryService.deleteCateGory(id);
        //通过图片上传的路径,创建文件对象
        File imageFolder= new File(uploadSrc);
        File file = new File(imageFolder,id+".jpg");
        //删除该文件
        file.delete();
        return null;
    }


    /**
     * 通过ID获取商品分类
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/categories/{id}")
    public Category get(@PathVariable("id") int id) throws Exception {
        Category bean=categoryService.getCateGory(id);
        return bean;
    }


    /**
     * 修改相对应的商品分类信息
     * @param bean
     * @param image
     * @param request
     * @return
     * @throws Exception
     */
    @PutMapping("/categories/{id}")
    public Object update(Category bean, MultipartFile image,HttpServletRequest request) throws Exception {
        //从请求中获取修改的分类名称
        String name = request.getParameter("name");
        //设置
        bean.setName(name);
        //调用接口修改
        categoryService.updateCateGory(bean);

        if(image!=null) {
            saveOrUpdateImageFile(bean, image, request);
        }
        return bean;
    }


}
