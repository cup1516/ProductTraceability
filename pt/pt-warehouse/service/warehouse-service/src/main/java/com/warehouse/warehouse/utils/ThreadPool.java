package com.warehouse.warehouse.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.RejectedExecutionException;

/**
 * Copyright (C), 2020-2020, si-tech
 * FileName: ThreadPool
 *
 * @Author: wanpp
 * @Date: 2020/6/02 17:26
 * Description: 线程池
 * Version: 1.0
 */
@Component
public class ThreadPool extends ThreadPoolTaskExecutor {

    private static final long serialVersionUID = -8240217644543643520L;

    Logger logger = LoggerFactory.getLogger(ThreadPool.class);

    public void add(Runnable runnable) {
        try {
            super.execute(runnable);
        } catch (RejectedExecutionException e) {
            throw new RuntimeException(e);
        } finally {
            dispaly();
        }
    }

    public void dispaly() {
        logger.warn("Thread Watting ...");
        logger.info("ThreadPool Monitor: poolSize:{}, corePoolSize:{}, maxPoolSize:{}, queueSize:{}", getPoolSize(), getCorePoolSize(), getMaxPoolSize(), getThreadPoolExecutor().getQueue().size());
    }

    /**
     * @title: setCorePoolSize
     * @description: 设置默认线程数
     * @params: [ corePoolSize ]
     * @return: void
     * @createTime: 2020/4/29 22:38
     * @version: 1.0
     * @author: wanpp
     */
    @Value("5")
    @Override
    public void setCorePoolSize(int corePoolSize) {
        super.setCorePoolSize(corePoolSize);
    }

    /**
     * @title: setQueueCapacity
     * @description: 设置 阻塞队列的长度，如果超过这个长度，会增加线程池的线程数量
     * @params: [queueCapacity]
     * @return: void
     * @createTime: 2020/4/29 22:38
     * @version: 1.0
     * @author: wanpp
     */
    @Value("10")
    @Override
    public void setQueueCapacity(int queueCapacity){
        super.setQueueCapacity(queueCapacity);
    }

    /**
     * @title: setMaxPoolSize
     * @description: 设置线程池最大的线程数量
     * @params: [ maxPoolSize ]
     * @return: void
     * @createTime: 2020/4/29 22:40
     * @version: 1.0
     * @author: wanpp
     */
    @Value("10")
    @Override
    public void setMaxPoolSize(int maxPoolSize){
        super.setMaxPoolSize(maxPoolSize);
    }

}
