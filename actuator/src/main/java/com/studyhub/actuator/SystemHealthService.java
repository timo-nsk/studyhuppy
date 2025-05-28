package com.studyhub.actuator;

import com.studyhub.actuator.monitoring.metrics.MetricsGatherService;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SystemHealthService {

	private final MetricsGatherService metricsService;
	private final Map<String, DatabaseHealthIndicator> databaseHealthIndicatorMap;
	private final Map<String, TrackHealthIndicator> trackHealthIndicatorMap;
	private final Map<String, AuthenticationHealthIndicator> authenticationHealthIndicatorMap;
	private final Map<String, KarteiHealthIndicator> karteiHealthIndicatorMap;
	private final Map<String, MailHealthIndicator> mailHealthIndicatorMap;

	public SystemHealthService(MetricsGatherService metricsService, Map<String, DatabaseHealthIndicator> databaseHealthIndicatorMap, Map<String, TrackHealthIndicator> trackHealthIndicatorMap, Map<String, AuthenticationHealthIndicator> authenticationHealthIndicatorMap, Map<String, KarteiHealthIndicator> karteiHealthIndicatorMap, Map<String, MailHealthIndicator> mailHealthIndicatorMap) {
		this.metricsService = metricsService;
		this.databaseHealthIndicatorMap = databaseHealthIndicatorMap;
		this.trackHealthIndicatorMap = trackHealthIndicatorMap;
		this.authenticationHealthIndicatorMap = authenticationHealthIndicatorMap;
		this.karteiHealthIndicatorMap = karteiHealthIndicatorMap;
		this.mailHealthIndicatorMap = mailHealthIndicatorMap;
	}

	public String getServiceHealthStatus(Map<String, ? extends HealthIndicator> indicatorMap) {
		List<HealthIndicator> indicators = List.copyOf(indicatorMap.values());
		//System.out.println("blub " + indicators.get(0).health().getStatus().toString());
		return indicators.get(0).health().getStatus().toString();
	}


	public List<SystemHealth> getSystemHealth() {
		SystemHealth trackHealth = new SystemHealth("Track", getServiceHealthStatus(trackHealthIndicatorMap), databaseHealthIndicatorMap.get("trackDatabaseService").health().getStatus().toString());
		SystemHealth karteiHealth = new SystemHealth("Kartei", getServiceHealthStatus(karteiHealthIndicatorMap), databaseHealthIndicatorMap.get("karteiDatabaseService").health().getStatus().toString());
		SystemHealth mailHealth = new SystemHealth("Mail", getServiceHealthStatus(mailHealthIndicatorMap), databaseHealthIndicatorMap.get("mailDatabaseService").health().getStatus().toString());
		SystemHealth authHealth = new SystemHealth("Authentication", getServiceHealthStatus(authenticationHealthIndicatorMap), databaseHealthIndicatorMap.get("authenticationDatabaseService").health().getStatus().toString());

		return List.of(trackHealth, karteiHealth, mailHealth, authHealth);
	}
}
