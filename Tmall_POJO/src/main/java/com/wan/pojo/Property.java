package com.wan.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 万星明
 * @Date 2019/2/17
 * <p>
 * 属性实体类
 */
@Data
@Entity //表明该类为实体类
@Table(name = "property")
public class Property implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //主键生成策略,自行增长
    @Column(name = "id")
    private int id;


    @Column(name = "name")
    private String name;


    @ManyToOne
    @JoinColumn(name = "cid")
    private Category category; //外键关联商品分类信息


    @Override
    public String toString() {
        return "Property{" + "id=" + id + ", name='" + name + '\'' + ", category=" + category + '}';
    }
}
