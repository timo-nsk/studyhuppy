package com.studyhub.track.adapter.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration;

@Service
public class AuthenticationService {
	private Logger log = LoggerFactory.getLogger(AuthenticationService.class);

	public Integer getSemesterOfUser(String username) {
		log.info("Try get fachsemester of user '%s' from authentication service.", username);
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