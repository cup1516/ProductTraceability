package com.pt.ptdealerorder.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value="com-pt-ptdealerorder-entity-SystemOrder")
@Data
@NoArgsConstructor
public class SystemOrder {
    /**
    * 订单id
    */
    @ApiModelProperty(value="订单id")
    private String orderId;

    /**
    * 卖方id
    */
    @ApiModelProperty(value="卖方id")
    private String sellerId;

    /**
    * 卖方名称
    */
    @ApiModelProperty(value="卖方名称")
    private String sellerName;

    /**
    * productBatch
    */
    @ApiModelProperty(value="买方id")
    private String buyerId;

    /**
    * 买方名称
    */
    @ApiModelProperty(value="买方名称")
    private String buyerName;

    /**
    * 产品id
    */
    @ApiModelProperty(value="产品id")
    private String productId;

    /**
     * 产品批次
     */
    @ApiModelProperty(value="产品批次")
    private String productBatch;

    /**
    * 产品名称
    */
    @ApiModelProperty(value="产品名称")
    private String productName;

    /**
    * 产品数量
    */
    @ApiModelProperty(value="产品数量")
    private Float productAmount;

    /**
    * 产品单价
    */
    @ApiModelProperty(value="产品单价")
    private Float productPrice;

    /**
    * 产品总价
    */
    @ApiModelProperty(value="产品总价")
    private Float productTotal;

    /**
    * 创建者id
    */
    @ApiModelProperty(value="创建者id")
    private String creatorId;

    /**
    * 创建者姓名
    */
    @ApiModelProperty(value="创建者姓名")
    private String creatorName;

    /**
    * 创建时间
    */
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
    * 审核人id
    */
    @ApiModelProperty(value="审核人id")
    private String reviewerId;

    /**
    * 审核人姓名
    */
    @ApiModelProperty(value="审核人姓名")
    private String reviewerName;

    /**
    * 审核意见
    */
    @ApiModelProperty(value="审核意见")
    private String reviewerSuggestions;

    /**
    * 审核时间
    */
    @ApiModelProperty(value="审核时间")
    private Date reviewTime;

    /**
    * 确认人id
    */
    @ApiModelProperty(value="确认人id")
    private String checkerId;

    /**
    * 确认人姓名
    */
    @ApiModelProperty(value="确认人姓名")
    private String checkerName;

    /**
    * 确认意见
    */
    @ApiModelProperty(value="确认意见")
    private String checkerSuggestions;

    /**
    * 确认时间
    */
    @ApiModelProperty(value="确认时间")
    private Date checkTime;

    /**
    * 后继单据id
    */
    @ApiModelProperty(value="后继单据id")
    private String nextId;

    /**
    * 前驱单据id
    */
    @ApiModelProperty(value="前驱单据id")
    private String previousId;

    /**
    * 0-未送审 1-未审批 2-通过 3-驳回
    */
    @ApiModelProperty(value="0-未送审 1-未审批 2-通过 3-驳回")
    private String checkFlag;

    /**
    * 0-未删除 1-已删除
    */
    @ApiModelProperty(value="0-未删除 1-已删除")
    private String delFlag;

    /**
    * 0-未发送 1-未确认 2-已确认 3-驳回
    */
    @ApiModelProperty(value="0-未发送 1-未确认 2-已确认 3-驳回")
    private String checkStatus;
}
