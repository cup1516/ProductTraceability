package com.warehouse.warehouse.pojo.po.warehouse.shelves.locator;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 货位表(Locator)实体类
 *
 * @author makejava
 * @since 2020-04-16 08:59:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "locator")
public class Locator implements Serializable {

    private static final long serialVersionUID = -83971201999787562L;

    @Id
    private String id;

    /**
     * 货位名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 货位编码
     */
    @Column(name = "code")
    private String code;

    /**
     * 长
     */
    @Column(name = "length")
    private Double length;

    /**
     * 宽
     */
    @Column(name = "width")
    private Double width;

    /**
     * 高
     */
    @Column(name = "height")
    private Double height;

    /**
     * 货位限重
     */
    @Column(name = "weight")
    private Double weight;

    /**
     * 库位类型（1：普通货位，2：入库区，3：大货区，4散货区，5：残次品,6：退货区，7：出库区）
     */
    @Column(name = "type")
    private String type;

    /**
     * 货位状态（0：空闲，未使用，1：已使用，未满，2：已满）
     */
    @Column(name = "state")
    private String state;

    /**
     * 所属仓库id
     */
    @Column(name = "warehouse_id")
    private String warehouseId;

    /**
     * 货架ID
     */
    @Column(name = "shelves_id")
    private String shelvesId;

    /**
     * 货架名称
     */
    @Column(name = "shelves_name")
    private String shelvesName;

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

}