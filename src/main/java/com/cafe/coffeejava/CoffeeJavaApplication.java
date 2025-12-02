package com.cafe.coffeejava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class CoffeeJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoffeeJavaApplication.class, args);
    }

}
