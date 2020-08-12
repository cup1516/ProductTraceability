package com.pt.ptmanor.pojo.painting;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "batch")
@ApiModel(value = "种植批次")
public class Batch {

    @Id
    @Column(name = "id")
    @ApiModelProperty(value="主键")
    private String id;

    @Column
    @ApiModelProperty(value="逻辑删除")
    private Integer isDeleted;

    @Column
    @ApiModelProperty(value="公司ID")
    private String companyId;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
