package com.example.evolvice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.evolvice.rest.RestApiController;

@Configuration
@ComponentScan(basePackageClasses = { RestApiController.class })
public class RestConfig {

}
