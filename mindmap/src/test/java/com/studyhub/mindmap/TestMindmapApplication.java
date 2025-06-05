package com.studyhub.mindmap;

import org.springframework.boot.SpringApplication;

public class TestMindmapApplication {

	public static void main(String[] args) {
		SpringApplication.from(MindmapApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
