package com.wan.dao;

import com.wan.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */
public interface UserDao extends JpaRepository<User,Integer> {
}
