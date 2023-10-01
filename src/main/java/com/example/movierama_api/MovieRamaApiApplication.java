package com.example.movierama_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example")
public class MovieRamaApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(MovieRamaApiApplication.class, args);

    }

}