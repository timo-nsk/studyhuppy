package com.studyhub.actuator.monitoring.kartei;

import com.studyhub.actuator.KarteiHealthIndicator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class KarteiService implements KarteiHealthIndicator {

	@Value("${app.services.health.kartei}")
	private String KARTEI_SERVICE_URI;

	@Override
	public Health health() {
		if(Boolean.TRUE.equals(isKarteiServiceHealthy().block())) return Health.up().build();
		return Health.down().build();
	}

	private Mono<Boolean> isKarteiServiceHealthy() {
		return WebClient.create()
				.get()
				.uri(KARTEI_SERVICE_URI)
				.retrieve()
				.toBodilessEntity()
				.map(response -> response.getStatusCode().is2xxSuccessful())
				.onErrorResume(ex -> Mono.just(false));
	}
}
