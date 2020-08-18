package com.warehouse.warehouse.pojo.po.stock;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 仓储表(Stock)实体类
 *
 * @author makejava
 * @since 2020-04-12 20:36:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock")
public class Stock implements Serializable {

    private static final long serialVersionUID = 132445524570979083L;

    @Id
    private String id;

    /**
     * 数据来源单号
     */
    @Column(name = "bill_no")
    private String billNo;

    /**
     * 货位id
     */
    @Column(name = "locator_id")
    private String locatorId;

    /**
     * 货位
     */
    @Column(name = "locator_name")
    private String locatorName;

    /**
     * 货位编码
     */
    @Column(name = "locator_code")
    private String locatorCode;

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
     * 总数量
     */
    @Column(name = "cqty")
    private Integer cqty;

    /**
     * 可用数量
     */
    @Column(name = "uqty")
    private Integer uqty;

    /**
     * 锁定数量
     */
    @Column(name = "lqty")
    private Integer lqty;

    /**
     * 库位状态 0 正常  1锁定
     */
    @Column(name = "locator_state")
    private String locatorState;

    /**
     * 仓库id
     */
    @Column(name = "warehouse_id")
    private String warehouseId;

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
     * 货架id
     */
    @Column(name = "shelves_id")
    private String shelvesId;

    /**
     * 货架名称
     */
    @Column(name = "shelves_name")
    private String shelvesName;

    @Column(name = "company_id")
    private String companyId;


    @Column(name = "batch")
    private String batch;

}