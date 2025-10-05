package com.studyhub.kartei.service.application;

import com.studyhub.kartei.domain.model.Karteikarte;

public interface KarteikarteRepository {

	Karteikarte save(Karteikarte karteikarte);

	Karteikarte findByFachId(String fachId);

	void updateLernstufen(String lernstufen, String karteId);
}
