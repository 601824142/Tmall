package com.wan.serviceImpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.wan.dao.PropertyValueDao;
import com.wan.pojo.Product;
import com.wan.pojo.Property;
import com.wan.pojo.PropertyValue;
import com.wan.service.IPropertyService;
import com.wan.service.IPropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */
@Service
public class PropertyValueServiceImpl implements IPropertyValueService {

        @Autowired
        private PropertyValueDao propertyValueDao;

        @Reference
        private IPropertyService propertyService;


        /**
         * 保存更新传进来的属性值对象
         * @param bean
         */
        @Override
        public void update(PropertyValue bean) {
            propertyValueDao.save(bean);
        }


        /**
         * 初始化商品属性
         * @param product
         */
        @Override
        public void init(Product product) {
            //通过传进来的商品,获得商品的分类,再通过商品分类获得该分类下的全部属性
            List<Property> propertys = propertyService.findAllPropertyByCategory(product.getCategory());

            //循环获取属性
            for (Property property : propertys) {
                //获取该商品的相应属性的属性值
                PropertyValue propertyValue = getByPropertyAndProduct(product, property);
                //如果该商品的相应属性值为空,则赋值
                if (null == propertyValue){
                    propertyValue= new PropertyValue();
                    propertyValue.setProduct(product);
                    propertyValue.setProperty(property);
                    propertyValueDao.save(propertyValue);
                }
            }


        }


        /**
         * 获得相应商品的属性值
         * @param product
         * @param property
         * @return
         */
        @Override
        public PropertyValue getByPropertyAndProduct(Product product, Property property) {
            //获得相应的商品的属性值
            return propertyValueDao.getByPropertyAndProduct(property,product);
        }


        /**
         * 获取某个商品的全部属性,并且是倒序
         * @param product
         * @return
         */
        @Override
        public List<PropertyValue> list(Product product) {
            return propertyValueDao.findByProductOrderByIdDesc(product);
        }
}
