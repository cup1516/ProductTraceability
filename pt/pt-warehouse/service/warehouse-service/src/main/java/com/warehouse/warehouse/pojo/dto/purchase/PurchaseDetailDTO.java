package com.warehouse.warehouse.pojo.dto.purchase;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 采购单子表(PurchaseDetail)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseDetailDTO implements Serializable {
    
    private static final long serialVersionUID = -43367681766608400L;

    /**
     * 编号
     */
    private String id;

    /**
     * 采购订单id
     */
    private String pid;

    /**
     * 商品id
     */
    private String commodityId;

    /**
     * 商品编码
     */
    private String commodityBar;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 数量
     */
    private Integer qty;

    /**
     * 创建人id
     */
    private String createId;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 修改人id
     */
    private String updateId;

    /**
     * 修改人
     */
    private String updator;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;


    /**
     * 排序字段
     */
    private String orderBy;
}