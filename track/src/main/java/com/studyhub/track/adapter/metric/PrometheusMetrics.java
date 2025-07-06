package com.studyhub.track.adapter.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class PrometheusMetrics {

    private final Counter totalRequests;

    public PrometheusMetrics(MeterRegistry registry) {
        this.totalRequests = Counter.builder("requests")
                .description("Total number of requests")
                .register(registry);
    }

    public void incrementTotalRequests() {
        totalRequests.increment();
    }
}