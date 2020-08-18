package com.warehouse.warehouse.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.warehouse.warehouse.dao.logging.LoggingMapper;
import com.warehouse.warehouse.pojo.po.logging.Logging;
import com.warehouse.warehouse.utils.RequestUtils;
import com.warehouse.warehouse.utils.ThreadPool;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Date;
import java.util.UUID;

/**
 * Copyright (C), 2020-2020, si-tech
 * FileName: LogRecordAspect
 *
 * @Author: wanpp
 * @Date: 2020/2/28 15:29
 * Description: 切面整合 swagger 进行 日志打印
 * Version: 1.0
 */

@Aspect
@Component
public class LogRecordAspect {

    @Resource
    private LoggingMapper loggingMapper;

    @Resource
    private ThreadPool threadPool;

    /**
     * @title: logPointCut
     * @description: 定义切点，是 io.swagger.annotations.ApiOperation
     * @params: []
     * @return: void
     * @createTime: 2020/2/28 16:49
     * @version: 1.0
     * @author: wanpp
     */
    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    public void logPointCut() {

    }

    /**
     * @title: around
     * @description: 环切上面的切点
     * @params: [point]
     * @return: java.lang.Object
     * @createTime: 2020/2/28 16:50
     * @version: 1.0
     * @author: wanpp
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //获取整个方法的信息
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        //获取swagger api 的信息
        ApiOperation annotation = method.getAnnotation(ApiOperation.class);
        //类名
        String className = point.getTarget().getClass().getName();

        //函数
        String methodName = signature.getName();
        //参数信息
        Object[] args = point.getArgs();
        Parameter[] parameters = ((MethodSignature) point.getSignature()).getMethod().getParameters();
        //使用json 处理一下参数信息
        JSONObject jsonObject = getParamData(parameters,args);
        //请求，主要用来获取ip
        HttpServletRequest request = RequestUtils.getHttpServletRequest();
        //获取类的 logger
        Logger logger = LogManager.getLogger(point.getTarget().getClass());

        Object result = new Object();

        Logging logging = new Logging();
        logging.setId(UUID.randomUUID().toString());
        logging.setProject("warehouse");
        logging.setExecuteClass(className);
        logging.setExecuteMethod(methodName);
        Date beginData = new Date();
        logging.setBeginTime(beginData);
        logging.setUserId("1");
        logging.setUserName("warehouse");
        logging.setIpAddress(RequestUtils.getIpAddr(request));
        logging.setRequestSessionId(request.getSession().getId());

        try {
            logger.info("用户"+"user"+"使用IP为"+RequestUtils.getIpAddr(request)+"的终端访问类"+className+"中的"+methodName+"方法");
            logger.info("准备执行类"+className+"中的"+methodName+"方法进行"+annotation.notes()+",传入的参数为:"+jsonObject.toJSONString());
            //执行开始时间
            //long beginTime = System.currentTimeMillis();
            StopWatch watch = new StopWatch();
            watch.start(methodName);
            //执行方法
            result = point.proceed();
            //执行时长(毫秒)
            //long time = System.currentTimeMillis() - beginTime;
            watch.stop();
            logger.info("执行类"+className+"中的"+methodName+"方法进行"+annotation.notes()+"完成,耗时 "+watch.getTotalTimeMillis()+" 毫秒,准备返回的结果为: "+ JSONObject.toJSONString(result));
            //日志可打入数据库
            //todo 解析用户信息
            logging.setLevel("info");
            logging.setEndTime(new Date());
            Long totalTimeMillis = watch.getTotalTimeMillis();
            logging.setConsumingTime(totalTimeMillis.intValue());
            logging.setExecuteIsSuccess(1);
            logging.setExecuteResultData(JSONObject.toJSONString(result));
            logging.setLogMessage("执行类"+className+"中的"+methodName+"方法进行"+annotation.notes()+"完成,耗时 "+watch.getTotalTimeMillis()+" 毫秒,准备返回的结果为: "+ JSONObject.toJSONString(result));
        }catch (Exception e){
            logger.error("异常：{}", JSON.toJSONString(e));
            logging.setLevel("error");
            Date endDate = new Date();
            logging.setEndTime(endDate);
            Long totalTimeMillis = endDate.getTime() - beginData.getTime();
            logging.setConsumingTime(totalTimeMillis.intValue());
            logging.setExecuteIsSuccess(0);
            logging.setExecuteResultData(JSONObject.toJSONString(result));
            logging.setLogMessage(JSON.toJSONString(e));
        }
        threadPool.add(()->{
            loggingMapper.insertLogging(logging);
        });
        return result;
    }

    /**
     * @title: getParamData
     * @description: 处理参数信息
     * @params: [paramsArgsName, paramsArgsValue]
     * @return: com.alibaba.fastjson.JSONObject
     * @createTime: 2020/2/28 16:57
     * @version: 1.0
     * @author: wanpp
     */
    private JSONObject getParamData(Parameter[] paramsArgsName, Object[] paramsArgsValue) {
        JSONObject jsonObject = new JSONObject();
        for (int i = 0;i<paramsArgsName.length;i++){
            jsonObject.put(paramsArgsName[i].getName(),paramsArgsValue[i]);
        }
        return jsonObject;
    }

}
