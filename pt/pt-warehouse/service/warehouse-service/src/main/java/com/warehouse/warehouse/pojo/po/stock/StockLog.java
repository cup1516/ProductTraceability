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
 * 仓储日志表(StockLog)实体类
 *
 * @author makejava
 * @since 2020-04-12 20:36:32
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "stock_log")
public class StockLog implements Serializable {

    private static final long serialVersionUID = -78090569711097234L;

    @Id
    private String id;

    /**
     * 数据来源单号
     */
    @Column(name = "bill_no")
    private String billNo;

    /**
     * 仓库id
     */
    @Column(name = "warehouse_id")
    private String warehouseId;

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
     * 状态（0：正常，1.失效）
     */
    @Column(name = "state")
    private String state;

    /**
     * 类型（1：入库，2：上架，3：锁定，：下架，5：出库）
     */
    @Column(name = "type")
    private String type;

    /**
     * 创建者id
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
     * 修改者id
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

}