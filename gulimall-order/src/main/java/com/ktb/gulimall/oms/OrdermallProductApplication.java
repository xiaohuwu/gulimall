package com.ktb.gulimall.oms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ktb.gulimall.oms.dao")
public class OrdermallProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrdermallProductApplication.class, args);
    }

}
