package com.warehouse.warehouse.pojo.dto.puton;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Puton)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutonDTO implements Serializable {
    
    private static final long serialVersionUID = 967790083474771111L;

    
    private String id;

    /**
     * 上架单号
     */
    private String putonNo;

    /**
     * 商品id
     */
    private String commodityId;

    /**
     * 商品编码
     */
    private String commodityBar;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 货位id
     */
    private String locatorId;

    /**
     * 货位编码
     */
    private String locatorCode;

    /**
     * 货位名称
     */
    private String locatorName;

    /**
     * 原货位id
     */
    private String oldlocatorId;

    /**
     * 原货位编码
     */
    private String oldlocatorCode;

    /**
     * 原货位名称
     */
    private String oldlocatorName;

    /**
     * 数量
     */
    private Integer qty;

    /**
     * 状态（1：待审核，2：已审核）
     */
    private String state;

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
}