package com.warehouse.warehouse.pojo.dto.warehouse.shelves;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 货架表(Shelves)实体类
 *
 * @author makejava
 * @since 2020-04-12 20:29:31
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelvesDTO implements Serializable {
    
    private static final long serialVersionUID = 218588881214214332L;

    /**
     * 货架Id
     */
    private String id;

    /**
     * 货架名称
     */
    private String shelvesName;

    /**
     * 所属仓库id
     */
    private String warehouseId;

    /**
     * 所属仓库名称
     */
    private String warehouseName;

    /**
     * 创建人Id
     */
    private String createUserId;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新人Id
     */
    private String updateUserId;

    /**
     * 更新人名称
     */
    private String updateUserName;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 状态值，默认为0
     */
    private Integer status;


    /**
     * 排序字段
     */
    private String orderBy;

    private String companyId;
}