package com.warehouse.warehouse.pojo.po.review;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (Review)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:28:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "review")
public class Review implements Serializable {

    private static final long serialVersionUID = -81219028661558807L;

    @Id
    private String id;

    /**
     * 出库订单id
     */
    @Column(name = "outorder_id")
    private String outorderId;

    /**
     * 出库订单编号
     */
    @Column(name = "outorder_no")
    private String outorderNo;

    /**
     * 状态（1：初始，2：复核中，3：完成，4：异常）
     */
    @Column(name = "state")
    private String state;

    /**
     * 当前仓库id
     */
    @Column(name = "warehouse_id")
    private String warehouseId;

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

}