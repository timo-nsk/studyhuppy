package com.studyhub.track.model;

import com.studyhub.track.domain.model.modul.Lerntage;
import com.studyhub.track.domain.model.semester.SemesterPhase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LerntageTest {

	@Test
	@DisplayName("Alle Lerntage werden als boolea-Array zurückgegeben")
	void test_1() {
		Lerntage lerntage = new Lerntage(true, true, true, true, false, false, false, SemesterPhase.KLAUSUR);

		boolean[] days = lerntage.getAllLerntage();;

		assertThat(days[0]).isTrue();
		assertThat(days[1]).isTrue();
		assertThat(days[2]).isTrue();
		assertThat(days[3]).isTrue();
		assertThat(days[4]).isFalse();
		assertThat(days[5]).isFalse();
		assertThat(days[6]).isFalse();
	}
}
