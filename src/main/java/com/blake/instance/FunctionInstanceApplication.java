package com.blake.instance;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.blake.instance.common.mapper"})
public class FunctionInstanceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FunctionInstanceApplication.class, args);
    }

}
