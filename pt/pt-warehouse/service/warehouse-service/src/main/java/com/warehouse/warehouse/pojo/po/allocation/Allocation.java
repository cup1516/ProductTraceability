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
 * 调拨单(Allocation)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "allocation")
public class Allocation implements Serializable {

    private static final long serialVersionUID = 356163981328152149L;

    /**
     * 编号
     */
    @Id
    private String id;

    /**
     * 调拨单号
     */
    @Column(name = "allocation_no")
    private String allocationNo;

    /**
     * 原仓库id
     */
    @Column(name = "bwarehouse_id")
    private String bwarehouseId;

    /**
     * 原仓库名称
     */
    @Column(name = "bwarehouse_name")
    private String bwarehouseName;

    /**
     * 目标仓库id
     */
    @Column(name = "awarehouse_id")
    private String awarehouseId;

    /**
     * 目标仓库名称
     */
    @Column(name = "awarehouse_name")
    private String awarehouseName;

    /**
     * 状态 0初始 1待审核 2已审核
     */
    @Column(name = "state")
    private String state;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

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

    @Column(name = "company_id")
    private String companyId;
}