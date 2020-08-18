package com.warehouse.warehouse.pojo.po.receive;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 接货表(Receive)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "receive")
public class Receive implements Serializable {

    private static final long serialVersionUID = 497673722576500312L;

    @Id
    private String id;

    /**
     * 接货单号
     */
    @Column(name = "receive_no")
    private String receiveNo;

    /**
     * 采购单号id
     */
    @Column(name = "purchase_id")
    private String purchaseId;

    /**
     * 采购单号
     */
    @Column(name = "purchase_no")
    private String purchaseNo;

    /**
     * 调拨id
     */
    @Column(name = "allocation_id")
    private String allocationId;

    /**
     * 调拨单号
     */
    @Column(name = "allocation_no")
    private String allocationNo;

    /**
     * 状态
     */
    @Column(name = "state")
    private String state;

    /**
     * 备注
     */
    @Column(name = "remarks")
    private String remarks;

    /**
     * 所属仓库id
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