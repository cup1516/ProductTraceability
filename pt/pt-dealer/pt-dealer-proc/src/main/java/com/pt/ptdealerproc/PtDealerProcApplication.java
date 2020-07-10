package com.pt.ptdealerproc;

import com.pt.ptcommonsecurity.annotation.EnableCustomResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wl
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCustomResourceServer
public class PtDealerProcApplication {

    public static void main(String[] args) {
        SpringApplication.run(PtDealerProcApplication.class, args);
    }

}
