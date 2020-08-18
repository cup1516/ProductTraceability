package com.warehouse.warehouse.pojo.po.otherout;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 出库子表(OtheroutDetail)实体类
 *
 * @author makejava
 * @since 2020-04-18 16:29:04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "otherout_detail")
public class OtheroutDetail implements Serializable {

    private static final long serialVersionUID = 843904316719568152L;

    @Id
    private String id;

    /**
     * 出库主表id
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

    /**
     * 仓储表数据id
     */
    @Column(name = "stock_id")
    private String stockId;

    @Column(name = "company_id")
    private String companyId;

    @Column(name = "batch")
    private String batch;

    @Column(name = "outcompany")
    private String outcompany;
}