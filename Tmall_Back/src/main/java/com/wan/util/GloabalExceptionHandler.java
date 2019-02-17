package com.wan.util;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author 万星明
 * @Date 2019/2/16
 *
 * 异常处理，主要是在处理删除父类信息的时候，因为外键约束的存在，而导致违反约束
 */

@RestController
@ControllerAdvice //将作用在所有注解了@RequestMapping的控制器的方法上
public class GloabalExceptionHandler {

    //添加了该注解,用于全局处理控制器里的异常。
    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        e.printStackTrace();
        //通过反射加载外键约束异常类
        Class constraintViolationException = Class.forName("org.hibernate.exception.ConstraintViolationException");
        if(null != e.getCause()  && constraintViolationException==e.getCause().getClass()) {
            return "违反了约束，多半是外键约束";
        }
        //返回抛出的异常信息
        return e.getMessage();
    }

}
