package com.warehouse.warehouse.pojo.dto.dispatch;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 报损子表(DispatchDetail)实体类
 *
 * @author makejava
 * @since 2020-05-01 19:49:56
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispatchDetailDTO implements Serializable {
    
    private static final long serialVersionUID = 750141420866597522L;

    
    private String id;

    /**
     * 报损主表id
     */
    private String pid;

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
     * 数量
     */
    private Integer qty;

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
     * 仓储表数据id
     */
    private String stockId;

    /**
     * 入库单客户id
     */
    private String otherinCustomerId;

    /**
     * 入库单id
     */
    private String otherinId;


    /**
     * 排序字段
     */
    private String orderBy;

    private String companyId;
}