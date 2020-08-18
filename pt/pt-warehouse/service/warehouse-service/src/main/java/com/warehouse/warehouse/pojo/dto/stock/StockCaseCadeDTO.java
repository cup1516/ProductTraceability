package com.warehouse.warehouse.pojo.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copyright (C), 2020-2020, si-tech
 * FileName: LocationCaseCadeDTO
 *
 * @Author: wanpp
 * @Date: 2020/4/14 9:05
 * Description:
 * Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockCaseCadeDTO {

    /**
     * 货位id
     */
    private String value;

    /**
     * 货位名称
     */
    private String label;

    /**
     * 货位信息
     */
    private StockDTO stockDTO;

    private String companyId;

}
