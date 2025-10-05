package com.studyhub.authentication.adapter.track;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TrackRequestService {
	@Value("${modul.api-url}")
	private String modulApiUrl;

	public boolean sendDeleteAllRequest(String username) {
		String uri = "%s/delete-all".formatted(modulApiUrl);

		return Boolean.TRUE.equals(WebClient.create()
				.post()
				.uri(uri)
				.bodyValue(username)
				.exchangeToMono(response -> Mono.just(response.statusCode().is2xxSuccessful()))
				.onErrorReturn(false)
				.block());
	}
}
