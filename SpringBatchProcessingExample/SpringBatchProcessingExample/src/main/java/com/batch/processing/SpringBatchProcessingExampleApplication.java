package com.batch.processing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringBatchProcessingExampleApplication {

	public static void main(String[] args) {
//		System.exit(SpringApplication.exit(
				SpringApplication.run(SpringBatchProcessingExampleApplication.class, args);
		System.out.println("Welcome to Spring Batch Processing...!!!");



	}

}
