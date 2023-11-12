package com.example.cloudnative;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class CloudnativeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CloudnativeApplication.class, args);
    }

}
