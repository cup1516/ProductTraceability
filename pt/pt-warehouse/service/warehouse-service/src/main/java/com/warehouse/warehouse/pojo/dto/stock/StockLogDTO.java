package com.warehouse.warehouse.pojo.dto.stock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 仓储日志表(StockLog)实体类
 *
 * @author makejava
 * @since 2020-04-12 20:36:32
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockLogDTO implements Serializable {
    
    private static final long serialVersionUID = -17022749812739263L;

    
    private String id;

    /**
     * 数据来源单号
     */
    private String billNo;

    /**
     * 仓库id
     */
    private String warehouseId;

    /**
     * 货位id
     */
    private String locatorId;

    /**
     * 货位编码
     */
    private String locatorCode;

    /**
     * 货位名称
     */
    private String locatorName;

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
     * 数量
     */
    private Integer qty;

    /**
     * 状态（0：正常，1.失效）
     */
    private String state;

    /**
     * 类型（1：入库，2：上架，3：锁定，：下架，5：出库）
     */
    private String type;

    /**
     * 创建者id
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
     * 修改者id
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
}