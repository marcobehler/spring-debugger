package org.example.springdebugger;

import org.springframework.boot.SpringApplication;

public class TestSpringDebuggerApplication {

	public static void main(String[] args) {
		SpringApplication.from(SpringDebuggerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
