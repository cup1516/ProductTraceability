package com.pt.ptmanor.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "system_order")
public class SystemOrder {

    @Id
    @Column(name = "orderId")
    private String orderId;

    @Column
    private String sellerId;

    @Column
    private String sellerName;

    @Column
    private String buyerId;

    @Column
    private String buyerName;

    @Column
    private String productId;

    @Column
    private String productName;

    @Column
    private Float productAmount;

    @Column
    private Float productPrice;

    @Column
    private Float productTotal;

    @Column
    private String creatorId;

    @Column
    private String creatorName;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    @Column
    private String reviewerId;

    @Column
    private String reviewerName;

    @Column
    private String reviewerSuggestions;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date reviewTime;

    @Column
    private String checkerId;

    @Column
    private String checkerName;

    @Column
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date checkTime;

    @Column
    private String checkerSuggestions;

    @Column
    private String nextId;

    @Column
    private String previousId;

    @Column
    private String checkFlag;

    @Column
    private String delFlag;

    @Column
    private String checkStatus;

    @Column
    private String productBatch;

    public String getProductBatch() {
        return productBatch;
    }

    public void setProductBatch(String productBatch) {
        this.productBatch = productBatch;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
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

    public Float getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Float productAmount) {
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

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewerSuggestions() {
        return reviewerSuggestions;
    }

    public void setReviewerSuggestions(String reviewerSuggestions) {
        this.reviewerSuggestions = reviewerSuggestions;
    }

    public Date getReviewerTime() {
        return reviewTime;
    }

    public void setReviewerTime(Date reviewerTime) {
        this.reviewTime = reviewerTime;
    }

    public String getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(String checkerId) {
        this.checkerId = checkerId;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public String getCheckerSuggestions() {
        return checkerSuggestions;
    }

    public void setCheckerSuggestions(String checkerSuggestions) {
        this.checkerSuggestions = checkerSuggestions;
    }

    public String getNextId() {
        return nextId;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    public String getPreviousId() {
        return previousId;
    }

    public void setPreviousId(String previousId) {
        this.previousId = previousId;
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    public String getDelflag() {
        return delFlag;
    }

    public void setDelflag(String delflag) {
        this.delFlag = delflag;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }
}
