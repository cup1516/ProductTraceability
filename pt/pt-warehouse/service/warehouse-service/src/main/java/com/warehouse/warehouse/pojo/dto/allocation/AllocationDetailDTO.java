package com.warehouse.warehouse.pojo.dto.allocation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 调拨单子表(AllocationDetail)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllocationDetailDTO implements Serializable {
    
    private static final long serialVersionUID = 800183149903046736L;

    /**
     * 编号
     */
    private String id;

    /**
     * 调拨id
     */
    private String pid;

    /**
     * 原货位id
     */
    private String locatorId;

    /**
     * 目标货位Id
     */
    private String toLocatorId;

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
     * 锁定库存的id
     */
    private String lockStockId;

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

    private String companyId;
}