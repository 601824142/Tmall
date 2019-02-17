package com.wan.pojo;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author 万星明
 * @Date 2019/2/17
 *
 * 属性实体类
 */
@Data
@Entity //表明该类为实体类
@Table(name = "property")
public class Property {

}
