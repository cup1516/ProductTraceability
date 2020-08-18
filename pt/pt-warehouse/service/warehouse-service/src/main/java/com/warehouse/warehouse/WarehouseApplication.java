package com.warehouse.warehouse;

import com.pt.ptcommonsecurity.annotation.EnableCustomResourceServer;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * Copyright (C), 2020-2020, wanpp
 * FileName: WarehouseApplication
 *
 * @Author: wanpp
 * @Date: 2020/5/12 20:13
 * Description:
 * Version: 1.0
 */

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.warehouse.warehouse.dao")
@EnableCustomResourceServer
public class WarehouseApplication {
    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }

    @Bean
    MeterRegistryCustomizer<MeterRegistry> configurer(@Value("${spring.application.name}") String applicationName) {
        return (registry) -> registry.config().commonTags("application", applicationName);
    }
}
