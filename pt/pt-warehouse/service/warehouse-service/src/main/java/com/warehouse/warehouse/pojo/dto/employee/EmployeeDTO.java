package com.warehouse.warehouse.pojo.dto.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Employee)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:31:06
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO implements Serializable {
    
    private static final long serialVersionUID = 882464483631633835L;

    
    private String id;

    /**
     * 职员名称
     */
    private String employeeName;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号码
     */
    private String telephone;

    /**
     * 地址
     */
    private String addr;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 生日
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date birthday;

    /**
     * 是否已婚
     */
    private Integer isMerried;

    /**
     * 入职时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date inTime;

    /**
     * 所属部门名称
     */
    private String department;

    /**
     * 领导人id
     */
    private String leaderId;

    /**
     * 民族
     */
    private String nation;


    /**
     * 排序字段
     */
    private String orderBy;

    private String companyId;
}