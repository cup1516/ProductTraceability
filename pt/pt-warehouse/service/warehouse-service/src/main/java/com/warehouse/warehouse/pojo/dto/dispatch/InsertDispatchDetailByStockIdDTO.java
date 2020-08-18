package com.warehouse.warehouse.pojo.dto.dispatch;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Copyright (C), 2020-2020, si-tech
 * FileName: InsertDispatchDetailByStockIdDTO
 *
 * @Author: wanpp
 * @Date: 2020/5/1 22:21
 * Description:
 * Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertDispatchDetailByStockIdDTO implements Serializable {

    private static final long serialVersionUID = -8083293313263081694L;

    /**
     * 仓储表id
     */
    private String stockId;

    /**
     * 报损数量
     */
    private Integer num;

    /**
     * 报损主表id
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

}
