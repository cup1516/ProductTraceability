package com.warehouse.warehouse.pojo.dto.logging;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoggingDTO {

    private static final long serialVersionUID = -97763266707717644L;

    /**
     * id
     */

    private String id;

    /**
     * 日志级别(TRACE < DEBUG < INFO < WARN < ERROR，先使用info和error)
     */
    private String level;

    /**
     * 来源那个项目
     */
    private String project;

    /**
     * 执行的类名称
     */
    private String executeClass;

    /**
     * 执行的函数
     */
    private String executeMethod;

    /**
     * 开始执行时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date beginTime;

    /**
     * 执行结束时间
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone="GMT+8")
    private Date endTime;

    /**
     * 执行耗费时间ms
     */
    private Integer consumingTime;

    /**
     * 0代表执行成功，1代表执行失败
     */
    private Integer executeIsSuccess;

    /**
     * 返回结果
     */
    private String executeResultData;

    /**
     * 日志触发用户id
     */
    private String userId;

    /**
     * 日志触发用户名称
     */
    private String userName;

    /**
     * 触发的IP地址
     */
    private String ipAddress;

    /**
     * 请求的sessionId
     */
    private String requestSessionId;

    /**
     * 日志信息
     */
    private String logMessage;

    private String companyId;

}
