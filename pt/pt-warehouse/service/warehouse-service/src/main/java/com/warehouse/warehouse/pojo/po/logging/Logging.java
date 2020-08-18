package com.warehouse.warehouse.pojo.po.logging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * (Logging)实体类
 *
 * @author makejava
 * @since 2020-05-27 14:52:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "logging")
public class Logging implements Serializable {

    private static final long serialVersionUID = -97763266707717644L;

    /**
     * id
     */
    @Id
    private String id;

    /**
     * 日志级别(TRACE < DEBUG < INFO < WARN < ERROR，先使用info和error)
     */
    @Column(name = "level")
    private String level;

    /**
     * 来源那个项目
     */
    @Column(name = "project")
    private String project;

    /**
     * 执行的类名称
     */
    @Column(name = "execute_class")
    private String executeClass;

    /**
     * 执行的函数
     */
    @Column(name = "execute_method")
    private String executeMethod;

    /**
     * 开始执行时间
     */
    @Column(name = "begin_time")
    private Date beginTime;

    /**
     * 执行结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 执行耗费时间ms
     */
    @Column(name = "consuming_time")
    private Integer consumingTime;

    /**
     * 0代表执行成功，1代表执行失败
     */
    @Column(name = "execute_is_success")
    private Integer executeIsSuccess;

    /**
     * 返回结果
     */
    @Column(name = "execute_result_data")
    private String executeResultData;

    /**
     * 日志触发用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 日志触发用户名称
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 触发的IP地址
     */
    @Column(name = "ip_address")
    private String ipAddress;

    /**
     * 请求的sessionId
     */
    @Column(name = "request_session_id")
    private String requestSessionId;

    /**
     * 日志信息
     */
    @Column(name = "log_message")
    private String logMessage;

    @Column(name = "company_id")
    private String companyId;

}