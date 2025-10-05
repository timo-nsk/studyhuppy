package com.studyhub.actuator.monitoring.mail;


import com.studyhub.actuator.MailHealthIndicator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MailService implements MailHealthIndicator {
	@Value("${app.services.health.mail}")
	private String MAIL_SERVICE_URI;

	@Override
	public Health health() {
		if(Boolean.TRUE.equals(isMailServiceHealthy().block())) return Health.up().build();
		return Health.down().build();
	}

	private Mono<Boolean> isMailServiceHealthy() {
		return WebClient.create()
				.get()
				.uri(MAIL_SERVICE_URI)
				.retrieve()
				.toBodilessEntity()
				.map(response -> response.getStatusCode().is2xxSuccessful())
				.onErrorResume(ex -> Mono.just(false));
	}

}
