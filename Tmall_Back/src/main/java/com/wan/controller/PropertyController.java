package com.wan.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.wan.pojo.Page4Navigator;
import com.wan.pojo.Property;
import com.wan.service.IPropertyService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */

@RestController
public class PropertyController {

    @Reference
    private IPropertyService propertyService;


    /**
     * 查询商品属性的分页数据
     * @param cid
     * @param start
     * @param size
     * @return
     */
    @GetMapping("/categories/{cid}/properties")
    public Page4Navigator<Property> list(@PathVariable("cid") int cid,
                                                            @RequestParam(value = "start",defaultValue = "0") int start,
                                                            @RequestParam(value = "size",defaultValue = "5") int size){

        //如果传进来的页码小于0,则将页码至为0,查询第一页的数据
        start = start<0?0:start;
        //调用服务查询分页数据
        Page4Navigator<Property> page = propertyService.findPropertyListByPage(cid, start, size, 5);
        //返回分页的数据
        return page;
    }

    /**
     * 通过ID获取属性
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/properties/{id}")
    public Property get(@PathVariable("id") int id) throws Exception {
        Property bean=propertyService.getProperty(id);
        return bean;
    }

    /**
     * 添加商品属性,并返回商品属性对象
     * @param property
     * @return
     * @throws Exception
     */
    @PostMapping("/properties")
    public Object add(@RequestBody Property property) throws Exception {
        propertyService.addProperty(property);
        return property;
    }

    /**
     * 根据商品属性ID,删除商品属性
     * @param id
     * @param request
     * @return
     * @throws Exception
     */
    @DeleteMapping("/properties/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request)  throws Exception {
        propertyService.deleteProperty(id);
        return null;
    }

    /**
     * 修改商品的属性
     * @param property
     * @return
     * @throws Exception
     */
    @PutMapping("/properties")
    public Object update(@RequestBody Property property) throws Exception {
        propertyService.updateProperty(property);
        return property;
    }



}
