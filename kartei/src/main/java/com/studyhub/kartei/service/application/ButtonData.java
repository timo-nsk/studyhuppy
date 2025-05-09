package com.studyhub.kartei.service.application;

import com.studyhub.kartei.domain.model.Schwierigkeit;

public record ButtonData(String btnTitel, Schwierigkeit schwierigkeit) {

	public String getSchwierigkeitUpperCamelCase() {
		return schwierigkeit.toString().substring(0, 1).toUpperCase() + schwierigkeit.toString().substring(1).toLowerCase();
	}
}
