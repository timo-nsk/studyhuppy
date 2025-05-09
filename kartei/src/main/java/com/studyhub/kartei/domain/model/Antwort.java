package com.studyhub.kartei.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Antwort {
	private String antwort;
	private boolean wahrheit;

	public void setWahrheit(boolean w) {
		wahrheit = w;
	}
}
