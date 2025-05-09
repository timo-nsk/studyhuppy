package com.studyhub.kartei.application;

import com.studyhub.kartei.domain.model.Schwierigkeit;
import com.studyhub.kartei.service.application.ButtonData;
import com.studyhub.kartei.service.application.ButtonDataGenerator;
import com.studyhub.kartei.util.KarteikarteMother;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ButtonDataGeneratorTest {

	@Test
	@DisplayName("Korrekte Instanziierung")
	void test_1() {
		ButtonDataGenerator gen = new ButtonDataGenerator(KarteikarteMother.newKarteikarteWithLernstufen("2m,2h,2d"));

		assertThat(gen).isNotNull();
		assertThat(gen.getKartekarte()).isNotNull();
		assertThat(gen.getButtonDataList()).isNotNull();

	}

	@Test
	@DisplayName("Es wird eine korrekte ButtonData-Liste für eine Karteikarte berechnet")
	void test_2() {
		ButtonDataGenerator gen = new ButtonDataGenerator(KarteikarteMother.newKarteikarteWithLernstufen("2m,2h,2d"));

		List<ButtonData> l = gen.generateButtons();

		assertThat(l.get(0)).isEqualTo(new ButtonData("2m", Schwierigkeit.HARD));
		assertThat(l.get(1)).isEqualTo(new ButtonData("2h", Schwierigkeit.NORMAL));
		assertThat(l.get(2)).isEqualTo(new ButtonData("2d", Schwierigkeit.EASY));
		assertThat(l.size()).isEqualTo(3);
	}
}
