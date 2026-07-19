package com;

import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableScheduling
@SpringBootApplication
public class StudentManagementSystemApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(
				StudentManagementSystemApplication.class, 
				args);
		
		System.out.println("Started SmsApplication");
	}

}
