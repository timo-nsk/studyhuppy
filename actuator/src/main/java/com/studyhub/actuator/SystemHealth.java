package com.studyhub.actuator;

public class SystemHealth {
	private String system;
	private String liveness;
	private String readiness;

	public SystemHealth(String system, String liveness, String readiness) {
		this.system = system;
		this.liveness = liveness;
		this.readiness = readiness;
	}

	public String getReadiness() {
		return readiness;
	}

	public String getSystem() {
		return system;
	}

	public String getLiveness() {
		return liveness;
	}


}
