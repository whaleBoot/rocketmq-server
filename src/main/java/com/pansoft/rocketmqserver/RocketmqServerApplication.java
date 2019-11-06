package com.pansoft.rocketmqserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.pansoft")
//@EnableScheduling
public class RocketmqServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RocketmqServerApplication.class, args);
	}

}
