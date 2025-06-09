package com.studyhub.track.adapter.kartei;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StapelRequestService {

	@Value("${kartei.api_url}")
	private String karteiApiUrl;

	private final Logger log = LoggerFactory.getLogger(StapelRequestService.class);

	public void sendCreateNewStapelRequest(CreateNewStapelRequest request) {
		String uri = "%s/create-stapel".formatted(karteiApiUrl);

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