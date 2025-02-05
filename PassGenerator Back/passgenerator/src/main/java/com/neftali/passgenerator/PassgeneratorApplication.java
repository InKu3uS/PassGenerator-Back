package com.neftali.passgenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableScheduling
@ComponentScan(basePackages = "com.neftali.passgenerator")
public class PassgeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(PassgeneratorApplication.class, args);
	}

	//Activar CORS
	@Bean
	public WebMvcConfigurer corsConfig(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("https://passgenerator-six.vercel.app", "http://localhost:4200")
						.allowedMethods("*")
						.allowedHeaders("*");
			}
		};
	}
}
