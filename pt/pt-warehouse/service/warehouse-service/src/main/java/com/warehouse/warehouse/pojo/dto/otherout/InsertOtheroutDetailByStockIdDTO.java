package com.warehouse.warehouse.pojo.dto.otherout;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Copyright (C), 2020-2020, si-tech
 * FileName: InsertOtheroutDetailByStockIdDTO
 *
 * @Author: wanpp
 * @Date: 2020/4/18 15:12
 * Description:
 * Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertOtheroutDetailByStockIdDTO implements Serializable {

    private static final long serialVersionUID = -6404392945171165789L;

    /**
     * 仓储表id
     */
    private String stockId;

    /**
     * 入库数量
     */
    private Integer num;

    /**
     * 出库主表id
     */
    private String pid;

    /**
     * 创建人编号
     */
    private String createId;

    /**
     * 创建人名称
     */
    private String creator;

    /**
     * 修改人编号
     */
    private String updateId;

    /**
     * 修改人人名称
     */
    private String updator;

    /**
     * detailId
     */
    private String detailId;

    private String companyId;

    private String batch;

    private String outcompany;

}
