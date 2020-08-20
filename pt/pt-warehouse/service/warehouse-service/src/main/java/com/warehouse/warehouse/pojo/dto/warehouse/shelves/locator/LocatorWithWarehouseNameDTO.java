package com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Copyright (C), 2020-2020, si-tech
 * FileName: LocatorWithWarehouseNameDTO
 *
 * @Author: wanpp
 * @Date: 2020/5/10 14:49
 * Description:
 * Version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocatorWithWarehouseNameDTO implements Serializable {

    private static final long serialVersionUID = 3596189923719193129L;

    private String id;

    /**
     * 货位名称
     */
    private String name;

    /**
     * 货位编码
     */
    private String code;

    /**
     * 长
     */
    private Double length;

    /**
     * 宽
     */
    private Double width;

    /**
     * 高
     */
    private Double height;

    /**
     * 货位限重
     */
    private Double weight;

    /**
     * 库位类型（1：普通货位，2：入库区，3：大货区，4散货区，5：残次品,6：退货区，7：出库区）
     */
    private String type;

    /**
     * 货位状态（0：正常，1：失效）
     */
    private String state;

    /**
     * 所属仓库id
     */
    private String warehouseId;

    /**
     * 仓库名称
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
     * 货架ID
     */
    private String shelvesId;

    /**
     * 货架名称
     */
    private String shelvesName;


    /**
     * 排序字段
     */
    private String orderBy;
}