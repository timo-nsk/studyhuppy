package com.studyhub.track.adapter.web.controller;

import com.studyhub.track.application.service.ModulService;
import com.studyhub.track.domain.model.modul.Modul;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.studyhub.track.util.ModulMother.initModul;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Disabled
@WebMvcTest(StatisticController.class)
public class StatisticControllerTest {

	@Autowired
	MockMvc mvc;


	@MockitoBean
	ModulService modulService;

	@Test
	@DisplayName("/statistics/overview ist erreichbar und gibt das korrekte Template zurück")
	void test_01() throws Exception {
		mvc.perform(get("/statistics/overview"))
				.andExpect(status().isOk())
				.andExpect(view().name("overview"));
	}

	@Test
	@DisplayName("/statistics/overall ist erreichbar und gibt das korrekte Template zurück")
	void test_02() throws Exception {
		mvc.perform(get("/statistics/overall"))
				.andExpect(status().isOk())
				.andExpect(view().name("overall"));
	}

	@Test
	@DisplayName("/statistics/each-modul ist erreichbar und gibt das korrekte Template zurück")
	void test_03() throws Exception {
		mvc.perform(get("/statistics/each-modul"))
				.andExpect(status().isOk())
				.andExpect(view().name("each-module"));
	}

	@Test
	@DisplayName("/statistics/overview hat alle benötigten Attribute")
	void test_04() throws Exception {
		Modul m = initModul();
		//when(modulService.getTotalStudyTime()).thenReturn("00d 00h 00m 40s");
		//when(modulService.countActiveModules()).thenReturn(2);
		//when(modulService.countNotActiveModules()).thenReturn(2);
		when(modulService.findModulWithMaxSeconds()).thenReturn(m.getName());
		when(modulService.findModulWithMinSeconds()).thenReturn(m.getName());
		mvc.perform(get("/statistics/overview"))
				.andExpect(status().isOk())
				.andExpect(view().name("overview"))
				.andExpect(model().attribute("totalStudyTime", "00d 00h 00m 40s"))
				.andExpect(model().attribute("NumberActiveModules", 2))
				.andExpect(model().attribute("NumberNotActiveModules", 2))
				.andExpect(model().attribute("MaxStudiedModul", m.getName()))
				.andExpect(model().attribute("MinStudiedModul", m.getName()));
	}
}
