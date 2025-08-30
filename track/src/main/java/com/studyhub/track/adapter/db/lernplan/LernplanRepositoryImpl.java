package com.studyhub.track.adapter.db.lernplan;

import com.studyhub.track.application.service.LernplanRepository;
import com.studyhub.track.domain.model.lernplan.Lernplan;
import org.springframework.stereotype.Repository;

import java.util.List;

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

	@Override
	public Lernplan findActiveByUsername(String username) {
		LernplanDto dto = lernplanDao.findActiveByUsername(username);
		if (dto == null) {
			return null;
		}
		return toDomain(dto);
	}

	@Override
	public List<Lernplan> findAllByUsername(String username) {
		List<LernplanDto> dtos = lernplanDao.findAllByUsername(username);
		return dtos.stream().map(LernplanMapper::toDomain).toList();
	}
}
