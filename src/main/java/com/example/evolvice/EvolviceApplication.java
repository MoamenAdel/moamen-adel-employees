package com.example.evolvice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import com.example.evolvice.config.ServiceConfig;

@SpringBootApplication
@Import({ ServiceConfig.class })
@Profile("!test")
public class EvolviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EvolviceApplication.class, args);
	}

}
