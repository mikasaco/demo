package com.shenlinqiang.demo.transaction;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = DruidDataSourceAutoConfigure.class)
@MapperScan("com.shenlinqiang.demo.transaction.mapper")
public class MainApplication {
    public static void main(String[] args) {
        String property = System.getProperty("env", "order");
        if("order".equals(property)){

        }
        SpringApplication.run(MainApplication.class, args);
    }
}
