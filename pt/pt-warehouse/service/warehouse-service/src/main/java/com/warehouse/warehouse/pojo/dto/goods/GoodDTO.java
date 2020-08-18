package com.warehouse.warehouse.pojo.dto.goods;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Good)实体类
 *
 * @author makejava
 * @since 2020-04-11 00:05:51
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GoodDTO implements Serializable {
    
    private static final long serialVersionUID = -59997749268541367L;

    
    private String id;

    /**
     * 商品编号
     */
    private String goodNo;

    /**
     * 商品名称
     */
    private String goodName;

    /**
     * 商品类型,存的是分类表名称
     */
    private String goodType;

    /**
     * 单位
     */
    private Long unit;


    private String goodCategoryId;

    /**
     * 长
     */
    private Double length;

    /**
     * 宽
     */
    private Double width;

    /**
     * 高
     */
    private Double heigth;

    /**
     * 重量
     */
    private Double weigth;

    /**
     * 0 正常 1 删除 2 冻结
     */
    private Integer status;

    
    private String remark;

    /**
     * 创建人
     */
    private String createUserId;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createDate;

    /**
     * 修改人id
     */
    private String modifiedUserId;

    /**
     * 修改人名称
     */
    private String modifiedUserName;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date modifiedDate;

    /**
     * 单价
     */
    private Double price;


    /**
     * 排序字段
     */
    private String orderBy;
    private String companyId;
}