package com.warehouse.warehouse.utils;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Copyright (C), 2020-2020, si-tech
 * FileName: RequestUtils
 *
 * @Author: wanpp
 * @Date: 2020/4/3 11:52
 * Description:
 * Version: 1.0
 */

public class RequestUtils {

    /**
     * @title: getHttpServletRequest
     * @description: 获取 Request
     * @params: []
     * @return: javax.servlet.http.HttpServletRequest
     * @createTime: 2020/2/28 16:56
     * @version: 1.0
     * @author: wanpp
     */
    public static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * @title: getHttpServletResponse
     * @description: 获取 Response
     * @params: []
     * @return: javax.servlet.http.HttpServletResponse
     * @createTime: 2020/2/28 16:56
     * @version: 1.0
     * @author: wanpp
     */
    public static HttpServletResponse getHttpServletResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    /**
     * @title: getIpAddr
     * @description: 获取ip地址
     * @params: [request]
     * @return: java.lang.String
     * @createTime: 2020/2/28 16:57
     * @version: 1.0
     * @author: wanpp
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 处理多IP的情况（只取第一个IP）
        if (ip != null && ip.contains(",")) {
            String[] ipArray = ip.split(",");
            ip = ipArray[0];
        }
        return ip;
    }


    /**
     * 获取发起请求的浏览器信息
     */
    public static String getBrowserMsg(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        return header;
    }

}
