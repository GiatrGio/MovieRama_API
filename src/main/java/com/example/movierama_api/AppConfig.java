package com.example.movierama_api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${react.app.url}")
    private String reactAppUrl;

    @Bean
    public String getReactAppUrl() {
        return reactAppUrl;
    }
}