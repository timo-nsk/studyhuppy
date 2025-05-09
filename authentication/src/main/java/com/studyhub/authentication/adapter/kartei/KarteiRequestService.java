package com.studyhub.authentication.adapter.kartei;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class KarteiRequestService {
	public boolean sendDeleteAllRequest(String username) {
		String uri = "http://localhost:8081/delete-all";

		return Boolean.TRUE.equals(WebClient.create()
				.post()
				.uri(uri)
				.bodyValue(username)
				.exchangeToMono(response -> Mono.just(response.statusCode().is2xxSuccessful()))
				.onErrorReturn(false)
				.block());
	}

}
