package com.ai.gpt.helloword;

import org.springframework.ai.autoconfigure.vertexai.gemini.VertexAiGeminiAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = {VertexAiGeminiAutoConfiguration.class})
public class HellowordApplication {

	public static void main(String[] args) {
		SpringApplication.run(HellowordApplication.class, args);
	}

}
