package com.wan.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @Author 万星明
 * @Date 2019/2/16
 */
@Configuration
public class CORSConfiguration extends WebMvcConfigurerAdapter {


//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        //所有请求都允许跨域(所有方法,所有请求头)
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("*")
//                .allowedHeaders("*");
//    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("跨域请求被调用！");
        //所有请求都允许跨域
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*");
    }



}
