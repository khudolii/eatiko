package com.eatiko.logic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableScheduling
public class EatikoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EatikoApplication.class, args);
    }

}
