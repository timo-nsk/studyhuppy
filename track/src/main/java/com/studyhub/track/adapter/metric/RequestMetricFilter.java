package com.studyhub.track.adapter.metric;


import io.prometheus.client.Summary;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class RequestMetricFilter implements Filter {

    private Summary summary = Summary.build()
            .name("http_request_duration_in_seconds")
            .help("The time it took to process the request in seconds")
            .labelNames("method", "uri", "status")
            .register();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long startTime = System.nanoTime();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        filterChain.doFilter(request, response);
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String status = String.valueOf(response.getStatus());
        long endTime = System.nanoTime();
        double diff = ((double) endTime - startTime) / (double) TimeUnit.SECONDS.toNanos(1L);
        summary.labels(method, uri, status).observe(diff);
    }
}
