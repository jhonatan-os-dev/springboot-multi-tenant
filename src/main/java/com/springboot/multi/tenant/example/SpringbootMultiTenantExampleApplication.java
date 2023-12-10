package com.springboot.multi.tenant.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@ComponentScan({"com.springboot.multi.tenant.example"})
public class SpringbootMultiTenantExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMultiTenantExampleApplication.class, args);
	}

}
