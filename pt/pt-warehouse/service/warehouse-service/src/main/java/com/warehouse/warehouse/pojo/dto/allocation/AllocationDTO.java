package com.warehouse.warehouse.pojo.dto.allocation;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 调拨单(Allocation)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllocationDTO implements Serializable {
    
    private static final long serialVersionUID = -64089178178209067L;

    /**
     * 编号
     */
    private String id;

    /**
     * 调拨单号
     */
    private String allocationNo;

    /**
     * 原仓库id
     */
    private String bwarehouseId;

    /**
     * 原仓库名称
     */
    private String bwarehouseName;

    /**
     * 目标仓库id
     */
    private String awarehouseId;

    /**
     * 目标仓库名称
     */
    private String awarehouseName;

    /**
     * 状态 0初始 1待审核 2已审核
     */
    private String state;

    /**
     * 备注
     */
    private String remark;

    /**
     * 当前仓库id
     */
    private String warehouseId;

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
     * 审核人id
     */
    private String auditId;

    /**
     * 审核人
     */
    private String auditor;

    /**
     * 审核时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date auditTime;


    /**
     * 排序字段
     */
    private String orderBy;

    private String companyId;
}