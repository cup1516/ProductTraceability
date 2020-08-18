package com.warehouse.warehouse.pojo.dto.supplier;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 供应商表(Supplier)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDTO implements Serializable {
    
    private static final long serialVersionUID = -18039411102611202L;

    /**
     * 编号
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 电话
     */
    private String phone;

    /**
     * 备注
     */
    private String remark;

    /**
     * 所属仓库id
     */
    private String warehouseId;

    /**
     * 创建人id
     */
    private String createId;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    /**
     * 修改人id
     */
    private String updateId;

    /**
     * 修改人
     */
    private String updator;

    /**
     * 修改时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;


    /**
     * 排序字段
     */
    private String orderBy;

    private String companyName;


    private String companyId;
}