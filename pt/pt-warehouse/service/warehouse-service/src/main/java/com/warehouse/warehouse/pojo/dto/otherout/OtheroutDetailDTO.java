package com.warehouse.warehouse.pojo.dto.otherout;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 出库子表(OtheroutDetail)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OtheroutDetailDTO implements Serializable {
    
    private static final long serialVersionUID = -65991509808654621L;

    
    private String id;

    /**
     * 出库主表id
     */
    private String pid;

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
     * 仓储表id
     */
    private String stockId;


    /**
     * 排序字段
     */
    private String orderBy;

    private String companyId;

    private String batch;
    private String incompany;
    private  String outcompany;
}