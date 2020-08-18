package com.warehouse.warehouse.pojo.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodCaseCadeDTO {
    /**
     * 商品条例id
     */
    private String value;

    /**
     * 货位名称
     */
    private String label;
    /*
     商品编号
     */
    private String code;

    /**
     * 货位实体
     */
    private GoodDTO goodDTO;

    private String companyId;
}
