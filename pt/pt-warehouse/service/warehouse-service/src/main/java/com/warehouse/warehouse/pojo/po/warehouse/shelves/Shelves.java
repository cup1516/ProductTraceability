package com.warehouse.warehouse.pojo.po.warehouse.shelves;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 货架表(Shelves)实体类
 *
 * @author makejava
 * @since 2020-04-12 20:32:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shelves")
public class Shelves implements Serializable {

    private static final long serialVersionUID = 613875152655743525L;

    /**
     * 货架Id
     */
    @Id
    private String id;

    /**
     * 货架名称
     */
    @Column(name = "shelves_name")
    private String shelvesName;

    /**
     * 所属仓库id
     */
    @Column(name = "warehouse_id")
    private String warehouseId;

    /**
     * 所属仓库名称
     */
    @Column(name = "warehouse_name")
    private String warehouseName;

    /**
     * 创建人Id
     */
    @Column(name = "create_user_id")
    private String createUserId;

    /**
     * 创建人名称
     */
    @Column(name = "create_user_name")
    private String createUserName;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新人Id
     */
    @Column(name = "update_user_id")
    private String updateUserId;

    /**
     * 更新人名称
     */
    @Column(name = "update_user_name")
    private String updateUserName;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 状态值，默认为0
     */
    @Column(name = "status")
    private Integer status;

    @Column(name = "company_id")
    private String companyId;

}