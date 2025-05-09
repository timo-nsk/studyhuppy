package com.studyhub.actuator.monitoring.kartei;

import com.studyhub.actuator.DatabaseHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class KarteiDatabaseService implements DatabaseHealthIndicator {
	@Override
	public Health health() {
		if(karteiServiceDatabaseIsHealthy()) return Health.up().build();
		return Health.down().build();
	}

	private boolean karteiServiceDatabaseIsHealthy() {
		String uri = "http://localhost:8081/api/get-db-health";
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
