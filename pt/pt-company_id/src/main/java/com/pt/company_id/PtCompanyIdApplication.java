package com.pt.company_id;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableCustomResourceServer
@ComponentScan(basePackages = {"com.pt"})
public class PtCompanyIdApplication {

    public static void main(String[] args) {
        SpringApplication.run(PtCompanyIdApplication.class, args);
    }

}
