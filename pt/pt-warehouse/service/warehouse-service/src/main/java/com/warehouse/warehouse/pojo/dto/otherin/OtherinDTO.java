package com.warehouse.warehouse.pojo.dto.otherin;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 入库表(Otherin)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtherinDTO implements Serializable {
    
    private static final long serialVersionUID = -44909930297389835L;

    
    private String id;

    /**
     * 入库单号
     */
    private String otherinNo;

    /**
     * 供应商id
     */
    private String supplierId;

    private String supplierName;
    /**
     * 所属仓库id
     */
    private String warehouseId;

    /**
     * 状态 0未审核 1已审核 2 已入库 3未入库
     */
    private String state;

    /**
     * 备注
     */
    private String remark;

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