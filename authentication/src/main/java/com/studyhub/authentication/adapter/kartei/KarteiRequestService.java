package com.studyhub.authentication.adapter.kartei;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class KarteiRequestService {
	@Value("${kartei.api-url}")
	private String karteiApiUrl;

	public boolean sendDeleteAllRequest(String username) {
		String uri = "%s/delete-all".formatted(karteiApiUrl);

		return Boolean.TRUE.equals(WebClient.create()
				.post()
				.uri(uri)
				.bodyValue(username)
				.exchangeToMono(response -> Mono.just(response.statusCode().is2xxSuccessful()))
				.onErrorReturn(false)
				.block());
	}

}
