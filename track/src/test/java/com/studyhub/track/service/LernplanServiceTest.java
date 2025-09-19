package com.studyhub.track.service;

import com.studyhub.track.application.service.LernplanRepository;
import com.studyhub.track.application.service.LernplanService;
import com.studyhub.track.application.service.SessionRepository;
import com.studyhub.track.application.service.dto.LernplanWochenuebersicht;
import com.studyhub.track.domain.model.lernplan.Lernplan;
import com.studyhub.track.domain.model.session.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static com.studyhub.track.util.LernplanMother.*;
import static com.studyhub.track.util.SessionMother.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LernplanServiceTest {

	@Mock
	private LernplanRepository lernplanRepository;

	@Mock
	private SessionRepository sessionRepository;

	@InjectMocks
	private LernplanService lernplanService;

	@Test
	@DisplayName("Wenn ein Lernplan erfolgreich gespeichert wird, dann soll true zurueckgegeben werden.")
	void test_1() {
		Lernplan l = initFullLernplan();
		when(lernplanRepository.save(l)).thenReturn(l);

		boolean wasSaved = lernplanService.saveLernplan(l);

		assertThat(wasSaved).isTrue();
		verify(lernplanRepository, times(1)).save(l);
	}

	@Test
	@DisplayName("Wenn ein Lernplan erfolglos gespeichert wird, dann soll false zurueckgegeben werden.")
	void test_2() {
		Lernplan l = new Lernplan();
		when(lernplanRepository.save(any())).thenReturn(null);

		boolean wasSaved = lernplanService.saveLernplan(l);

		assertThat(wasSaved).isFalse();
		verify(lernplanRepository, times(1)).save(l);
	}

	@Test
	@DisplayName("Für einen User 'testuser' wird für seinen aktiv gesetzten Lernplan eine Wochenübersicht korrekt erstellt.")
	void test_3() {
		String username = "testuser";
		UUID sessionId = UUID.randomUUID();
		Lernplan aktiverLernplan = initFullLernplan(sessionId);
		Session s = createSessionWithTwoBlocks(sessionId);
		when(lernplanRepository.findActiveByUsername(username)).thenReturn(aktiverLernplan);
		when(sessionRepository.findSessionByFachId(sessionId)).thenReturn(s);
		LernplanWochenuebersicht expected = initFullLernplanWochenuebersicht(sessionId);

		LernplanWochenuebersicht actual = lernplanService.collectLernplanWochenuebersicht(username);

		assertThat(actual).isEqualTo(expected);
	}


}
