package com.studyhub.kartei.application;

import com.studyhub.kartei.service.application.StapelOptionService;
import com.studyhub.kartei.service.application.StapelRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class StapelOptionServiceTest {

	private static StapelRepository stapelRepository;
	private static StapelOptionService optionService;
	private static Logger log;

	@BeforeAll
	static void init() {
		stapelRepository = mock(StapelRepository.class);
		optionService = new StapelOptionService(stapelRepository);
		log = mock(Logger.class);
	}

	@Test
	@DisplayName("Repo wird für das Löschen eines Stapels aufgerufen")
	void test_1() {
		optionService.deleteKarteiSet(anyString());

		verify(stapelRepository).deleteKarteiSet(anyString());
	}

	@Test
	@DisplayName("Repo wird für das Ändern eines Stapel-Namen aufgerufen")
	void test_2() {
		optionService.changeSetName(anyString(), anyString());

		verify(stapelRepository).changeSetName(anyString(), anyString());
	}

	@Disabled
	@Test
	@DisplayName("Repo wird für das Zurücksetzen aller Karten aufgerufen")
	void test_3() {
		//TODO: implement
	}

	@Test
	@DisplayName("Repo wird für das Löschen aller Karteikarten eines Stapels aufgerufen")
	void test_4() {
		optionService.deleteKarteikartenOfSet(anyString());

		verify(stapelRepository).deleteAllKarteikartenOfSet(anyString());
	}


}
