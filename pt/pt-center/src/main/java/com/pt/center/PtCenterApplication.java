package com.pt.center;

import com.pt.ptcommonsecurity.annotation.EnableCustomResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableCustomResourceServer
@ComponentScan(basePackages = {"com.pt"})
public class PtCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(PtCenterApplication.class, args);
	}
}
