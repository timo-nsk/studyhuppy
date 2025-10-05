package com.studyhub.track.application.service;

public record SessionBewertungStatistikDto(
		Double konzentrationBewertung,
		Double produktivitaetBewertung,
		Double schwierigkeitBewertung
) {
}
