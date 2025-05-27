package com.studyhub.kartei.domain.model;

import com.studyhub.kartei.domain.model.FrageTyp;
import com.studyhub.kartei.domain.model.Karteikarte;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class KarteikarteBuilderTest {

    @Test
    @DisplayName("Builder erzeugt die gewünschte Karteikarte-Instanz")
    void test_1(){
		UUID uuid = UUID.randomUUID();
		LocalDateTime now = LocalDateTime.now();
	    Karteikarte k = Karteikarte.builder()
			    .fachId(uuid)
			    .frage("f")
			    .antwort("a")
			    .antworten(new LinkedList<>())
			    .erstelltAm(now)
			    .letzteAenderungAm(now)
			    .faelligAm(now)
			    .notiz("n")
			    .wasHard(1)
			    .frageTyp(FrageTyp.NORMAL)
			    .antwortzeitSekunden(20)
			    .lernstufen("2m,2h,3d")
			    .build();

		assertThat(k.getFachId()).isEqualTo(uuid);
	    assertThat(k.getFrage()).isEqualTo("f");
	    assertThat(k.getAntwort()).isEqualTo("a");
	    assertThat(k.getAntworten()).isNotNull();
	    assertThat(k.getErstelltAm()).isEqualTo(now);
	    assertThat(k.getLetzteAenderungAm()).isEqualTo(now);
	    assertThat(k.getFaelligAm()).isEqualTo(now);
	    assertThat(k.getNotiz()).isEqualTo("n");
	    assertThat(k.getWasHard()).isEqualTo(1);
	    assertThat(k.getFrageTyp()).isEqualTo(FrageTyp.NORMAL);
	    assertThat(k.getAntwortzeitSekunden()).isEqualTo(20);
	    assertThat(k.getLernstufen()).isEqualTo("2m,2h,3d");

    }

	@Disabled
	@Test
	@DisplayName("Beim Builden mit falschen Lernstufen wird eine Exception geworfen")
	void test_2(){

		/**
		 * Builder ist von Lombook, muss den selber schreiben
		 *
		assertThrows(AggregateInconsistencyException.class, () -> {
			UUID uuid = UUID.randomUUID();
			LocalDateTime now = LocalDateTime.now();
			Karteikarte k = Karteikarte.builder()
					.fachId(uuid)
					.frage("f")
					.antwort("a")
					.antworten(new LinkedList<>())
					.erstelltAm(now)
					.letzteAenderungAm(now)
					.faelligAm(now)
					.notiz("n")
					.wasHard(1)
					.frageTyp(FrageTyp.NORMAL)
					.antwortzeitSekunden(20)
					.lernstufen("2m2h3d")
					.build();
		});
		 **/
	}
}