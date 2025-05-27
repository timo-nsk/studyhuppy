package com.studyhub.kartei.service.application;

import com.studyhub.kartei.domain.model.FrageTyp;
import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.domain.model.Schwierigkeit;
import com.studyhub.kartei.domain.model.Stapel;
import com.studyhub.kartei.service.application.KarteikarteRepository;
import com.studyhub.kartei.service.application.KarteikarteService;
import com.studyhub.kartei.service.application.StapelRepository;
import com.studyhub.kartei.service.application.UpdateInfo;
import com.studyhub.kartei.service.application.lernzeit.KarteikarteGelerntEventRepository;
import com.studyhub.kartei.util.KarteikarteMother;
import com.studyhub.kartei.util.StapelMother;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class KarteikarteServiceTest {

	private static StapelRepository stapelRepository;
	private static KarteikarteRepository karteikarteRepository;
	private static KarteikarteGelerntEventRepository eventRepo;
	private static KarteikarteService karteikarteService;
	private static LocalDateTime now;

	@BeforeAll
	static void init() {
		stapelRepository = mock(StapelRepository.class);
		karteikarteRepository = mock(KarteikarteRepository.class);
		eventRepo = mock(KarteikarteGelerntEventRepository.class);
		karteikarteService = new KarteikarteService(stapelRepository, karteikarteRepository, eventRepo);
		now = LocalDateTime.now();
	}

	@Test
	@DisplayName("Frage/Antwort/Notiz einer Karteikarte wird erfolgreich geupdated")
	void test_1() {
		UUID stapelId = UUID.randomUUID();
		Stapel stapel = mock(Stapel.class);
		List<Karteikarte> karten = new ArrayList<>();
		Karteikarte karte = Karteikarte.initNewKarteikarte("Frage1", "Antwort1", "Notiz1", FrageTyp.NORMAL);
		Karteikarte editedKarte = Karteikarte.initNewKarteikarte("EditedFrage", "EditedAntwort", "EditedNotiz", FrageTyp.NORMAL);
		karten.add(karte);
		when(stapelRepository.findByFachId(stapelId)).thenReturn(stapel);
		when(stapel.getKarteikarten()).thenReturn(karten);

		boolean erfolg = karteikarteService.editNormalKarteikarte(stapelId.toString(), karte.getFachId().toString(), editedKarte);

		Karteikarte assertingKarte = karten.get(0);
		assertThat(assertingKarte.getFrage()).isEqualTo("EditedFrage");
		assertThat(assertingKarte.getAntwort()).isEqualTo("EditedAntwort");
		assertThat(assertingKarte.getNotiz()).isEqualTo("EditedNotiz");
		assertThat(assertingKarte.getFachId().toString()).isEqualTo(karte.getFachId().toString());
		assertThat(assertingKarte.getFachId().toString()).isNotEqualTo(editedKarte.getFachId().toString());
		assertThat(erfolg).isTrue();
		verify(stapelRepository, times(1)).save(stapel);
	}

	@Test
	@DisplayName("Karteikarte kann nicht geupdated werden, wenn die editierte Karte nicht vorhanden ist")
	void test_2() {
		boolean erfolg = karteikarteService.editNormalKarteikarte(UUID.randomUUID().toString(), UUID.randomUUID().toString(), null);

		assertThat(erfolg).isFalse();
	}

	@Test
	@DisplayName("Karteikarte kann nicht geupdated werden, wenn die Stapel-Id nicht vorhanden ist")
	void test_3() {
		Karteikarte editedKarte = Karteikarte.initNewKarteikarte("EditedFrage", "EditedAntwort", "EditedNotiz", FrageTyp.NORMAL);

		boolean erfolg = karteikarteService.editNormalKarteikarte("", UUID.randomUUID().toString(), editedKarte);

		assertThat(erfolg).isFalse();
	}

	@Test
	@DisplayName("Karteikarte kann nicht geupdated werden, wenn die Karteikarte-Id nicht vorhanden ist")
	void test_4() {
		Karteikarte editedKarte = Karteikarte.initNewKarteikarte("EditedFrage", "EditedAntwort", "EditedNotiz", FrageTyp.NORMAL);

		boolean erfolg = karteikarteService.editNormalKarteikarte(UUID.randomUUID().toString(), "", editedKarte);

		assertThat(erfolg).isFalse();
	}

	@Test
	@DisplayName("Eine Karteikarte wird für den nächsten Review-Termin erfolgreich prepariert, falls die Karte beim Lernen als EASY eingestuft wurde")
	void test_5() {
		String stapelId = UUID.randomUUID().toString();
		String karteId = UUID.randomUUID().toString();
		UpdateInfo info = new UpdateInfo(stapelId, karteId, Schwierigkeit.EASY, 0);
		Stapel stapel = StapelMother.initSetWithIds(stapelId, karteId);
		Karteikarte karteToUpdate = stapel.findKarteikarteByFachId(karteId);
		when(stapelRepository.findByFachId(UUID.fromString(stapelId))).thenReturn(stapel);

		karteikarteService.updateKarteikarteForNextReview(info);

		assertThat(karteToUpdate.getFaelligAm().toLocalDate()).isEqualTo(LocalDateTime.now().plusDays(10).toLocalDate());
		verify(stapelRepository).save(stapel);
	}

	@Test
	@DisplayName("Eine Karteikarte wird für den nächsten Review-Termin erfolgreich prepariert, falls die Karte beim Lernen als NORMAL eingestuft wurde")
	void test_6() {
		String stapelId = UUID.randomUUID().toString();
		String karteId = UUID.randomUUID().toString();
		UpdateInfo info = new UpdateInfo(stapelId, karteId, Schwierigkeit.NORMAL, 0);
		Stapel stapel = StapelMother.initSetWithIds(stapelId, karteId);
		Karteikarte karteToUpdate = stapel.findKarteikarteByFachId(karteId);
		when(stapelRepository.findByFachId(UUID.fromString(stapelId))).thenReturn(stapel);

		karteikarteService.updateKarteikarteForNextReview(info);

		assertThat(karteToUpdate.getFaelligAm().toLocalDate()).isEqualTo(LocalDateTime.now().plusHours(10).toLocalDate());
		verify(stapelRepository).save(stapel);
	}

	@Test
	@DisplayName("Eine Karteikarte wird für den nächsten Review-Termin erfolgreich prepariert, falls die Karte beim Lernen als HARD eingestuft wurde")
	void test_7() {
		String stapelId = UUID.randomUUID().toString();
		String karteId = UUID.randomUUID().toString();
		UpdateInfo info = new UpdateInfo(stapelId, karteId, Schwierigkeit.HARD, 0);
		Stapel stapel = StapelMother.initSetWithIds(stapelId, karteId);
		Karteikarte karteToUpdate = stapel.findKarteikarteByFachId(karteId);
		when(stapelRepository.findByFachId(UUID.fromString(stapelId))).thenReturn(stapel);

		karteikarteService.updateKarteikarteForNextReview(info);

		assertThat(karteToUpdate.getFaelligAm().toLocalDate()).isEqualTo(LocalDateTime.now().plusMinutes(10).toLocalDate());
		verify(stapelRepository).save(stapel);
	}

	@Test
	@DisplayName("Wenn die Zeiteinheit weder 'm', 'h' oder 'd' ist, wird das originale Fälligkeitsdatum zurückgegeben")
	void test_8() {
		LocalDateTime now = LocalDateTime.now();

		LocalDateTime computed = karteikarteService.addTimeToDate(now, 'z', 10);

		assertThat(computed).isEqualTo(now);
	}

	@Test
	@DisplayName("Wenn eine Karteikarte existiert, wird false zurückgegeben")
	void test_9() {
		Karteikarte k = KarteikarteMother.newKarteikarte("f", "a");
		when(karteikarteRepository.findByFachId(k.getFachId().toString())).thenReturn(k);

		boolean exists = karteikarteService.karteNotExistsById(k.getFachId().toString());

		assertThat(exists).isFalse();
	}

	@Test
	@DisplayName("Wenn eine Karteikarte nicht existiert, wird true zurückgegeben")
	void test_10() {
		when(karteikarteRepository.findByFachId(anyString())).thenReturn(null);

		boolean exists = karteikarteService.karteNotExistsById(UUID.randomUUID().toString());

		assertThat(exists).isTrue();
	}
}
