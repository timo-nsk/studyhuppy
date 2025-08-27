package com.studyhub.track.adapter.db.lernplan;

import com.studyhub.track.application.service.LernplanRepository;
import com.studyhub.track.domain.model.lernplan.Lernplan;
import org.springframework.stereotype.Repository;

import static com.studyhub.track.adapter.db.lernplan.LernplanMapper.toDomain;
import static com.studyhub.track.adapter.db.lernplan.LernplanMapper.toDto;

@Repository
public class LernplanRepositoryImpl implements LernplanRepository {

	private final LernplanDao lernplanDao;

	public LernplanRepositoryImpl(LernplanDao lernplanDao) {
		this.lernplanDao = lernplanDao;
	}

	@Override
	public Lernplan save(Lernplan lernplan) {
		LernplanDto dto = toDto(lernplan);
		return  toDomain(lernplanDao.save(dto));
	}
}
