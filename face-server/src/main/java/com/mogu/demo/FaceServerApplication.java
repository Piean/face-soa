package com.mogu.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Created by mogu
 * Date: 2018/10/19
 */

@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = {"com.mogu.demo"})
@MapperScan(basePackages = {"com.mogu.demo.mapper"})
public class FaceServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(FaceServerApplication.class, args);
    }
}