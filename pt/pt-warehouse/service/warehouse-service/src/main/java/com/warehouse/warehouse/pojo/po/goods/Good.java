package com.warehouse.warehouse.pojo.po.goods;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (Good)实体类
 *
 * @author makejava
 * @since 2020-04-11 00:05:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "good")
public class Good implements Serializable {

    private static final long serialVersionUID = -31995849214215752L;

    @Id
    private String id;

    /**
     * 商品编号
     */
    @Column(name = "good_no")
    private String goodNo;

    /**
     * 商品名称
     */
    @Column(name = "good_name")
    private String goodName;

    /**
     * 商品类型,存的是分类表名称
     */
    @Column(name = "good_type")
    private String goodType;

    /**
     * 单位
     */
    @Column(name = "unit")
    private Long unit;

    /**
     * 长
     */
    @Column(name = "length")
    private Double length;

    /**
     * 宽
     */
    @Column(name = "width")
    private Double width;

    /**
     * 高
     */
    @Column(name = "heigth")
    private Double heigth;

    /**
     * 重量
     */
    @Column(name = "weigth")
    private Double weigth;

    /**
     * 单价
     */
    @Column(name = "price")
    private Double price;

    /**
     * 0 正常 1 删除 2 冻结
     */
    @Column(name = "status")
    private Integer status;

    
    @Column(name = "remark")
    private String remark;

    /**
     * 创建人
     */
    @Column(name = "create_user_id")
    private String createUserId;

    /**
     * 创建人名称
     */
    @Column(name = "create_user_name")
    private String createUserName;


    @Column(name = "category_id")
    private String goodCategoryId;
    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 修改人id
     */
    @Column(name = "modified_user_id")
    private String modifiedUserId;

    /**
     * 修改人名称
     */
    @Column(name = "modified_user_name")
    private String modifiedUserName;

    /**
     * 修改时间
     */
    @Column(name = "modified_date")
    private Date modifiedDate;

    @Column(name = "company_id")
    private String companyId;

}