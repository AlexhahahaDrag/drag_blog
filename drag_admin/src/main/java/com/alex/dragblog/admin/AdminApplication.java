package com.alex.dragblog.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableTransactionManagement
@SpringBootApplication
@EnableSwagger2
@EnableCaching
@EnableFeignClients("com.alex.dragblog.commons.feign")
@ComponentScan(basePackages = {
        "com.alex.dragblog.admin.config",
        "com.alex.dragblog.admin.restapi",
        "com.alex.dragblog.commons.config",
        "com.alex.dragblog.utils",
        "com.alex.dragblog.xo.service",
        "com.alex.dragblog.xo.util",
        "com.alex.dragblog.xo.mapper",
        "com.alex.dragblog"
})
@MapperScan(basePackages = "com.alex.dragblog")
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }

}
