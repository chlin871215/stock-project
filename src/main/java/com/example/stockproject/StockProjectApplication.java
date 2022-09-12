package com.example.stockproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class StockProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockProjectApplication.class, args);
	}

}
