package com.pt.ptauth.service;

import com.pt.ptauth.util.*;
import com.pt.ptcommoncore.constant.CacheConstants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @author wl
 * @date 2020/5/19
 */
@Slf4j
public class CustomClientDetailsService extends JdbcClientDetailsService{
    @Resource
    private RedisUtils redisUtils;


    public CustomClientDetailsService(DataSource dataSource) {
        super(dataSource);
    }


    /**
     * 重写原生方法支持redis缓存
     * 获取clientId并存储
     * @param clientId
     * @return
     */
    @Override
    @SneakyThrows
    public ClientDetails loadClientByClientId(String clientId) {
        redisUtils.set(CacheConstants.CLIENT_DETAILS_KEY,clientId);
        return super.loadClientByClientId(clientId);
    }



}
