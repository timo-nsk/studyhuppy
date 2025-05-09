package com.studyhub.track.service;

import com.studyhub.jwt.JWTService;
import com.studyhub.track.application.service.*;
import com.studyhub.track.domain.model.modul.Modul;
import com.studyhub.track.util.ModulMother;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ModulEventServiceTest {
	static ModulRepository modulRepo;
	static ModulGelerntEventRepository eventRepo;
	static JWTService jwtService;
	static ModulEventService service;
	static DateProvider dateProvider;

	@BeforeAll
	static void init() {
		modulRepo = mock(ModulRepository.class);
		eventRepo = mock(ModulGelerntEventRepository.class);
		jwtService = mock(JWTService.class);
		dateProvider = mock(DateProvider.class);
		service = new ModulEventService(eventRepo, modulRepo, jwtService, dateProvider);
	}

	@Test
	@DisplayName("Die Lerndaten der letzten 7 Tage werden für einen User und den in dieser Zeit gelernten Module korrekt zurückgegeben.")
	void test_01() {
		List<Modul> modules = List.of(
				ModulMother.initModulWithNameAndUsername("Mathe", "timo123"),
				ModulMother.initModulWithNameAndUsername("AlDat", "timo123"));

		when(jwtService.extractUsername("token")).thenReturn("timo123");
		when(modulRepo.findByUsername("timo123")).thenReturn(modules);
		when(dateProvider.getTodayDate()).thenReturn(LocalDate.of(2025, 1, 10));
		when(eventRepo.getSumSecondsLearned(any(), any(), any())).thenReturn(20);

		Map<LocalDate, List<ModulStat>> res = service.getStatisticsForRecentDays(7, "token");

		assertThat(res.size()).isEqualTo(7);
		List<ModulStat> stats = res.get(LocalDate.of(2025, 1, 4));
		assertThat(stats.size()).isEqualTo(2);
		assertThat(stats.get(0).secondsLearned()).isEqualTo("20");
		assertThat(stats.get(1).secondsLearned()).isEqualTo("20");
		assertThat(stats.get(1).modulName()).isEqualTo("AlDat");
	}

	@Test
	@DisplayName("Wenn in den letzten x Tagen nicht gelernt wurde, gibt es auch keine Statistiken")
	void test_02() {
		List<Modul> modules = List.of(
				ModulMother.initModulWithNameAndUsername("Mathe", "timo123"),
				ModulMother.initModulWithNameAndUsername("AlDat", "timo123"));

		when(jwtService.extractUsername("token")).thenReturn("timo123");
		when(modulRepo.findByUsername("timo123")).thenReturn(modules);
		when(dateProvider.getTodayDate()).thenReturn(LocalDate.of(2025, 1, 10));
		when(eventRepo.getSumSecondsLearned(any(), any(), any())).thenReturn(0);

		Map<LocalDate, List<ModulStat>> res = service.getStatisticsForRecentDays(7, "token");

		assertThat(res.size()).isEqualTo(0);
	}
}
