package com.studyhub.track.application.service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DateProvider {
	public LocalDate getTodayDate() {
		return LocalDate.now();
	}
}
