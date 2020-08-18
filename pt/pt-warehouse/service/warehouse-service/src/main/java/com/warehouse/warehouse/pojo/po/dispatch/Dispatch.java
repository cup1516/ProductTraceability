package com.warehouse.warehouse.pojo.po.dispatch;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 报损表(Dispatch)实体类
 *
 * @author makejava
 * @since 2020-05-01 19:48:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dispatch")
public class Dispatch implements Serializable {

    private static final long serialVersionUID = -20355080075328110L;

    @Id
    private String id;

    /**
     * 报损单号
     */
    @Column(name = "dispatch_no")
    private String dispatchNo;

    /**
     * 状态（0：未审核，1：已审核，2：已报损，3：未报损，4：审核通过，5：审核不通过）
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