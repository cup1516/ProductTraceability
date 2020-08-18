package com.warehouse.warehouse.pojo.dto.warehouse.shelves.locator;

import com.warehouse.warehouse.pojo.dto.stock.StockDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocatorAndGoodsDTO {
    /**
     * 货位实体
     */
    private LocatorDTO locatorDTO;

    private StockDTO stockDTO;
}