package com.autocat.humusontest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class HumusonTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(HumusonTestApplication.class, args);
    }

}
