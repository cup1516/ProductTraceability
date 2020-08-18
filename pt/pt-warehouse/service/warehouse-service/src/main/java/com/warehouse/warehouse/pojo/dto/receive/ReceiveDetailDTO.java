package com.warehouse.warehouse.pojo.dto.receive;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 接货表子表(ReceiveDetail)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiveDetailDTO implements Serializable {
    
    private static final long serialVersionUID = -97517932153932610L;

    
    private String id;

    /**
     * 接货表id
     */
    private String pid;

    /**
     * 商品id
     */
    private String commodityId;

    /**
     * 商品条码
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
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;


    /**
     * 排序字段
     */
    private String orderBy;
}