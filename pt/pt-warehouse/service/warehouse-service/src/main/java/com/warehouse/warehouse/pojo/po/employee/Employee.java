package com.warehouse.warehouse.pojo.po.employee;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (Employee)实体类
 *
 * @author makejava
 * @since 2020-04-09 20:28:58
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 637623015763188125L;

    @Id
    private String id;

    /**
     * 职员名称
     */
    @Column(name = "employee_name")
    private String employeeName;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 手机号码
     */
    @Column(name = "telephone")
    private String telephone;

    /**
     * 地址
     */
    @Column(name = "addr")
    private String addr;

    /**
     * 状态
     */
    @Column(name = "status")
    private Integer status;

    /**
     * 性别
     */
    @Column(name = "sex")
    private Integer sex;

    /**
     * 生日
     */
    @Column(name = "birthday")
    private Date birthday;

    /**
     * 是否已婚
     */
    @Column(name = "is_merried")
    private Integer isMerried;

    /**
     * 入职时间
     */
    @Column(name = "in_time")
    private Date inTime;

    /**
     * 所属部门名称
     */
    @Column(name = "department")
    private String department;

    /**
     * 领导人id
     */
    @Column(name = "leader_id")
    private String leaderId;

    /**
     * 民族
     */
    @Column(name = "nation")
    private String nation;

    @Column(name = "company_id")
    private String companyId;

}