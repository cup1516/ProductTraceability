package com.warehouse.warehouse.pojo.po.outorder;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 出库收据表子表(OutorderDetail)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "outorder_detail")
public class OutorderDetail implements Serializable {

    private static final long serialVersionUID = -16012576377028924L;

    @Id
    private String id;

    /**
     * 出库收据表id
     */
    @Column(name = "pid")
    private String pid;

    /**
     * 库存日志表id
     */
    @Column(name = "stock_log_id")
    private String stockLogId;

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
     * 商品名
     */
    @Column(name = "commodity_name")
    private String commodityName;

    /**
     * 数量
     */
    @Column(name = "qty")
    private Integer qty;

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

}