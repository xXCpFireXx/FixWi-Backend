package com.fixwi.fixwi_backend;

import org.springframework.boot.SpringApplication;

public class TestFixWiBackendApplication {

	public static void main(String[] args) {
		SpringApplication.from(FixWiBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
