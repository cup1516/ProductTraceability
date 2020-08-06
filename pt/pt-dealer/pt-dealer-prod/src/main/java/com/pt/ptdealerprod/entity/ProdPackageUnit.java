package com.pt.ptdealerprod.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value="com-pt-ptdealerprod-entity-ProdPackageUnit")
@Data
public class ProdPackageUnit {
    @ApiModelProperty(value="")
    private String packageId;
    /**
     * 公司id
     */
    @ApiModelProperty(value="公司id")
    private String companyId;
    @ApiModelProperty(value="")
    private String unitId;

    @ApiModelProperty(value="")
    private String sort;

    @ApiModelProperty(value="")
    private String number;
}