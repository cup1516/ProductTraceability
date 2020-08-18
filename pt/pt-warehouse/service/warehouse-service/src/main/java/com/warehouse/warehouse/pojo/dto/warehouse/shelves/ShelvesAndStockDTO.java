package com.warehouse.warehouse.pojo.dto.warehouse.shelves;

import com.warehouse.warehouse.pojo.dto.stock.StockCaseCadeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Copyright (C), 2020-2020, si-tech
 * FileName: ShelvesAndLocationDTO
 *
 * @Author: wanpp
 * @Date: 2020/4/14 9:03
 * Description:
 * Version: 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelvesAndStockDTO {

    /**
     * 货架id
     */
    private String value;

    /**
     * 货架名称
     */
    private String label;

    /**
     * 货架信息
     */
    private ShelvesDTO shelvesDTO;

    /**
     * 货位
     */
    private List<StockCaseCadeDTO> children;

    private String companyId;

}
