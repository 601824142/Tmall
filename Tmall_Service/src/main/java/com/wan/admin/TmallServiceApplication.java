package com.wan.admin;

import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.wan")
@DubboComponentScan("com.wan.serviceImpl")//服务管理中心注册扫描
@EnableJpaRepositories("com.wan.dao")
@EntityScan("com.wan.pojo")
public class TmallServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TmallServiceApplication.class, args);
    }

}

