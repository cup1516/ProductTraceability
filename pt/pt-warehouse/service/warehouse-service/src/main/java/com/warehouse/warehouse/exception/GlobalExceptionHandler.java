package com.warehouse.warehouse.exception;

import com.common.utils.ResponseData;
import com.common.utils.ResponseDataUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Copyright (C), 2020-2020, wanpp
 * FileName: GlobalExceptionHandler
 *
 * @Author: wanpp
 * @Date: 2020/5/15 20:42
 * Description: 统一异常处理
 * Version: 1.0
 */

//@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);

    /**
     * @title: runtimeExceptionHandler
     * @description: 自己抛出的异常
     * @params: [ e ]
     * @return: com.common.utils.ResponseData
     * @createTime: 2020/5/15 20:56
     * @version: 1.0
     * @author: wanpp
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    private ResponseData runtimeExceptionHandler(Exception e) {
        logger.error("RuntimeException handler: ", e);
        return ResponseDataUtils.buildError(e.toString(), "操作失败");
    }

    /**
     * @title: ExceptionHandler
     * @description: 全部异常
     * @params: [ e ]
     * @return: com.common.utils.ResponseData
     * @createTime: 2020/5/15 21:01
     * @version: 1.0
     * @author: wanpp
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    private ResponseData ExceptionHandler(Exception e) {
        logger.error("Exception handler: ", e);
        return ResponseDataUtils.buildError(e.toString(), "操作失败");
    }

}
