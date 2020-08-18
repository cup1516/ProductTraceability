package com.warehouse.warehouse.pojo.dto.inorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (InOrder)实体类
 *
 * @author makejava
 * @since 2020-05-10 19:12:20
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InOrderDTO implements Serializable {
    
    private static final long serialVersionUID = -29341367738974697L;

    
    private String id;

    
    private String otherinNo;

    
    private String pid;

    
    private String companyNo;

    
    private String companyName;

    
    private Integer state;

    
    private String remark;

    
    private String warehouseId;

    
    private String warehouseName;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

    
    private String createId;

    
    private String creator;


    private String goodNo;


    private double goodPrice;


    private double totPrice;

    private String supCompany;

    
    private String supplierId;

    private String supplierName;


    private int goodNum;


    /**
     * 排序字段
     */
    private String orderBy;

    private String companyId;

    private  String batch;
}