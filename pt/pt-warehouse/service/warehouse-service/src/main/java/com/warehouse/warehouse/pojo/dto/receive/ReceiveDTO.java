package com.warehouse.warehouse.pojo.dto.receive;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 接货表(Receive)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveDTO implements Serializable {
    
    private static final long serialVersionUID = -35324406252946731L;

    
    private String id;

    /**
     * 接货单号
     */
    private String receiveNo;

    /**
     * 采购单号id
     */
    private String purchaseId;

    /**
     * 采购单号
     */
    private String purchaseNo;

    /**
     * 调拨id
     */
    private String allocationId;

    /**
     * 调拨单号
     */
    private String allocationNo;

    /**
     * 状态
     */
    private String state;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 所属仓库id
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
}