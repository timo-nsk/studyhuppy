package com.studyhub.actuator.monitoring.auth;

import com.studyhub.actuator.AuthenticationHealthIndicator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthenticationService implements AuthenticationHealthIndicator {

	@Value("${app.api.token}")
	private String token;

	@Value("${app.services.health.authentication}")
	private String AUTH_SERVICE_URI;

	@Override
	public Health health() {
		if (Boolean.TRUE.equals(isLoginServiceHealthy().block())) {
			return Health.up().build();
		}
		return Health.down().build();
	}

	private Mono<Boolean> isLoginServiceHealthy() {
		return WebClient.create()
				.get()
				.uri(AUTH_SERVICE_URI)
				.header("ActuatorAuth", "Bearer " + token)
				.retrieve()
				.toBodilessEntity()
				.map(response -> response.getStatusCode().is2xxSuccessful())
				.onErrorResume(ex -> Mono.just(false));
	}
}
