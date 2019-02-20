package com.wan.serviceImpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.wan.dao.UserDao;
import com.wan.pojo.Page4Navigator;
import com.wan.pojo.User;
import com.wan.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Page4Navigator<User> list(int start, int size, int navigatePages) {
        //设置排序方式
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        //通过JPA自带的PageRequest分页请求,创建Pageable分页对象
        Pageable pageable = PageRequest.of(start, size, sort);
        //查询全部
        Page pageFromJPA =userDao.findAll(pageable);
        //返回分页数据
        return new Page4Navigator<>(pageFromJPA,navigatePages);
    }
}
