package com.wan.dao;

import com.wan.pojo.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author 万星明
 * @Date 2019/2/16
 */
@Repository
public interface CategoryDao extends JpaRepository<Category,Integer> {
}
