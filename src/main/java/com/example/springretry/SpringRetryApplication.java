package com.example.springretry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry; // Import @EnableRetry

@SpringBootApplication
@EnableRetry // Add this annotation
public class SpringRetryApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRetryApplication.class, args);
	}

}
