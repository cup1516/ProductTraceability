package com.warehouse.warehouse.pojo.dto.otherout;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C), 2020-2020, si-tech
 * FileName: OtheroutWithWarehouseNameDTO
 *
 * @Author: wanpp
 * @Date: 2020/4/19 12:48
 * Description:
 * Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtheroutWithWarehouseNameDTO implements Serializable {

    private static final long serialVersionUID = -183556077735073604L;

    private String id;

    /**
     * 出库单号
     */
    private String otheroutNo;

    /**
     * 客户id
     */
    private String customerId;

    /**
     * 状态（0：未审核，1：已审核，2：已出库，3：未出库）
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
     * 当前仓库id
     */
    private String warehouseName;

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

    private String companyId;
}
