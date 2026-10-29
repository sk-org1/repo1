package com.rebanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class RebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(RebankingApplication.class, args);
	}
}
