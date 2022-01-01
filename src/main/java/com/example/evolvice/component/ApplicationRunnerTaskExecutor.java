package com.example.evolvice.component;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("!test")
@Component
public class ApplicationRunnerTaskExecutor implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) {
	}

}
