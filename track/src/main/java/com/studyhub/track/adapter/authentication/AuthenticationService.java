package com.studyhub.track.adapter.authentication;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration;

@Service
public class AuthenticationService {
	public Integer getSemesterOfUser(String username) {
		return WebClient.create()
				.post()
				.uri("http://localhost:8084/api/v1/get-semester")
				.bodyValue(username)
				.retrieve()
				.bodyToMono(Integer.class)
				.timeout(Duration.ofSeconds(5))
				.block();
	}
}