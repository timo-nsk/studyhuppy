package com.studyhub.track.application.service;

import com.studyhub.track.domain.model.modul.ModulGelerntEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ModulGelerntEventRepository {
	ModulGelerntEvent save(ModulGelerntEvent event);
	int getSumSecondsLearned(LocalDate date, String username, UUID modulId);

	List<ModulGelerntEvent> getAllByUsername(String username);
}
