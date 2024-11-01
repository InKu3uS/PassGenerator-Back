package com.neftali.passgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableScheduling
@ComponentScan(basePackages = "com.neftali.passgenerator")
public class PassgeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PassgeneratorApplication.class, args);
	}

}
