package com.warehouse.warehouse.pojo.po.goods;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 商品类目(GoodCategory)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "good_category")
public class GoodCategory implements Serializable {

    private static final long serialVersionUID = 635634825208973860L;

    /**
     * 类目ID
     */
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Long id;

    /**
     * 父类目ID=0时，代表的是一级的类目
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 类目名称
     */
    @Column(name = "name")
    private String name;

    @Column(name = "company_id")
    private String companyId;

}