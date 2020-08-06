package com.pt.ptdealerprod.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
    * 产品类型表
    */
@ApiModel(value="com-pt-ptdealerprod-entity-ProdType")
@Data
public class ProdType {
    /**
    * 主键
    */
    @ApiModelProperty(value="主键")
    private String typeId;
    /**
     * 公司id
     */
    @ApiModelProperty(value="公司id")
    private String companyId;
    /**
    * 产品类型编号
    */
    @ApiModelProperty(value="产品类型编号")
    private String typeCode;

    /**
    * 产品类型名称
    */
    @ApiModelProperty(value="产品类型名称")
    private String typeName;

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