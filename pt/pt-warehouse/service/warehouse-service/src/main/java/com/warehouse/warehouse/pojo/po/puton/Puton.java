package com.warehouse.warehouse.pojo.po.puton;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (Puton)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "puton")
public class Puton implements Serializable {

    private static final long serialVersionUID = -99286720432470738L;

    @Id
    private String id;

    /**
     * 上架单号
     */
    @Column(name = "puton_no")
    private String putonNo;

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
     * 货位id
     */
    @Column(name = "locator_id")
    private String locatorId;

    /**
     * 货位编码
     */
    @Column(name = "locator_code")
    private String locatorCode;

    /**
     * 货位名称
     */
    @Column(name = "locator_name")
    private String locatorName;

    /**
     * 原货位id
     */
    @Column(name = "oldlocator_id")
    private String oldlocatorId;

    /**
     * 原货位编码
     */
    @Column(name = "oldlocator_code")
    private String oldlocatorCode;

    /**
     * 原货位名称
     */
    @Column(name = "oldlocator_name")
    private String oldlocatorName;

    /**
     * 数量
     */
    @Column(name = "qty")
    private Integer qty;

    /**
     * 状态（1：待审核，2：已审核）
     */
    @Column(name = "state")
    private String state;

    /**
     * 当前仓库id
     */
    @Column(name = "warehouse_id")
    private String warehouseId;

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

    /**
     * 审核人id
     */
    @Column(name = "audit_id")
    private String auditId;

    /**
     * 审核人
     */
    @Column(name = "auditor")
    private String auditor;

    /**
     * 审核时间
     */
    @Column(name = "audit_time")
    private Date auditTime;


}