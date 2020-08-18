package com.warehouse.warehouse.pojo.po.warehouse;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 仓库表(Warehouse)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:28:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "warehouse")
public class Warehouse implements Serializable {

    private static final long serialVersionUID = 813472750482359996L;

    /**
     * 仓库编号
     */
    @Id
    private String id;

    /**
     * 仓库名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 仓库简称
     */
    @Column(name = "alias")
    private String alias;

    /**
     * 仓库地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 创建人id
     */
    @Column(name = "create_id")
    private String createId;

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

    /**
     * 负责人Id
     */
    @Column(name = "principal_id")
    private String principalId;

    /**
     * 负责人名称
     */
    @Column(name = "principal_name")
    private String principalName;


    @Column(name = "company_id")
    private String companyId;

}