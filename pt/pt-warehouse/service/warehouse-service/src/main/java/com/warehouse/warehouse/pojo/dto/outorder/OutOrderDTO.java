package com.warehouse.warehouse.pojo.dto.outorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (OutOrder)实体类
 *
 * @author makejava
 * @since 2020-05-10 19:12:25
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutOrderDTO implements Serializable {
    
    private static final long serialVersionUID = -94216474950943440L;

    
    private String id;

    
    private String otheroutNo;

    
    private String pid;

    
    private String companyNo;

    
    private String companyName;

    
    private String remark;

    
    private String warehouseId;

    
    private String warehouseName;

    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date createTime;

    
    private String createId;

    
    private String creator;

    
    private String customerId;

    private String customerName;

    
    private Integer state;

    private String goodNo;


    private double goodPrice;


    private double totPrice;

    private String cusCompany;


    private String supplierId;

    private int goodNum;


    /**
     * 排序字段
     */
    private String orderBy;

    private String companyId;

    private String batch;
}