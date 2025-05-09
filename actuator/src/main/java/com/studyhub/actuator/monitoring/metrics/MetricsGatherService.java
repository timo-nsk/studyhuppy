package com.studyhub.actuator.monitoring.metrics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class MetricsGatherService {
	@Value("${app.metrics.track}")
	private String TRACK_SERVICE_METRICS_URI;

	@Value("${app.metrics.kartei}")
	private String KARTEI_SERVICE_METRICS_URI;

	@Value("${app.metrics.mail}")
	private String MAIL_SERVICE_METRICS_URI;

	@Value("${app.metrics.authentication}")
	private String AUTHENTICATION_SERVICE_METRICS_URI;

	private final Logger log = LoggerFactory.getLogger(MetricsGatherService.class);


	public String getMetricsFrom(String uri) throws ExecutionException, InterruptedException {

		return WebClient.create()
				.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(String.class)
				.timeout(Duration.of(5, ChronoUnit.SECONDS))
				.toFuture()
				.get();
	}

	public Map<String, String> getSystemMetricsMap() {
		try {
			String trackMetrics = getMetricsFrom(TRACK_SERVICE_METRICS_URI);
			String karteiMetrics = getMetricsFrom(KARTEI_SERVICE_METRICS_URI);
			String mailMetrics = getMetricsFrom(MAIL_SERVICE_METRICS_URI);
			String authMetrics = getMetricsFrom(AUTHENTICATION_SERVICE_METRICS_URI);
			log.info("Metrics from services gathered successfully");
			return Map.of("track", trackMetrics, "kartei", karteiMetrics, "mail", mailMetrics, "authentication", authMetrics);
		} catch(ExecutionException | InterruptedException e) {
			System.out.println(e.getMessage());
			log.info("Gathering metrics from services failed");
			return null;
		}
	}
}
