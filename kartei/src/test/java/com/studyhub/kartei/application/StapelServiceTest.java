package com.studyhub.kartei.application;

import com.studyhub.kartei.adapter.web.controller.RemoveAntwortRequest;
import com.studyhub.kartei.adapter.web.controller.StapelDashboardDto;
import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.domain.model.Stapel;
import com.studyhub.kartei.service.application.StapelRepository;
import com.studyhub.kartei.service.application.StapelService;
import com.studyhub.kartei.service.application.lernzeit.LernzeitService;
import com.studyhub.kartei.util.KarteikarteMother;
import com.studyhub.kartei.util.StapelMother;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class StapelServiceTest {

	static StapelRepository repo;
	static StapelService service;
	static LernzeitService lernzeitService;

	@BeforeAll
	static void init() {
		repo = mock(StapelRepository.class);
		lernzeitService = mock(LernzeitService.class);
		service = new StapelService(repo, lernzeitService);
	}

	@Test
	@DisplayName("Es wird eine Liste mit KarteikartenSet returned")
	void test_01() {
		List<Stapel> set = List.of(StapelMother.initSet());
		when(repo.findAll()).thenReturn(set);

		service.findAllStapel();

		verify(repo).findAll();

	}

	@Test
	@DisplayName("Es Set wird abgespeichert")
	void test_02() throws Exception {
		Stapel set = StapelMother.initSet();

		service.saveSet(set);

		verify(repo).save(any(Stapel.class));
	}

	@Test
	@DisplayName("Ein Set wird anhand seiner Fach-Id returned")
	void test_03() {
		Stapel set = StapelMother.initSet();
		when(repo.findByFachId(any(UUID.class))).thenReturn(set);

		Stapel found = service.findByFachId(UUID.randomUUID().toString());

		assertThat(found).isNotNull();
		verify(repo).findByFachId(any(UUID.class));
	}

	@Test
	@DisplayName("Eine Karteikarte in einem Set wird erfolgreich gelöscht")
	void test_04() {
		Stapel set = StapelMother.initSet();
		UUID setId = set.getFachId();
		UUID karteId = set.getKarteikarten().get(0).getFachId();
		when(repo.findByFachId(setId)).thenReturn(set);

		service.deleteKarteikarteByFachId(setId, karteId);

		assertThat(set.getKarteikarten().size()).isEqualTo(0);
		verify(repo).save(set);
	}

	@Test
	@DisplayName("Wenn keine Karteikarten-Sets existieren, wird true returned")
	void test_05() {
		when(repo.countAll()).thenReturn(0);

		assertThat(service.areKarteiSetsAvailable()).isFalse();
	}

	@Test
	@DisplayName("Wenn keine Karteikarten-Sets existieren, wird true returned")
	void test_06() {
		when(repo.countAll()).thenReturn(10);

		assertThat(service.areKarteiSetsAvailable()).isTrue();
	}

	@Test
	@DisplayName("Wenn keine Karteikarten-Sets existieren, wird true returned")
	void test_07() {
		when(repo.countAll()).thenReturn(10);

		assertThat(service.areKarteiSetsAvailable()).isTrue();
	}

	@Test
	@DisplayName("Wenn ein Karteikarten-Set existiert, wird true returned")
	void test_08() {
		String id = UUID.randomUUID().toString();
		Stapel set = StapelMother.initSet();
		when(repo.findByFachId(any(UUID.class))).thenReturn(set);

		assertThat(service.stapelNotExists(id)).isFalse();
	}

	@Test
	@DisplayName("Wenn ein Karteikarten-Set nicht existiert, wird false returned")
	void test_09() {
		String id = UUID.randomUUID().toString();
		when(repo.findByFachId(any(UUID.class))).thenReturn(null);

		assertThat(service.stapelNotExists(id)).isTrue();
	}

	@Test
	@DisplayName("Wenn ein Karteikarte wird anhand seiner Id aus dem Karteikarten-Set gefunden")
	void test_10() {
		Stapel set = StapelMother.initSet();
		String setId = set.getFachId().toString();
		String karteId = set.getKarteikarten().get(0).getFachId().toString();
		when(repo.findByFachId(any(UUID.class))).thenReturn(set);

		assertThat(service.findKarteikarteByFachId(setId, karteId)).isNotNull();
	}

	@Test
	@DisplayName("Wenn ein Karteikarten-Set noch nicht in der Session ist, wird es dort abgespeichert und das Set wird returned")
	void test_11() {
		HttpSession sessionMock = mock(HttpSession.class);
		Stapel set = StapelMother.initSet();
		when(sessionMock.getAttribute(anyString())).thenReturn(null);

		Stapel returnedSet = service.maybeGetHttpSessionForStapel(sessionMock, set);

		assertThat(returnedSet).isNotNull();
		verify(sessionMock).setAttribute("currentSet", returnedSet);
	}

	@Test
	@DisplayName("Wenn ein Karteikarten-Set schon in der Session ist, wird es von dort geholt und returned")
	void test_12() {
		HttpSession sessionMock = mock(HttpSession.class);
		Stapel set = StapelMother.initSet();
		when(sessionMock.getAttribute(anyString())).thenReturn(set);

		Stapel returnedSet = service.maybeGetHttpSessionForStapel(sessionMock, set);

		assertThat(returnedSet).isNotNull();
		verify(sessionMock, times(2)).getAttribute("currentSet");
	}

	@Test
	@DisplayName("Ein Karteikarten-Set wird erfolgreich aus der HttpSession gelöscht")
	void test_13() {
		HttpSession sessionMock = mock(HttpSession.class);

		service.removeSetFromSession(sessionMock);

		verify(sessionMock).removeAttribute("currentSet");
	}

	@Test
	@DisplayName("Wenn die Lern-Session soweit fortgeschreitet ist, dass der Index für die Liste die größe der " +
			"karteikarten-Liste erreicht, wird true returned")
	void test_14() {
		int index = 10;
		int listSize = 10;

		assertThat(service.isLastKarteikarteReached(listSize, index)).isTrue();
	}

	@Test
	@DisplayName("Wenn die Lern-Session noch nicht soweit fortgeschreitet ist, dass der Index für die Liste die größe der " +
			"karteikarten-Liste erreicht, wird false returned")
	void test_15() {
		int index = 10;
		int listSize = 9;

		assertThat(service.isLastKarteikarteReached(listSize, index)).isFalse();
	}

	@Test
	@DisplayName("Wenn der Index größer als die Karteikarten-Listengröße ist, wird ein Fehler geworfen")
	void test_16() {
		int index = 10;
		int listSize = 9;

		// TODO: assertThrows...
		assertThat(service.isLastKarteikarteReached(listSize, index)).isFalse();
	}

	@Test
	@DisplayName("Für das updaten eines Stapels mit einer neuen Karteikarte wird das repo aufgerufen")
	void test_17() throws Exception {
		Karteikarte k = KarteikarteMother.newKarteikarte("f", "a");
		String stapelId = UUID.randomUUID().toString();

		service.updateSetWithNewKarteikarte(stapelId, k);

		verify(repo).updateSetWithNewKarteikarte(stapelId, k);
	}

	@Test
	@DisplayName("Die Anzahl fälliger Karteikarten aller Stapel wird als Map mit key=Stapelname, value=AnzahlFälligerKarten korrekt berechnet")
	void test_18() {
		List<Stapel> allStapel = StapelMother.initManyStapel();
		when(repo.findAll()).thenReturn(allStapel);

		Map<String, Integer> resultMap = service.getAnzahlFaelligeKartenForEachStapel(LocalDateTime.of(2025, 1, 5, 10, 0));

		assertThat(resultMap).isNotNull();
		assertThat(resultMap.size()).isEqualTo(3);
		assertThat(resultMap.get("stapel1")).isEqualTo(1);
		assertThat(resultMap.get("stapel2")).isEqualTo(2);
		assertThat(resultMap.get("stapel3")).isEqualTo(3);
	}

	@Test
	@DisplayName("Benötigte Daten eines Stapels werden korrekt in ein Dto gepackt")
	void test_19() {
		List<Stapel> stapel = StapelMother.initManyStapel();
		when(lernzeitService.getVorraussichtlicheLernzeitFürStapel(any(UUID.class), any(LocalDateTime.class))).thenReturn(1);

		List<StapelDashboardDto> dtos = service.prepareDashboardInfo(stapel);

		assertThat(dtos.size()).isEqualTo(3);
		assertThat(dtos.get(0).name()).isEqualTo("stapel1");
		assertThat(dtos.get(0).fachId()).isEqualTo("40c0ea04-fb47-4fbe-a512-76280cb7dd12");
		assertThat(dtos.get(0).vorraussichtlicheLernzeit()).isNotNull();
		assertThat(dtos.get(0).anzahlNeueKarteikarten()).isEqualTo(0);
		assertThat(dtos.get(0).anzahlFaelligeKarteikarten()).isEqualTo(4);
	}

	@Test
	@DisplayName("Finde Stapel, der nur die fälligen Karteikarten enthält")
	void test_20() {
		Stapel stapel = StapelMother.initSetWithALotKarteikarten();
		UUID stapelId = stapel.getFachId();
		LocalDateTime today = LocalDateTime.of(2025, 1, 20, 10, 0, 0);
		when(repo.findByFachId(any())).thenReturn(stapel);

		Stapel res = service.findByFachIdWithFaelligeKarten(stapelId.toString(), today);

		assertThat(res.getKarteikarten().size()).isEqualTo(5);
	}

	@Test
	@DisplayName("Wenn ein Antwort aus einer Stapel gelöscht wurde, wird true zurückgegeben und keine Fehlermeldung geworfen")
	void test_21() {
		Stapel s = StapelMother.stapelWithSingleChoiceKarteikarte();
		String karteId = s.getKarteikarten().get(0).getFachId().toString();
		String stapelId = s.getFachId().toString();
		RemoveAntwortRequest req = new RemoveAntwortRequest(stapelId, karteId, 1);
		when(repo.findByFachId(any())).thenReturn(s);

		boolean res = service.removeAntwortFromKarte(req);

		assertThat(res).isTrue();
	}

	@Test
	@DisplayName("Wenn ein Antwortindex geschickt wird, zu dem keine Antwort in einer karteikarte existiert, wird beim Löschen eine Exception geworfen")
	void test_22() {
		Stapel s = StapelMother.stapelWithSingleChoiceKarteikarte();
		String karteId = s.getKarteikarten().get(0).getFachId().toString();
		String stapelId = s.getFachId().toString();
		RemoveAntwortRequest req = new RemoveAntwortRequest(stapelId, karteId, 10);
		when(repo.findByFachId(any())).thenReturn(s);


		assertThrows(IndexOutOfBoundsException.class, () -> service.removeAntwortFromKarte(req));
	}
}
