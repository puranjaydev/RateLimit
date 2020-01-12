package com.halodoc.ratelimit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class RatelimitApplication {

    public static void main(String[] args) {
        SpringApplication.run(RatelimitApplication.class, args);

    }

}
