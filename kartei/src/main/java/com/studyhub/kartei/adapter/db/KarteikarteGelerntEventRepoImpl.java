package com.studyhub.kartei.adapter.db;

import com.studyhub.kartei.adapter.db.mapper.KarteikarteGelerntEventMapper;
import com.studyhub.kartei.domain.model.KarteikarteGelerntEvent;
import com.studyhub.kartei.service.application.lernzeit.KarteikarteGelerntEventRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

import static com.studyhub.kartei.adapter.db.mapper.KarteikarteGelerntEventMapper.*;

@Repository
public class KarteikarteGelerntEventRepoImpl implements KarteikarteGelerntEventRepository {

	private KarteikarteGelerntEventDao dao;

	public KarteikarteGelerntEventRepoImpl(KarteikarteGelerntEventDao dao) {
		this.dao = dao;
	}

	@Override
	public KarteikarteGelerntEvent save(KarteikarteGelerntEvent event) {
		return toKarteikarteGelerntEvent(dao.save(toKarteikarteGelerntEventDto(event)));
	}

	@Override
	public List<KarteikarteGelerntEvent> findByKarteikarteId(UUID karteikarteId) {
		return dao.findByKarteikarteId(karteikarteId).stream().map(KarteikarteGelerntEventMapper::toKarteikarteGelerntEvent).toList();
	}

	@Override
	public int sumOfSecondsNeededForKarteikarte(UUID karteikarteId) {
		return dao.sumOfSecondsNeededForKarteikarte(karteikarteId);
	}

	@Override
	public int coundByKarteikarteId(UUID karteikarteId) {
		return dao.coundByKarteikarteId(karteikarteId);
	}

	@Override
	public double getAverageSecondsNeededForKarte(UUID karteikarteId) {
		int summe = sumOfSecondsNeededForKarteikarte(karteikarteId);
		int anzahl = coundByKarteikarteId(karteikarteId);
		return (double) summe /anzahl;
	}

	@Override
	public List<KarteikarteGelerntEvent> findByStapelId(UUID stapelId) {
		return dao.findByStapelId(stapelId);
	}

	@Override
	public void deleteAllByKarteiFachId(UUID karteiFachId) {
		dao.deleteAllByKarteiFachId(karteiFachId);
	}
}
