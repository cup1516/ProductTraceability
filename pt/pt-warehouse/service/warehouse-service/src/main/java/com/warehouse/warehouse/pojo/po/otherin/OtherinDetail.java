package com.warehouse.warehouse.pojo.po.otherin;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 入库子表(OtherinDetail)实体类
 *
 * @author makejava
 * @since 2020-04-12 17:02:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "otherin_detail")
public class OtherinDetail implements Serializable {

    private static final long serialVersionUID = 410406308456957841L;

    @Id
    private String id;

    /**
     * 入库主表id
     */
    @Column(name = "pid")
    private String pid;

    /**
     * 货位id
     */
    @Column(name = "locator_id")
    private String locatorId;

    /**
     * 货位编码
     */
    @Column(name = "locator_code")
    private String locatorCode;

    /**
     * 货位名称
     */
    @Column(name = "locator_name")
    private String locatorName;

    /**
     * 商品id
     */
    @Column(name = "commodity_id")
    private String commodityId;

    /**
     * 商品编码
     */
    @Column(name = "commodity_bar")
    private String commodityBar;

    /**
     * 商品名称
     */
    @Column(name = "commodity_name")
    private String commodityName;

    /**
     * 数量
     */
    @Column(name = "qty")
    private Integer qty;

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

    @Column(name = "company_id")
    private String companyId;


    @Column(name = "batch")
    private String batch;

    @Column(name = "incompany")
    private String incompany;
}