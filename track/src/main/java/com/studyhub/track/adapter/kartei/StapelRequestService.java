package com.studyhub.track.adapter.kartei;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StapelRequestService {

	private final Logger log = LoggerFactory.getLogger(StapelRequestService.class);

	public void sendCreateNewStapelRequest(CreateNewStapelRequest request) {
		String uri = "http://localhost:8081/api/create-stapel";

		WebClient.create()
					.post()
					.uri(uri)
					.bodyValue(request)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
					.retrieve()
					.bodyToMono(String.class)
					.block();

		log.info("CreateNewStapelRequest sent to: %s ".formatted(uri));
	}
}
