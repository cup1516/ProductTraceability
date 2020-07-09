package com.pt.ptdealerprod;

import com.pt.ptcommonsecurity.annotation.EnableCustomResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCustomResourceServer
public class PtDealerProdApplication {

    public static void main(String[] args) {
        SpringApplication.run(PtDealerProdApplication.class, args);
    }

}
