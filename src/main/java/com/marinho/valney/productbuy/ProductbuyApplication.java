package com.marinho.valney.productbuy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProductbuyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductbuyApplication.class, args);
	}

}
