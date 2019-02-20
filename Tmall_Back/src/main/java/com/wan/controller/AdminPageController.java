package com.wan.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author 万星明
 * @Date 2019/2/16
 */
@Controller
public class AdminPageController {

    /**
     * 访问地址 admin,跳转到后台管理分类admin_category_list去
     * @return
     */
    @GetMapping(value = "/admin")
    public String admin(){
        return "redirect:admin_category_list";
    }


    /**
     * 商品分类查询：访问地址admin_category_list 就会访问 admin/listCategory.html
     * @return
     */
    @GetMapping(value = "/admin_category_list")
    public String listCategory(){
        return "admin/listCateGory";
    }


    /**
     * 商品分类编辑：请求跳转到编辑商品分类信息
     * @return
     */
    @GetMapping(value="/admin_category_edit")
    public String editCategory(){
        return "admin/editCategory";
    }


    /**
     * 分类属性查询：
     * @return
     */
    @GetMapping(value="/admin_property_list")
    public String listProperty(){
        return "admin/listProperty";
    }

    /**
     * 分类属性编辑：
     * @return
     */
    @GetMapping(value="/admin_property_edit")
    public String editProperty(){
        return "admin/editProperty";

    }


    /**
     * 具体产品查询：
     * @return
     */
    @GetMapping(value="/admin_product_list")
    public String listProduct(){
        return "admin/listProduct";
    }

    /**
     * 具体产品编辑：
     * @return
     */
    @GetMapping(value="/admin_product_edit")
    public String editProduct(){
        return "admin/editProduct";
    }

    /**
     * 产品图片查询：
     * @return
     */
    @GetMapping(value="/admin_productImage_list")
    public String listProductImage(){
        return "admin/listProductImage";
    }

    /**
     * 产品属性值编辑：
     * @return
     */
    @GetMapping(value="/admin_propertyValue_edit")
    public String editPropertyValue(){
        return "admin/editPropertyValue";
    }


    /**
     * 用户查询：
     * @return
     */
    @GetMapping(value="/admin_user_list")
    public String listUser(){
        return "admin/listUser";
    }


    /**
     * 订单查询：
     * @return
     */
    @GetMapping(value="/admin_order_list")
    public String listOrder(){
        return "admin/listOrder";

    }

}
