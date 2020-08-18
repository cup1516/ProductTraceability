package com.warehouse.warehouse.pojo.dto.goods;

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
public class GoodCategoryAndGoodDTO {

    /**
     * 货架id
     */
    private Long value;

    /**
     * 货架名称
     */
    private String label;


    private Long code;

    /**
     * 货架
     */
    private GoodCategoryDTO goodCategoryDTO;

    private String companyId;

    /**
     * 货位
     */
    private List<GoodCaseCadeDTO> children;

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public GoodCategoryDTO getGoodCategoryDTO() {
        return goodCategoryDTO;
    }

    public void setGoodCategoryDTO(GoodCategoryDTO goodCategoryDTO) {
        this.goodCategoryDTO = goodCategoryDTO;
    }

    public List<GoodCaseCadeDTO> getChildren() {
        return children;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }



    public void setChildren(List<GoodCaseCadeDTO> children) {
        this.children = children;
    }
}
