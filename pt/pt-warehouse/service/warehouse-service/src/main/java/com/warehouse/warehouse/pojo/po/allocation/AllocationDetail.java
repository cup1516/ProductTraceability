package com.warehouse.warehouse.pojo.po.allocation;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 调拨单子表(AllocationDetail)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "allocation_detail")
public class AllocationDetail implements Serializable {

    private static final long serialVersionUID = 971981986953743687L;

    /**
     * 编号
     */
    @Id
    private String id;

    /**
     * 调拨id
     */
    @Column(name = "pid")
    private String pid;

    /**
     * 原货位id
     */
    @Column(name = "locator_id")
    private String locatorId;

    /**
     * 原货位id
     */
    @Column(name = "to_locator_id")
    private String toLocatorId;

    /**
     * 商品id
     */
    @Column(name = "commodity_id")
    private String commodityId;

    /**
     * 商品编码
     */
    @Column(name = "commodity_bar")
    private String commodityBar;

    /**
     * 商品名称
     */
    @Column(name = "commodity_name")
    private String commodityName;

    /**
     * 数量
     */
    @Column(name = "qty")
    private Integer qty;

    /**
     * 锁定库存的id
     */
    @Column(name = "lock_stock_id")
    private String lockStockId;

    /**
     * 创建人id
     */
    @Column(name = "create_id")
    private String createId;

    /**
     * 创建人
     */
    @Column(name = "creator")
    private String creator;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改人id
     */
    @Column(name = "update_id")
    private String updateId;

    /**
     * 修改人
     */
    @Column(name = "updator")
    private String updator;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "company_id")
    private String companyId;

}