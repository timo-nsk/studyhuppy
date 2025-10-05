package com.studyhub.track.metric;

import com.studyhub.track.adapter.metric.PrometheusMetrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class PrometheusMetricsTest {

	@Test
	@DisplayName("incrementTotalRequests inkrementiert den Counter korrekt um 2")
	void incrementTotalRequests_shouldIncreaseCounter() {
		SimpleMeterRegistry registry = new SimpleMeterRegistry();
		PrometheusMetrics prometheusMetrics = new PrometheusMetrics(registry);
		double before = prometheusMetrics.getTotalRequestsCount();

		prometheusMetrics.incrementTotalRequests();
		prometheusMetrics.incrementTotalRequests();

		assertThat(before).isZero();
		assertThat(prometheusMetrics.getTotalRequestsCount()).isEqualTo(2.0);
	}
}
