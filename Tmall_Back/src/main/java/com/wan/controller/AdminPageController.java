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
     * 访问地址admin_category_list 就会访问 admin/listCategory.html
     * @return
     */
    @GetMapping(value = "/admin_category_list")
    public String listCategory(){
        return "admin/listCateGory";
    }


}
