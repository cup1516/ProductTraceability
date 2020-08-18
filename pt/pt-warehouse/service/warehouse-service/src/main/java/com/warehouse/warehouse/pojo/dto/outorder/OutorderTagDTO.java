package com.warehouse.warehouse.pojo.dto.outorder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (OutorderTag)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutorderTagDTO implements Serializable {
    
    private static final long serialVersionUID = -36335483593730016L;

    
    private String id;

    /**
     * 主表id
     */
    private String pid;

    /**
     * 出库订单编号
     */
    private String outorderNo;

    /**
     * 当前仓库id
     */
    private String warehouseId;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String county;

    /**
     * 出库订单类型（1：单品单件，2：单品多件，3：多品单件，4：多品多件）
     */
    private String type;


    /**
     * 排序字段
     */
    private String orderBy;
    private String companyId;
}