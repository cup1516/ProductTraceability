package com.warehouse.warehouse.pojo.dto.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Review)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO implements Serializable {
    
    private static final long serialVersionUID = 774354417155105618L;

    
    private String id;

    /**
     * 出库订单id
     */
    private String outorderId;

    /**
     * 出库订单编号
     */
    private String outorderNo;

    /**
     * 状态（1：初始，2：复核中，3：完成，4：异常）
     */
    private String state;

    /**
     * 当前仓库id
     */
    private String warehouseId;

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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;


    /**
     * 排序字段
     */
    private String orderBy;
}