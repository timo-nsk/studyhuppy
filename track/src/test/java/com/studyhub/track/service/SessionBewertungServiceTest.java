package com.studyhub.track.service;

import com.studyhub.track.application.service.SessionBeendetEventRepository;
import com.studyhub.track.application.service.SessionBewertungService;
import com.studyhub.track.application.service.SessionBewertungStatistikDto;
import com.studyhub.track.domain.model.session.SessionBeendetEvent;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static com.studyhub.track.util.SessionBeendetEventMother.initEventsOfUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessionBewertungServiceTest {

	private static String USER = "peter93";

	@Mock
	private SessionBeendetEventRepository sessionBeendetEventRepository;

	@InjectMocks
	private SessionBewertungService sessionBewertungService;

	@Test
	@DisplayName("Für einen User 'peter93' wird der Durchschnitt der Konzentrationsbewertung korrekt berechnet")
	void testGetAverageKonzentrationBewertungByUsername() {
		List<SessionBeendetEvent> events = initEventsOfUser(USER);
		when(sessionBeendetEventRepository.findAllByUsername(USER))
			.thenReturn(events);

		Double average = sessionBewertungService.getAverageKonzentrationBewertungByUsername(USER);

		assertThat(average).isEqualTo(5.0);
	}

	@Test
	@DisplayName("Für einen User 'peter93' wird der Durchschnitt der Produktivitätsbewertung korrekt berechnet")
	void testGetAverageProduktivitaetBewertungByUsername() {
		List<SessionBeendetEvent> events = initEventsOfUser(USER);
		when(sessionBeendetEventRepository.findAllByUsername(USER))
				.thenReturn(events);

		Double average = sessionBewertungService.getAverageProduktivitaetBewertungByUsername(USER);

		assertThat(average).isEqualTo(6.0);
	}

	@Test
	@DisplayName("Für einen User 'peter93' wird der Durchschnitt der Schwierigkeitsbewertung korrekt berechnet")
	void testGetAverageSchwierigkeitBewertungByUsername() {
		List<SessionBeendetEvent> events = initEventsOfUser(USER);
		when(sessionBeendetEventRepository.findAllByUsername(USER))
				.thenReturn(events);

		Double average = sessionBewertungService.getAverageSchwierigkeitBewertungByUsername(USER);

		assertThat(average).isEqualTo(7.0);
	}

	@Test
	@DisplayName("Ein SessionBewertungStatistikDto wird korrekt für einen User wiedergegeben")
	void test_04() {
		List<SessionBeendetEvent> events = initEventsOfUser(USER);
		when(sessionBeendetEventRepository.findAllByUsername(USER))
				.thenReturn(events);

		SessionBewertungStatistikDto statistikDto = sessionBewertungService.getSessionBewertungStatistikByUsername(USER);

		assertThat(statistikDto.konzentrationBewertung()).isEqualTo(5.0);
		assertThat(statistikDto.produktivitaetBewertung()).isEqualTo(6.0);
		assertThat(statistikDto.schwierigkeitBewertung()).isEqualTo(7.0);
	}
}