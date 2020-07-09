package com.pt.ptdealerprod.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
    * 产品单位表
    */
@ApiModel(value="com-pt-ptdealerprod-entity-ProdUnit")
@Data
public class ProdUnit {
    /**
     * 主键
     */
    @ApiModelProperty(value="主键")
    private String unitId;

    /**
     * 产品单位编号
     */
    @ApiModelProperty(value="产品单位编号")
    private String unitCode;

    /**
     * 产品单位名称
     */
    @ApiModelProperty(value="产品单位名称")
    private String unitName;

    /**
     * 状态 0-正常 1-未启用
     */
    @ApiModelProperty(value="状态 0-正常 1-未启用")
    private String status;

    /**
     * 创建者
     */
    @ApiModelProperty(value="创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 删除标志 0-正常 1-已删除
     */
    @ApiModelProperty(value="删除标志 0-正常 1-已删除")
    private String delFlag;
}