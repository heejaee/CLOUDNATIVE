package com.example.cloudnative.util;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(final CorsRegistry registry) {
        //CORS 설정
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080","http://localhost:8090")
                .allowedMethods("GET", "POST")
                .maxAge(3000);
    }
}
