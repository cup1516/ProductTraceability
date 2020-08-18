package com.warehouse.warehouse.pojo.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 商品类目(GoodCategory)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodCategoryDTO implements Serializable {
    
    private static final long serialVersionUID = -85484614368124612L;

    /**
     * 类目ID
     */
    private Long id;

    /**
     * 父类目ID=0时，代表的是一级的类目
     */
    private Long parentId;

    /**
     * 类目名称
     */
    private String name;


    /**
     * 排序字段
     */
    private String orderBy;

    private String companyId;
}