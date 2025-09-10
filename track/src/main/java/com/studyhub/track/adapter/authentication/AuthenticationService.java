package com.studyhub.track.adapter.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration;

@Service
public class AuthenticationService {
	@Value("${auth.api-url}")
	private String authAiUrl;

	private Logger log = LoggerFactory.getLogger(AuthenticationService.class);

	public Integer getSemesterOfUser(String username, String token) {
		log.info("Try get fachsemester of user '%s' from authentication service.", username);
		return WebClient.builder()
				.baseUrl(authAiUrl)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE)
				.build()
				.post()
				.uri("/get-semester")
				.header("Authorization", "Bearer " + token)
				.bodyValue(username)
				.retrieve()
				.bodyToMono(Integer.class)
				.block();

	}
}