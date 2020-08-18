package com.warehouse.warehouse.pojo.po.purchase;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 采购表(Purchase)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "purchase")
public class Purchase implements Serializable {

    private static final long serialVersionUID = 641085978877364521L;

    /**
     * 编号
     */
    @Id
    private String id;

    /**
     * 采购订单编号
     */
    @Column(name = "purchase_no")
    private String purchaseNo;

    /**
     * 供应商id
     */
    @Column(name = "supplier_id")
    private String supplierId;

    /**
     * 供应商名称
     */
    @Column(name = "supplier_name")
    private String supplierName;

    /**
     * 商品总数量
     */
    @Column(name = "commodity_num")
    private Integer commodityNum;

    /**
     * 预计到货时间
     */
    @Column(name = "estimate_arrival_time")
    private Date estimateArrivalTime;

    /**
     * 仓库id
     */
    @Column(name = "warehouse_id")
    private String warehouseId;

    /**
     * 仓库名称
     */
    @Column(name = "warehouse_name")
    private String warehouseName;

    /**
     * 状态(0未审核，1待审核，2已审核)
     */
    @Column(name = "state")
    private String state;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

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