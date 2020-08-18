package com.warehouse.warehouse.pojo.dto.otherin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 入库子表(OtherinDetail)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtherinDetailDTO implements Serializable {
    
    private static final long serialVersionUID = -45882959692981932L;

    
    private String id;

    /**
     * 入库主表id
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
    private Date updateTime;


    /**
     * 排序字段
     */
    private String orderBy;

    private String companyId;

    /**
     * 批次号
     */
    private String batch;
    private String incompany;
}