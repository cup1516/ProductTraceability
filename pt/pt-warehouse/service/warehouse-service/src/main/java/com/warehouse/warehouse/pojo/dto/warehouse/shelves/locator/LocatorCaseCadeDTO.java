package com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator;

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
public class LocatorCaseCadeDTO {

    /**
     * 货位id
     */
    private String value;

    /**
     * 货位名称
     */
    private String label;

    /**
     * 货位实体
     */
    private LocatorDTO locatorDTO;


}
