package com.example.evolvice.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.evolvice.util.IUtilityService;

@Configuration
@ComponentScan(basePackageClasses = { IUtilityService.class })
public class ServiceConfig {

}
