package com.BackSpringBoys.Java_Backend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class JavaBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(JavaBackendApplication.class, args);
	}
}
