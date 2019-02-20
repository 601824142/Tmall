package com.wan.service;

import com.wan.pojo.Page4Navigator;
import com.wan.pojo.User;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */
public interface IUserService {

    //用户分页查询
    Page4Navigator<User> list(int start, int size, int navigatePages);

}
