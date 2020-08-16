package com.pt.ptmanor.aspect;


import com.alibaba.fastjson.JSON;


import com.pt.ptcommonsecurity.util.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;
import java.util.UUID;


@Aspect
@Component
public class SysLogAspect {


    @Autowired
    SysLogRepository sysLogRepository;

    private static  final org.slf4j.Logger log =  LoggerFactory.getLogger(SysLogAspect.class );

    //定义切点 @Pointcut
    //在注解的位置切入代码
    @Pointcut("@annotation( com.pt.ptmanor.aspect.MyLog)")
    public void logPointCut() {
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e)
    {
        handleLog(joinPoint, e, null);
    }


    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(value = "logPointCut()",returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult)
    {
        handleLog(joinPoint,null,jsonResult);
    }


    protected void handleLog(final JoinPoint joinPoint,final Exception e,Object jsonResult)
    {

        try{
            // *========数据库日志=========*//
            SysLog sysLog = new SysLog();

            sysLog.setStatus(BusinessStatus.SUCCESS.ordinal());


            Signature signature = joinPoint.getSignature();

            MethodSignature methodSignature = (MethodSignature) signature;

            Method method = methodSignature.getMethod();

            //获取操作
            MyLog myLog = method.getAnnotation(MyLog.class);

            // 设置action动作
            sysLog.setBusinessType(myLog.businessType().ordinal());

            // 设置操作人类别
            sysLog.setOperatorType(myLog.operatorType().ordinal());
            // 是否需要保存request，参数和值
            if (myLog.isSaveRequestData())
            {
                // 获取参数的信息，传入到数据库中。
                setRequestValue(joinPoint, sysLog);
            }

             //请求的参数
            Object[] args = joinPoint.getArgs();

             //将参数所在的数组转换成json
            String params = JSON.toJSONString(args);


            sysLog.setParams(params);

            String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
            sysLog.setIp(ip);
            // 返回参数
            sysLog.setJsonResult(JSON.toJSONString(jsonResult));
            sysLog.setUrl(ServletUtils.getRequest().getRequestURI());
            if (SecurityUtils.getUserName() != null)
            {
                sysLog.setUserName(SecurityUtils.getUserName());
                sysLog.setUserId(SecurityUtils.getId());
            }
            if (e != null)
            {
                sysLog.setStatus(BusinessStatus.FAIL.ordinal());
                sysLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }

            sysLog.setValue(myLog.value());
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            sysLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            sysLog.setRequestMethod(ServletUtils.getRequest().getMethod());
            // 处理设置注解上的参数
            // 保存数据库
            UUID u = UUID.randomUUID();
            String str = u.toString();
            str = str.replace("-","");
            sysLog.setId(str);
            sysLog.setCreateDate(new Date());
            sysLog.setIsDeleted(0);
            sysLog.setCompanyId(  SecurityUtils.getCompanyId());
            sysLogRepository.save(sysLog);
        }
        catch (Exception exp)
        {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }


    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param myLog 日志
     * @param sysLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, MyLog myLog, SysLog sysLog) throws Exception
    {
        // 设置action动作
        sysLog.setBusinessType(myLog.businessType().ordinal());

        // 设置操作人类别
        sysLog.setOperatorType(myLog.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (myLog.isSaveRequestData())
        {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, sysLog);
        }
    }


    /**
     * 获取请求的参数，放到log中
     *
     * @param sysLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, SysLog sysLog) throws Exception
    {


        String requestMethod = sysLog.getRequestMethod();

        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod))
        {
            String params = argsArrayToString(joinPoint.getArgs());
            sysLog.setParams(StringUtils.substring(params, 0, 2000));
        }
        else
        {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            sysLog.setParams(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private MyLog getAnnotationLog(JoinPoint joinPoint) throws Exception
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(MyLog.class);
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray)
    {
        String params = "";
        if (paramsArray != null && paramsArray.length > 0)
        {
            for (int i = 0; i < paramsArray.length; i++)
            {
                if (!isFilterObject(paramsArray[i]))
                {
                    Object jsonObj = JSON.toJSON(paramsArray[i]);
                    params += jsonObj.toString() + " ";
                }
            }
        }
        return params.trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o)
    {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }

}
