package com.studyhub.kartei.service.application;

import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.domain.model.Stapel;

import java.util.List;
import java.util.UUID;

public interface StapelRepository {
	Stapel save(Stapel set);
	List<Stapel> findAll();
	Stapel findByFachId(UUID fachId);
	void deleteKarteikarteByFachid(UUID karteToDelete);
	int countAll();

	void deleteKarteiSet(String karteiSetId);

	void changeSetName(String karteiSetId, String newSetName);

	void resetAllKarteikarten(String karteiSetId);

	void deleteAllKarteikartenOfSet(String karteiSetId);

	void updateLernIntervalle(String newLernIntervalle, UUID stapelFachId);

	int updateSetWithNewKarteikarte(String karteiSetId, Karteikarte karteikarte);

	List<Stapel> findByUsername(String username);

	boolean isStapelDbHealthy();
}
