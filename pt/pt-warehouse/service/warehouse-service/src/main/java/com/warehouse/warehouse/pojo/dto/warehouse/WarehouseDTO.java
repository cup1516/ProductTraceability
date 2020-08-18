package com.warehouse.warehouse.pojo.dto.warehouse;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 仓库表(Warehouse)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseDTO implements Serializable {
    
    private static final long serialVersionUID = 920274408314261097L;

    /**
     * 仓库编号
     */
    private String id;

    /**
     * 仓库名称
     */
    private String name;

    /**
     * 仓库简称
     */
    private String alias;

    /**
     * 仓库地址
     */
    private String address;

    /**
     * 备注
     */
    private String remark;

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
     * 负责人Id
     */
    private String principalId;

    /**
     * 负责人名称
     */
    private String principalName;


    /**
     * 排序字段
     */
    private String orderBy;


    private String companyId;
}