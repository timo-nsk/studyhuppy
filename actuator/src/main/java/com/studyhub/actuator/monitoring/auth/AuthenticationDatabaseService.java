package com.studyhub.actuator.monitoring.auth;

import com.studyhub.actuator.DatabaseHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class AuthenticationDatabaseService implements DatabaseHealthIndicator {

	@Override
	public Health health() {
		if(authenticationServiceDatabaseIsHealthy()) return Health.up().build();
		return Health.down().build();
	}

	private boolean authenticationServiceDatabaseIsHealthy() {
		String uri = "http://localhost:8084/api/get-db-health";
		CompletableFuture<String> health = WebClient.create()
				.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(String.class)
				.timeout(Duration.of(3, ChronoUnit.SECONDS))
				.toFuture();

		try {
			String response = health.get();
			return Boolean.parseBoolean(response);

		} catch(ExecutionException | InterruptedException e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
}
