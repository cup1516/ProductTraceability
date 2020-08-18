package com.warehouse.warehouse.pojo.po.supplier;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 供应商表(Supplier)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:28:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "supplier")
public class Supplier implements Serializable {

    private static final long serialVersionUID = 907571631654127840L;

    /**
     * 编号
     */
    @Id
    private String id;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 联系人
     */
    @Column(name = "contact")
    private String contact;

    /**
     * 电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 所属仓库id
     */
    @Column(name = "warehouse_id")
    private String warehouseId;

    /**
     * 创建人id
     */
    @Column(name = "create_id")
    private String createId;


    @Column(name = "company_name")
    private String companyName;
    /**
     * 创建人
     */
    @Column(name = "creator")
    private String creator;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改人id
     */
    @Column(name = "update_id")
    private String updateId;

    /**
     * 修改人
     */
    @Column(name = "updator")
    private String updator;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "company_id")
    private String companyId;

}