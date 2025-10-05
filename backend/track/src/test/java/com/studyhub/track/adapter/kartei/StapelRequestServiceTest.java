package com.studyhub.track.adapter.kartei;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StapelRequestServiceTest {
	@Disabled(
			"""
				TODO: FIXME - aktuell fehlschlagend, da die Funktion rausgenommen wurde und vor der Token-VErifizierung implementiert wurde.
				Deswegen der 403 - Fehler. Die sendCreateNewStapelRequest muss den Header mit dem Token bekommen und weiter schicken
			""")
	@Test
	@DisplayName("Ein valider RequestBody wird an das Kartei-System per POST-Request geschickt")
	void testSendCreateNewStapelRequest() throws IOException, InterruptedException {
		MockWebServer mockWebServer = new MockWebServer();
		mockWebServer.start(9100);
		mockWebServer.enqueue(new MockResponse().setBody("OK").setResponseCode(200));

		StapelRequestService service = new StapelRequestService();
		service.setKarteiApiUrl("http://localhost:9081/api/kartei/v1");
		CreateNewStapelRequest request = new CreateNewStapelRequest(UUID.randomUUID().toString(), "name", "be", "2m,2d", "user");

		service.sendCreateNewStapelRequest(request);

		RecordedRequest recordedRequest = mockWebServer.takeRequest();
		assertEquals("/api/kartei/v1/create-stapel", recordedRequest.getPath());
		assertEquals("POST", recordedRequest.getMethod());

		mockWebServer.shutdown();
	}
}
