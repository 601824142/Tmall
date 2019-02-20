package com.wan.serviceImpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.wan.dao.PropertyDao;
import com.wan.pojo.Category;
import com.wan.pojo.Page4Navigator;
import com.wan.pojo.Property;
import com.wan.service.ICategoryService;
import com.wan.service.IPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * @Author 万星明
 * @Date 2019/2/18
 */
@Service
public class PropertyServiceImpl implements IPropertyService {

    @Autowired
    private PropertyDao propertyDao;

    @Reference
    private ICategoryService categoryService;


    /**
     * 添加属性
     * @param property
     */
    @Override
    public void addProperty(Property property) {
        propertyDao.save(property);
    }

    /**
     * 通过ID删除属性
     * @param id
     */
    @Override
    public void deleteProperty(int id) {
        propertyDao.deleteById(id);
    }


    /**
     * 通过ID获取属性
     * @param id
     * @return
     */
    @Override
    public Property getProperty(int id) {
        Optional<Property> optional = propertyDao.findById(id);
        return optional.get();
    }


    /**
     * 修改更新商品属性
     * @param property
     */
    @Override
    public void updateProperty(Property property) {
        propertyDao.saveAndFlush(property);
    }


    /**
     * 分页查询商品的属性
     * @param cid
     * @param start (页码)
     * @param size  (页面显示记录数量)
     * @param navigatePages (显示的页码数量)
     * @return
     */
    @Override
    public Page4Navigator<Property> findPropertyListByPage(int cid, int start, int size, int navigatePages) {
        //通过商品ID查询商品
        Category cateGory = categoryService.getCateGory(cid);

        //创建排序器,通过商品属性ID,倒序排列
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        //通过JPA内置分页,创建分页请求
        Pageable pageable = PageRequest.of(start, size, sort);
        //关联查询,查询对应商品分类下的属性,并进行分页
        Page<Property> propertyPage = propertyDao.findByCategory(cateGory, pageable);
        //创建分页包装类对象
        Page4Navigator<Property> page4Navigator = new Page4Navigator<>(propertyPage, navigatePages);

        //返回分页包装类对象
        return page4Navigator;
    }


    /**
     * 通过分类获取所有属性集合的方法
     * @param category
     * @return
     */
    @Override
    public List<Property> findAllPropertyByCategory(Category category) {
        return propertyDao.findByCategory(category);
    }
}
