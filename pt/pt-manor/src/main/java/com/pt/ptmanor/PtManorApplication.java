package com.pt.ptmanor;

import com.pt.ptcommonsecurity.annotation.EnableCustomResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableCustomResourceServer
//@ComponentScan("com.linghit.ocs.zhanxing.service.handler.LogAspect")
public class PtManorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PtManorApplication.class, args);
	}

}
