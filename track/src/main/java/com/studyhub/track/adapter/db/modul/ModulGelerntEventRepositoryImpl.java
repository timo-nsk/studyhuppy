package com.studyhub.track.adapter.db.modul;

import com.studyhub.track.application.service.ModulGelerntEventRepository;
import com.studyhub.track.domain.model.modul.ModulGelerntEvent;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

import static com.studyhub.track.adapter.db.modul.ModulGelerntEventMapper.*;

@Repository
public class ModulGelerntEventRepositoryImpl implements ModulGelerntEventRepository {

	private final ModulGelerntEventDao dao;

	public ModulGelerntEventRepositoryImpl(ModulGelerntEventDao dao) {
		this.dao = dao;
	}

	@Override
	public ModulGelerntEvent save(ModulGelerntEvent event) {
		return toModulGelerntEvent(dao.save(toDto(event)));
	}

	@Override
	public int getSumSecondsLearned(LocalDate date, String username, UUID modulId) {
		return dao.getSumSecondsLearned(date, username, modulId);
	}
}
