package com.ffw.flyfarewatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class FlyfarewatcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlyfarewatcherApplication.class, args);
	}

}
