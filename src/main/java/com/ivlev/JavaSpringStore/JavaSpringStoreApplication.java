package com.ivlev.JavaSpringStore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class JavaSpringStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaSpringStoreApplication.class, args);
	}

}
