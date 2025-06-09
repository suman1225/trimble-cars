package com.trimble_cars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TrimbleCarsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrimbleCarsApplication.class, args);
	}

}
