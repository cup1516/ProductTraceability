package com.warehouse.warehouse.pojo.dto.warehouse.shelves;

import com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator.LocatorCaseCadeDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

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
public class ShelvesAndLocatorDTO {


    /**
     * 货架id
     */
    private String value;

    /**
     * 货架名称
     */
    private String label;

    /**
     * 货架
     */
    private ShelvesDTO shelvesDTO;

    /**
     * 货位
     */
    private List<LocatorCaseCadeDTO> children;
   //商品
    private String map1;
    //货位名称
    private  String map2;
    private String companyId;

}
