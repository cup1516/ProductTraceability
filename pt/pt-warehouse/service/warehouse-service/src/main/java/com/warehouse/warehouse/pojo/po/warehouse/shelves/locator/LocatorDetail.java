package com.warehouse.warehouse.pojo.po.warehouse.shelves.locator;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
@Table(name = "locator_detail")
public class LocatorDetail implements Serializable {

    private static final long serialVersionUID = -16065192785110109L;

    @Id
    private String id;

    @Column(name = "locator_id")
    private String locatorId;

    @Column(name = "locator_name")
    private String locatorName;

    @Column(name = "locator_code")
    private String locatorCode;

    @Column(name = "length")
    private Double length;

    @Column(name = "width")
    private Double width;

    @Column(name = "height")
    private Double height;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "creator")
    private String creator;

    @Column(name = "create_id")
    private String createId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_id")
    private String updateId;

    @Column(name = "updator")
    private String updator;

    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "capacity")
    private Double capacity;

    @Column(name = "charge")
    private Double charge;

    @Column(name = "type")
    private String type;

    @Column(name = "state")
    private String state;

    @Column(name = "warehouse_name")
    private String warehouseName;

    @Column(name = "warehouse_id")
    private String warehouseId;

    @Column(name = "shelf_name")
    private String shelfName;

    @Column(name = "shelf_id")
    private String shelfId;

    @Column(name = "good_name")
    private String goodName;

    @Column(name = "good_no")
    private String goodNo;

    @Column(name = "good_num")
    private Integer goodNum;

    @Column(name = "good_price")
    private Double goodPrice;


    @Column(name = "company_id")
    private Double companyId;

}