package com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (LocatorDetail)实体类
 *
 * @author makejava
 * @since 2020-06-02 21:20:32
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocatorDetailDTO implements Serializable {

    private static final long serialVersionUID = -82631881833867638L;

    private String id;

    private String locatorId;

    private String locatorName;

    private String locatorCode;

    private Double length;

    private Double width;

    private Double height;

    private Double weight;

    private String creator;

    private String createId;

    private Date createTime;

    private String updateId;

    private String updator;

    private Date updateTime;

    private Double capacity;

    private Double charge;

    private String type;

    private String state;

    private String warehouseName;

    private String warehouseId;

    private String shelfName;

    private String shelfId;

    private String goodName;

    private String goodNo;

    private Integer goodNum;

    private Double goodPrice;

    /**
     * 排序字段
     */
    private String orderBy;

    private String companyId;
}