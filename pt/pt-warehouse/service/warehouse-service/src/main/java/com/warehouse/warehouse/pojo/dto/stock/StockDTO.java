package com.warehouse.warehouse.pojo.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 仓储表(Stock)实体类
 *
 * @author makejava
 * @since 2020-04-12 20:36:32
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO implements Serializable {
    
    private static final long serialVersionUID = -54410797377108608L;

    
    private String id;

    /**
     * 数据来源单号
     */
    private String billNo;

    /**
     * 货位id
     */
    private String locatorId;

    /**
     * 货位
     */
    private String locatorName;

    /**
     * 货位编码
     */
    private String locatorCode;

    /**
     * 商品id
     */
    private String commodityId;

    /**
     * 商品编码
     */
    private String commodityBar;

    /**
     * 商品名称
     */
    private String commodityName;

    /**
     * 总数量
     */
    private Integer cqty;

    /**
     * 可用数量
     */
    private Integer uqty;

    /**
     * 锁定数量
     */
    private Integer lqty;

    /**
     * 库位状态 0 正常  1锁定
     */
    private String locatorState;

    /**
     * 仓库id
     */
    private String warehouseId;

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
    private Date updateTime;

    /**
     * 货架id
     */
    private String shelvesId;

    /**
     * 货架名称
     */
    private String shelvesName;


    /**
     * 排序字段
     */
    private String orderBy;

    private String companyId;
    private String batch;
}