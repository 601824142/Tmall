package com.wan.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 万星明
 * @Date 2019/2/19
 */
@Data
@Entity //表明该类为实体类
@Table(name = "propertyvalue")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class PropertyValue implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name="pid")
    private Product product;

    @ManyToOne
    @JoinColumn(name="ptid")
    private Property property;

    private String value;

    @Override
    public String toString() {
        return "PropertyValue [id=" + id + ", product=" + product + ", property=" + property + ", value=" + value + "]";
    }

}
