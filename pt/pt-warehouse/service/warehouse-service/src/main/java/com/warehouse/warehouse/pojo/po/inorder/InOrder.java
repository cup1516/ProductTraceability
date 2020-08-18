package com.warehouse.warehouse.pojo.po.inorder;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (InOrder)实体类
 *
 * @author makejava
 * @since 2020-05-10 19:10:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "in_order")
public class InOrder implements Serializable {

    private static final long serialVersionUID = 967120401950851611L;

    @Id
    private String id;

    @Column(name = "otherin_no")
    private String otherinNo;

    @Column(name = "pid")
    private String pid;

    @Column(name = "company_no")
    private String companyNo;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "state")
    private Integer state;

    @Column(name = "remark")
    private String remark;

    @Column(name = "warehouse_id")
    private String warehouseId;

    @Column(name = "warehouse_name")
    private String warehouseName;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "create_id")
    private String createId;

    @Column(name = "creator")
    private String creator;

    @Column(name = "supplier_id")
    private String supplierId;

    @Column(name = "supplier_name")
    private String supplierName;

    @Column(name = "good_no")
    private String goodNo;

    @Column(name = "good_price")
    private Double goodPrice;

    @Column(name = "tot_price")
    private Double totPrice;

    @Column(name = "good_num")
    private Integer goodNum;

    @Column(name = "sup_company")
    private String supCompany;

    @Column(name = "company_id")
    private String companyId;

    @Column(name = "batch")
    private String batch;

}