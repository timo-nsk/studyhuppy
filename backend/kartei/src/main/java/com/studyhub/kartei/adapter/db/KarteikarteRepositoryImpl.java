package com.studyhub.kartei.adapter.db;

import com.studyhub.kartei.adapter.db.dto.KarteikarteDto;
import com.studyhub.kartei.adapter.db.mapper.KarteikarteMapper;
import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.service.application.KarteikarteRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class KarteikarteRepositoryImpl implements KarteikarteRepository {

	private KarteikarteDao dao;

	public KarteikarteRepositoryImpl(KarteikarteDao dao) {
		this.dao = dao;
	}

	@Override
	public Karteikarte save(Karteikarte karteikarte) {
		Integer existingKey = dao.findByFachId(karteikarte.getFachId()).map(KarteikarteDto::id).orElse(null);
		return KarteikarteMapper.toKarteikarte(dao.save(KarteikarteMapper.toKarteikarteDto(karteikarte, existingKey)));
	}

	@Override
	public Karteikarte findByFachId(String fachId) {
		Optional<KarteikarteDto> karte = dao.findByFachId(UUID.fromString(fachId));
		return karte.map(KarteikarteMapper::toKarteikarte).orElse(null);
	}

	@Override
	public void updateLernstufen(String lernstufen, String karteId) {
		dao.updateLernstufen(lernstufen, UUID.fromString(karteId));
	}
}
