package com.studyhub.track.adapter.kartei;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindingResult;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CreateNewStapelRequestTest {

	@Test
	@DisplayName("Korrekte Instanziierung")
	void test_1() {
		String uuid = UUID.randomUUID().toString();
		CreateNewStapelRequest req = new CreateNewStapelRequest("name", uuid, "beschreibung", "20m,2h,d", "peter22");

		assertThat(req.getModulFachId()).isEqualTo(uuid);
		assertThat(req.getSetName()).isEqualTo("name");
		assertThat(req.getBeschreibung()).isEqualTo("beschreibung");
		assertThat(req.getLernIntervalle()).isEqualTo("20m,2h,d");
	}

	@Test
	@DisplayName("Request mit nicht korrekten lernstufen-Formatierung wird als valid erkannt")
	void test_2() {
		String uuid = UUID.randomUUID().toString();
		CreateNewStapelRequest req = new CreateNewStapelRequest(uuid, "name", "beschreibung", "20m,2h,2d", "peter22");

		boolean res = req.invalidLernstufen();

		assertThat(res).isTrue();
	}

	@Test
	@DisplayName("Request mit nicht korrekten lernstufen-Formatierung wird als invalid erkannt")
	void test_3() {
		String uuid = UUID.randomUUID().toString();
		CreateNewStapelRequest req = new CreateNewStapelRequest(uuid, "name", "beschreibung", "20m,2h2d", "peter22");

		boolean res = req.invalidLernstufen();

		assertThat(res).isFalse();
	}

	@Test
	@DisplayName("Request mit nicht korrekten lernstufen-Formatierung wird als invalid erkannt")
	void test_4() {
		String uuid = UUID.randomUUID().toString();
		CreateNewStapelRequest req = new CreateNewStapelRequest(uuid, "name", "beschreibung", "20m,2h2d", "peter22");

		boolean res = req.invalidLernstufen();

		assertThat(res).isFalse();
	}

	@Test
	@DisplayName("Request ohne Setnamen ist nicht möglich")
	void test_5() {
		String uuid = UUID.randomUUID().toString();
		CreateNewStapelRequest req = new CreateNewStapelRequest("", uuid, "beschreibung", "20m,2h2d", "peter22");
		CreateNewStapelRequest req2 = new CreateNewStapelRequest(null, uuid, "beschreibung", "20m,2h2d", "peter22");

		boolean res = req.invalidSetname();
		boolean res2 = req2.invalidSetname();

		assertThat(res).isTrue();
		assertThat(res2).isTrue();
	}

	@Test
	@DisplayName("Wenn der Setname invalid ist, wird dies im BindingResult als Error verzeichnet")
	void test_6() {
		CreateNewStapelRequest req = new CreateNewStapelRequest("", UUID.randomUUID().toString(), "aerhgaerh", "20m,2h2d", "peter22");
		BindingResult bindingResult = mock(BindingResult.class);

		req.validateForBindingResult(bindingResult);

		verify(bindingResult).rejectValue("stapelName", "error.stapelName", "Geben Sie einen Setnamen ein.");
		verify(bindingResult, never()).rejectValue(eq("20m,2h2d"), anyString(), anyString()); // Kein Lernstufen-Fehler
	}

	@Test
	@DisplayName("Wenn die Lernstufen invalid ist, wird dies im BindingResult als Error verzeichnet")
	void test_7() {
		CreateNewStapelRequest req = new CreateNewStapelRequest("weg", UUID.randomUUID().toString(), "aerhgaerh", "20m2h2d", "peter22");
		BindingResult bindingResult = mock(BindingResult.class);

		req.validateForBindingResult(bindingResult);

		verify(bindingResult).rejectValue("lernstufen", "error.lernstufen", "Die eingegeben Lernstufen haben kein gültiges Format.");
		verify(bindingResult, never()).rejectValue(eq("stapelName"), anyString(), anyString()); // Kein Setname-Fehler
	}

	@Test
	@DisplayName("Wenn die Request-Daten valid sind, wird nichts als Error im BindingResult verzeichnet")
	void test_8() {
		CreateNewStapelRequest req = new CreateNewStapelRequest("setname", UUID.randomUUID().toString(), "beschreibung", "20m,2h,2d", "peter22");
		BindingResult bindingResult = mock(BindingResult.class);

		req.validateForBindingResult(bindingResult);

		verify(bindingResult, never()).rejectValue(anyString(), anyString(), anyString());
	}
}
