package com.warehouse.warehouse.config.filter;

import com.alibaba.fastjson.JSON;
import com.common.utils.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Copyright (C), 2020-2020, wanpp
 * FileName: LoginFilter
 *
 * @Author: wanpp
 * @Date: 2020/5/20 22:04
 * Description: 此类用作使用jwt解析token信息，校验登入信息，并且根据菜单权限进行处理
 * Version: 1.0
 */
//@Configuration
public class LoginFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(LoginFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String tokenHeader = request.getHeader("token");
        logger.info("token: {}", tokenHeader);
        if (tokenHeader != null) {
            if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                return;
            }

            try {
                Claims claims = JwtUtils.parseJWT(tokenHeader);

                logger.info("解析token获取到的数据为：{}", JSON.toJSONString(claims));
                //todo 获取用户信息，并根据当前request请求的地址进行拦截和通过
                request.setAttribute("user", claims);
            } catch (Exception e) {
                logger.info("解析token数据异常，异常为：{}", e.toString());
                response.setStatus(200);
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write("{\"msg\": \"user not login\", \"code\": \"5001\"}");
                return;
            }


            // String username = claims.get("username").toString();

            filterChain.doFilter(request, response);
        } else {
            logger.debug("仓库管理门户请求链接" + request.getServletPath() + "被拦截，没有token");
            response.setStatus(200);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"msg\": \"user not login\", \"code\": \"5001\"}");
            return;
        }

    }

    @Override
    public void destroy() {

    }
}
