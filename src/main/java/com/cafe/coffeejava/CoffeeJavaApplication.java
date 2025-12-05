package com.cafe.coffeejava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@ConfigurationPropertiesScan
@SpringBootApplication
@EnableScheduling
public class CoffeeJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoffeeJavaApplication.class, args);
    }

}
