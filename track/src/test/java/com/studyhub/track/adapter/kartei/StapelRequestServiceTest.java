package com.studyhub.track.adapter.kartei;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StapelRequestServiceTest {
	@Test
	@DisplayName("Ein valider RequestBody wird an das Kartei-System per POST-Request geschickt")
	void testSendCreateNewStapelRequest() throws IOException, InterruptedException {
		MockWebServer mockWebServer = new MockWebServer();
		mockWebServer.start(8081);
		mockWebServer.enqueue(new MockResponse().setBody("OK").setResponseCode(200));

		StapelRequestService service = new StapelRequestService();
		CreateNewStapelRequest request = new CreateNewStapelRequest(UUID.randomUUID().toString(), "name", "be", "2m,2d", "user");

		service.sendCreateNewStapelRequest(request);

		RecordedRequest recordedRequest = mockWebServer.takeRequest();
		assertEquals("/api/create-stapel", recordedRequest.getPath());
		assertEquals("POST", recordedRequest.getMethod());

		mockWebServer.shutdown();
	}
}
