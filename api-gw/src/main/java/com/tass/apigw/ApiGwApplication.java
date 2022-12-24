package com.tass.apigw;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class ApiGwApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGwApplication.class, args);
    }

}
