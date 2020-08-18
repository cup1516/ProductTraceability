package com.warehouse.warehouse.pojo.po.dispatch;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 报损子表(DispatchDetail)实体类
 *
 * @author makejava
 * @since 2020-05-01 19:48:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "dispatch_detail")
public class DispatchDetail implements Serializable {

    private static final long serialVersionUID = 720746159427695235L;

    @Id
    private String id;

    /**
     * 报损主表id
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

    /**
     * 入库单客户id
     */
    @Column(name = "otherin_customer_id")
    private String otherinCustomerId;

    /**
     * 入库单id
     */
    @Column(name = "otherin_id")
    private String otherinId;


    @Column(name = "company_id")
    private String companyId;
}