package com.pt.ptmanor.pojo.product;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "invoice")
public class Invoice {

    @Column
    private String companyId;

    @Id
    @NotFound(action= NotFoundAction.IGNORE)
    private String orderId;


    //卖方ID
    @Column(name = "seller_number")
    private String sellerNumber;

    //卖方公司名
    @Column
    private  String sellerName;

    //卖方维护的ID
    @Column
    private String sellerId;

    //卖方的发票创建者用户名
    @Column
    private String sellerCreator;

    //买方维护的ID
    @Column
    private  String buyerId;

    //买方的公司名
    @Column
    private String buyerName;

    //买方的公司ID
    @Column
    private String buyerNumber;

    //产品ID
    @Column
    private String productId;

    //产品名
    @Column
    private String productName;

    //产品数量（kg）
    @Column
    private Integer productAmount;

    //产品单价（元）
    @Column
    private Float productPrice;

    //产品总价（元）
    @Column
    private Float productTotal;

    @Column
    /*
    * 0代表庄园-经销商
    * */
    private Integer orderType;

    //1代表删除
    @Column
    private Integer isDeleted;

    //创建时间
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    //修改时间
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date orderUpdateTime;

    //发票创建者ID
    @Column
    private String orderCreator;


        /*0:存在
        1：修改过的发票
        2:已作废
        * */
    @Column
    private Integer status;

    @Column
    private  String beforeInvoiceId;

    //审查者ID
    @Column
    private String reviewerId;

    /*
        0:未审批
        1：已审批
        2：审批通过
        3：审批未通过
        4：审核通过（修改后）
        * */
    @Column
    private Integer checkFlag;

    //创建者
    @Column
    private  String creatorName;

    //审查者的用户名
    @Column
    private String reviewerName;

    //审查时间
    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date checkTime;

    //修改前的id
    @Column
    private String previousId;



    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getOrderCreator() {
        return orderCreator;
    }

    public void setOrderCreator(String orderCreator) {
        this.orderCreator = orderCreator;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public Integer getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(Integer checkFlag) {
        this.checkFlag = checkFlag;
    }
    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }
    public String getPreviousId() {
        return previousId;
    }

    public void setPreviousId(String previousId) {
        this.previousId = previousId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBeforeInvoiceId() {
        return beforeInvoiceId;
    }

    public void setBeforeInvoiceId(String beforeInvoiceId) {
        this.beforeInvoiceId = beforeInvoiceId;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public Integer getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Integer productAmount) {
        this.productAmount = productAmount;
    }

    public Float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Float productPrice) {
        this.productPrice = productPrice;
    }

    public Float getProductTotal() {
        return productTotal;
    }

    public void setProductTotal(Float productTotal) {
        this.productTotal = productTotal;
    }

    public String getSellerNumber() {
        return sellerNumber;
    }

    public void setSellerNumber(String sellerNumber) {
        this.sellerNumber = sellerNumber;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerCreator() {
        return sellerCreator;
    }

    public void setSellerCreator(String sellerCreator) {
        this.sellerCreator = sellerCreator;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerNumber() {
        return buyerNumber;
    }

    public void setBuyerNumber(String buyerNumber) {
        this.buyerNumber = buyerNumber;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getOrderUpdateTime() {
        return orderUpdateTime;
    }

    public void setOrderUpdateTime(Date orderUpdateTime) {
        this.orderUpdateTime = orderUpdateTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }



}
