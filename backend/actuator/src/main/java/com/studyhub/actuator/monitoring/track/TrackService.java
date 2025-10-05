package com.studyhub.actuator.monitoring.track;

import com.studyhub.actuator.TrackHealthIndicator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class TrackService implements TrackHealthIndicator {

	@Value("${app.api.token}")
	private String token;

	@Value("${app.services.health.track}")
	private String TRACK_SERVICE_URI;

	@Override
	public Health health() {
		if (Boolean.TRUE.equals(isTrackServiceHealthy().block())) {
			return Health.up().build();
		}
		return Health.down().build();
	}

	private Mono<Boolean> isTrackServiceHealthy() {
		return WebClient.create()
				.get()
				.uri(TRACK_SERVICE_URI)
				.header("ActuatorAuth", "Bearer " + token)
				.retrieve()
				.toBodilessEntity()
				.map(response -> response.getStatusCode().is2xxSuccessful())
				.onErrorResume(ex -> Mono.just(false));
	}
}
