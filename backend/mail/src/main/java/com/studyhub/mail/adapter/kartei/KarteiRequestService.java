package com.studyhub.mail.adapter.kartei;

import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

//TODO: refactor service
@Service
public class KarteiRequestService {

	@Value("${kartei.api-url}")
	private String karteiApiUrl;


	private Logger log = LoggerFactory.getLogger(KarteiRequestService.class);


	// TODO: die Email soll nur abgeschickt werden, wennd er User auch Benachrichtigungen angeschaltet hat
	/**
	 * Methode holt sich jeden Tag um eine gewisse Uhrzeit im Karteisystem die Anzahl fälliger Karten pro Stapel eines Users
	 * und verschickt Sie an den User
	 */
	//@Scheduled(cron = "0 18 * * * ?")
	//@Scheduled(fixedRate = 10000)
	public void pollFaelligeKarteikartenOfStapel() {
		String uri = "%s/get-faelligeKarten".formatted(karteiApiUrl);

		CompletableFuture<Map> amountOfFaelligeKarteikarten = WebClient.create()
				.get()
				.uri(uri)
				.retrieve()
				.bodyToMono(Map.class)
				.timeout(Duration.of(3, ChronoUnit.SECONDS))
				.toFuture();

		try {
			Map<String, Integer> futureResult = (Map<String, Integer>) amountOfFaelligeKarteikarten.get();
			//templateMailService.prepareLernNotificationTemplating(futureResult);
			log.info("SUCCESS fetched amountOfFaelligeKarteikarten from: %s".formatted(uri));
		} catch(CancellationException | ExecutionException | InterruptedException ex) {
			System.out.println(ex.getMessage());
			log.info("FAILED could not fetch from: %s".formatted(uri));
		}
    }
}
