package com.studyhub.kartei.application;

import com.studyhub.kartei.domain.model.Schwierigkeit;
import com.studyhub.kartei.service.application.ButtonData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ButtonDataTest {

	@Test
	@DisplayName("btnTitel korrekt zurückgeben")
	void test_1() {
		ButtonData buttonData = new ButtonData("Start", Schwierigkeit.NORMAL);

		assertThat(buttonData.btnTitel()).isEqualTo("Start");
	}

	@Test
	@DisplayName("schwierigkeit korrekt zurückgeben")
	void test_2() {
		ButtonData buttonData = new ButtonData("Test", Schwierigkeit.EASY);

		assertThat(buttonData.schwierigkeit()).isEqualTo(Schwierigkeit.EASY);
	}

	@Test
	@DisplayName("Schwierigkeit-Feld wird korrekt formatiert HARD -> Hard")
	void test_3() {
		ButtonData buttonData1 = new ButtonData("Test1", Schwierigkeit.HARD);
		ButtonData buttonData2 = new ButtonData("Test2", Schwierigkeit.NORMAL);
		ButtonData buttonData3 = new ButtonData("Test3", Schwierigkeit.EASY);

		assertThat(buttonData1.getSchwierigkeitUpperCamelCase()).isEqualTo("Hard");
		assertThat(buttonData2.getSchwierigkeitUpperCamelCase()).isEqualTo("Normal");
		assertThat(buttonData3.getSchwierigkeitUpperCamelCase()).isEqualTo("Easy");
	}

	@Test
	@DisplayName("Prüfe auf Identität")
	void test_4() {
		ButtonData button1 = new ButtonData("Start", Schwierigkeit.NORMAL);
		ButtonData button2 = new ButtonData("Start", Schwierigkeit.NORMAL);
		ButtonData button3 = new ButtonData("Stop", Schwierigkeit.HARD);

		assertThat(button1).isEqualTo(button2);
		assertThat(button1).hasSameHashCodeAs(button2);
		assertThat(button1).isNotEqualTo(button3);
	}
}
