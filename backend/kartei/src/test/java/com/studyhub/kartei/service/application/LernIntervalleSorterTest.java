package com.studyhub.kartei.service.application;

import com.studyhub.kartei.service.application.LernIntervalleSorter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class LernIntervalleSorterTest {

	String[] intervalle1 = {"2d", "4h", "10m"};
	String[] intervalle2 = {"2m", "4d", "10h"};
	String[] intervalle3 = {"4d", "2d", "10m"};
	String[] intervalle4 = {"50m", "2m", "2h"};
	String[] intervalle5 = {"4h", "2h", "10m", "5m"};
	String[] intervalle6 = {"1d", "2h", "3m", "4d", "5m", "6h", "7m", "8m", "9d", "10h", "11m", "12d", "13h", "14h", "15m"};

	@Test
	@DisplayName(" '{2d, 4h, 10m}' wird korrekt sortiert -> '{10m, 4h, 2d}'")
	void test_1() {
		String[] sorted = LernIntervalleSorter.sortIntervalleLexikographisch(intervalle1);

		assertThat(sorted).isEqualTo(new String[] {"10m", "4h", "2d"});
	}

	@Test
	@DisplayName(" '{2m, 4d, 10h}' wird korrekt sortiert -> '{2m, 10h, 4d}'")
	void test_2() {
		String[] sorted = LernIntervalleSorter.sortIntervalleLexikographisch(intervalle2);

		assertThat(sorted).isEqualTo(new String[] {"2m", "10h", "4d"});
	}

	@Test
	@DisplayName(" '4d, 2d, 10m' wird korrekt sortiert -> '{10m, 2d, 4d}'")
	void test_3() {
		String[] sorted = LernIntervalleSorter.sortIntervalleLexikographisch(intervalle3);

		assertThat(sorted).isEqualTo(new String[] {"10m", "2d", "4d"});
	}

	@Test
	@DisplayName(" {50m, 2m, 2h} wird korrekt sortiert -> '{2m, 50m, 2h}'")
	void test_4() {
		String[] sorted = LernIntervalleSorter.sortIntervalleLexikographisch(intervalle4);

		assertThat(sorted).isEqualTo(new String[] {"2m", "50m", "2h"});
	}

	@Test
	@DisplayName(" {4h, 2h, 10m, 5m} wird korrekt sortiert -> '{5m, 10m, 2h, 4h}'")
	void test_5() {
		String[] sorted = LernIntervalleSorter.sortIntervalleLexikographisch(intervalle5);
		System.out.println(sorted);

		assertThat(sorted).isEqualTo(new String[] {"5m", "10m", "2h", "4h"});
	}

	@Test
	@DisplayName(" {1d, 2h, 3m, 4d, 5m, 6h, 7m, 8m, 9d, 10h, 11m, 12d, 13h, 14h, 15m} wird korrekt sortiert -> '{3m, 5m, 7m, 8m, 11m, 15m, 2h, 6h, 10h, 13h, 14h, 1d, 4d, 9d, 12d}'")
	void test_6() {
		String[] sorted = LernIntervalleSorter.sortIntervalleLexikographisch(intervalle6);
		System.out.println(sorted);

		assertThat(sorted).isEqualTo(new String[] {"3m", "5m", "7m", "8m", "11m", "15m", "2h", "6h", "10h", "13h", "14h", "1d", "4d", "9d", "12d"});
	}
}
