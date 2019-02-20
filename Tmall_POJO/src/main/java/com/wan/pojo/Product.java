package com.wan.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */

@Data
@Entity //表明该类为实体类
@Table(name = "product")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer"})
@Document(indexName = "tmall_springboot",type = "product") //将该类加入到文档中
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @ManyToOne
    @JoinColumn(name="cid")
    private Category category;

    //如果既没有指明关联到哪个Column,又没有明确要用@Transient忽略,那么就会自动关联到表对应的同名字段
    private String name;
    private int stock;
    private String subTitle;
    private float originalPrice;
    private float promotePrice;
    private Date createDate;

    @Transient //该注解标记的成员变量不参与序列化过程
    private ProductImage firstProductImage;//主商品图片

}
