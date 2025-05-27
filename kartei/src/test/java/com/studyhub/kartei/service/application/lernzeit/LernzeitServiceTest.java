package com.studyhub.kartei.service.application.lernzeit;

import com.studyhub.kartei.domain.model.KarteikarteGelerntEvent;
import com.studyhub.kartei.domain.model.Stapel;
import com.studyhub.kartei.service.application.StapelRepository;
import com.studyhub.kartei.service.application.lernzeit.KarteikarteGelerntEventRepository;
import com.studyhub.kartei.service.application.lernzeit.LernzeitService;
import com.studyhub.kartei.util.KarteikarteGelerntEventMother;
import com.studyhub.kartei.util.StapelMother;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LernzeitServiceTest {

	private static StapelRepository stapelRepository;
	private static KarteikarteGelerntEventRepository karteikarteGelerntEventRepository;
	private static LernzeitService lernzeitService;

	@BeforeAll
	public static void init() {
		stapelRepository = mock(StapelRepository.class);
		karteikarteGelerntEventRepository = mock(KarteikarteGelerntEventRepository.class);
		lernzeitService = new LernzeitService(stapelRepository, karteikarteGelerntEventRepository);
	}

	@Test
	@DisplayName("Alle Events für einen Stapel werden gefunden und die vorraussichtliche Lernzeit dafür berechnet.")
	void test_1() {
		LocalDateTime now = LocalDateTime.of(2025, 1, 15, 10, 0);
		UUID stapelId = UUID.randomUUID();
		List<KarteikarteGelerntEvent> events = KarteikarteGelerntEventMother.initEvents(stapelId);
		Stapel stapel = KarteikarteGelerntEventMother.initStapelWithKartenAndId(stapelId);
		when(karteikarteGelerntEventRepository.findByStapelId(stapelId)).thenReturn(events);
		when(stapelRepository.findByFachId(stapelId)).thenReturn(stapel);

		int vorraussichtlicheZeit = lernzeitService.getVorraussichtlicheLernzeitFürStapel(stapelId, now);

		assertThat(vorraussichtlicheZeit).isEqualTo(41);
	}

	@Test
	@DisplayName("Alle Events für einen Stapel werden gefunden und die vorraussichtliche Lernzeit dafür berechnet.")
	void test_2() {
		LocalDateTime now = LocalDateTime.of(2025, 1, 20, 10, 1);
		UUID stapelId = UUID.randomUUID();
		List<KarteikarteGelerntEvent> events = KarteikarteGelerntEventMother.initEvents(stapelId);
		Stapel stapel = KarteikarteGelerntEventMother.initStapelWithKartenAndId(stapelId);
		when(karteikarteGelerntEventRepository.findByStapelId(stapelId)).thenReturn(events);
		when(stapelRepository.findByFachId(stapelId)).thenReturn(stapel);

		int vorraussichtlicheZeit = lernzeitService.getVorraussichtlicheLernzeitFürStapel(stapelId, now);

		assertThat(vorraussichtlicheZeit).isEqualTo(93);
	}

	@Test
	@DisplayName("300s werden korrekt in Minuten formattiert")
	void test_3() {
		int sekunden = 300;

		String formatted = lernzeitService.calculateZeitAsString(sekunden);

		assertThat(formatted).isEqualTo("~5m");
	}

	@Test
	@DisplayName("1956s werden korrekt in Minuten formattiert")
	void test_4() {
		int sekunden = 1956;

		String formatted = lernzeitService.calculateZeitAsString(sekunden);

		assertThat(formatted).isEqualTo("~32m");
	}

	@Test
	@DisplayName("1956s werden korrekt in Minuten formattiert")
	void test_5() {
		int sekunden = 3600;

		String formatted = lernzeitService.calculateZeitAsString(sekunden);

		assertThat(formatted).isEqualTo("~1h");
	}

	@Test
	@DisplayName("1956s werden korrekt in Minuten formattiert")
	void test_6() {
		int sekunden = 3540;

		String formatted = lernzeitService.calculateZeitAsString(sekunden);

		assertThat(formatted).isEqualTo("~59m");
	}

	@Test
	@DisplayName("Wenn die approximierte Lernzeit größer als ein Wunschwert ist, kann sie angezeigt werden")
	void test_7() {
		// implement
	}

	@Test
	@DisplayName("Wenn die approximierte Lernzeit kleiner als ein Wunschwert ist, kann sie nicht angezeigt werden")
	void test_8() {
		// implement
	}

	@Test
	@DisplayName("Wenn keine Events existieren für einen Stapel, wird 0 zurückgegeben")
	void test_9() {
		LocalDateTime now = LocalDateTime.of(2025, 1, 15, 10, 0);
		UUID stapelId = UUID.randomUUID();
		List<KarteikarteGelerntEvent> events = List.of();
		Stapel stapel = KarteikarteGelerntEventMother.initStapelWithKartenAndId(stapelId);
		when(karteikarteGelerntEventRepository.findByStapelId(stapelId)).thenReturn(events);

		int vorraussichtlicheZeit = lernzeitService.getVorraussichtlicheLernzeitFürStapel(stapelId, now);

		assertThat(vorraussichtlicheZeit).isEqualTo(0);
	}

	@Test
	@DisplayName("Wenn der Stapel keine Karteikarten erhält, wird 0 zurückgegeben")
	void test_10() {
		LocalDateTime now = LocalDateTime.of(2025, 1, 15, 10, 0);
		UUID stapelId = UUID.randomUUID();
		List<KarteikarteGelerntEvent> events = KarteikarteGelerntEventMother.initEvents(stapelId);
		Stapel stapel = StapelMother.initSetWithoutKarteikarten();
		when(karteikarteGelerntEventRepository.findByStapelId(stapelId)).thenReturn(events);
		when(stapelRepository.findByFachId(stapelId)).thenReturn(stapel);

		int vorraussichtlicheZeit = lernzeitService.getVorraussichtlicheLernzeitFürStapel(stapelId, now);

		assertThat(vorraussichtlicheZeit).isEqualTo(0);
	}
}
