package com.studyhub.kartei.service.application.lernzeit;

import com.studyhub.kartei.domain.model.KarteikarteGelerntEvent;

import java.util.List;
import java.util.UUID;

public interface KarteikarteGelerntEventRepository {
	KarteikarteGelerntEvent save(KarteikarteGelerntEvent event);
	List<KarteikarteGelerntEvent> findByKarteikarteId(UUID karteikarteId);

	int sumOfSecondsNeededForKarteikarte(UUID karteikarteId);

	int coundByKarteikarteId(UUID karteikarteId);

	double getAverageSecondsNeededForKarte(UUID karteikarteId);

	List<KarteikarteGelerntEvent> findByStapelId(UUID stapelId);

	void deleteAllByKarteiFachId(UUID karteiFachId);

	void deleteAllByStapelId(UUID stapelId);
}
