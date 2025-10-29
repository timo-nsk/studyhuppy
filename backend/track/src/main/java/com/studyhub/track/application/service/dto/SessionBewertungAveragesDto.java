package com.studyhub.track.application.service.dto;

public record SessionBewertungAveragesDto(
	Double averageKonzentrationBewertung,
	Double averageProduktivitaetBewertung,
	Double averageSchwierigkeitBewertung
) {
}
