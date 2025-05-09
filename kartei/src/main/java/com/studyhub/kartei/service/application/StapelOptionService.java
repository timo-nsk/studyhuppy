package com.studyhub.kartei.service.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StapelOptionService {

	private final StapelRepository repo;
	private final Logger log = LoggerFactory.getLogger(StapelOptionService.class);

	public StapelOptionService(StapelRepository repo) {
		this.repo = repo;
	}

	public void deleteKarteiSet(String karteiSetId) {
		repo.deleteKarteiSet(karteiSetId);
		log.warn("deleted set with id '%s'".formatted(karteiSetId));
	}

	public void changeSetName(String karteiSetId, String newSetName) {
		repo.changeSetName(karteiSetId, newSetName);
		log.info("changed name of set '%s' to '%s'".formatted(karteiSetId, newSetName));
	}

	// TODO: resetlogik muss angepasst werden + testen
	public void resetAllKarteikarten(String karteiSetId) {
		repo.resetAllKarteikarten(karteiSetId);
		log.info("reset all karteikarten from set '%s'".formatted(karteiSetId));
	}

	public void deleteKarteikartenOfSet(String karteiSetId) {
		repo.deleteAllKarteikartenOfSet(karteiSetId);
		log.warn("deleted all karteikarten from set '%s'".formatted(karteiSetId));
	}
}