package com.warehouse.warehouse.pojo.dto.otherout;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 出库表(Otherout)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtheroutDTO implements Serializable {
    
    private static final long serialVersionUID = 174171608220398362L;

    
    private String id;

    /**
     * 出库单号
     */
    private String otheroutNo;

    /**
     * 客户id
     */
    private String customerId;

    private String customerName;

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