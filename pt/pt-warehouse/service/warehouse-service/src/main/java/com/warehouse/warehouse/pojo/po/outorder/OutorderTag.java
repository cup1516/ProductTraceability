package com.warehouse.warehouse.pojo.po.outorder;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * (OutorderTag)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "outorder_tag")
public class OutorderTag implements Serializable {

    private static final long serialVersionUID = -58876003037569304L;

    @Id
    private String id;

    /**
     * 主表id
     */
    @Column(name = "pid")
    private String pid;

    /**
     * 出库订单编号
     */
    @Column(name = "outorder_no")
    private String outorderNo;

    /**
     * 当前仓库id
     */
    @Column(name = "warehouse_id")
    private String warehouseId;

    /**
     * 省
     */
    @Column(name = "province")
    private String province;

    /**
     * 市
     */
    @Column(name = "city")
    private String city;

    /**
     * 区
     */
    @Column(name = "county")
    private String county;

    /**
     * 出库订单类型（1：单品单件，2：单品多件，3：多品单件，4：多品多件）
     */
    @Column(name = "type")
    private String type;
}