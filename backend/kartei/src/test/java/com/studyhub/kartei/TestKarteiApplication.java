package com.studyhub.kartei;

import org.springframework.boot.SpringApplication;

public class TestKarteiApplication {

	public static void main(String[] args) {
		SpringApplication.from(KarteiApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
