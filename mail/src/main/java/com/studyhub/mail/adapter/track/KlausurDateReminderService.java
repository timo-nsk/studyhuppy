package com.studyhub.mail.adapter.track;

import com.studyhub.mail.application.service.TemplateMailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class KlausurDateReminderService {

	@Value("${auth.api-url}")
	private String authApiUrl;

	private final TemplateMailService templateMailService;

	public KlausurDateReminderService(TemplateMailService templateMailService) {
		this.templateMailService = templateMailService;
	}

	//TODO: manuell getestet und läuft soweit, sollte aber richtig getestet werden
	/**
	 * 1x im Monat wird überprüft, ob der User ein Klausurdatum angegeben hat und bekommt dementsprechend eine
	 * E-Mail als Erinnerung, sie einzutragen (wenn notification eingeschaltet sind).
	 */
	@Scheduled(cron = "0 0 12 15 * ?")  // Jeden 15. um 12:00 Uhr
	public void sendReminderMail() {
		// Hole von auth alle User, die Benachrichtigungen eingeschaltet haben
		Map<String, String> usersWithNotificationOn = getUsersWithNotificationOn();

		// Hole das KlausurReminderDto-Objekt der User, die für ein aktives Modul kein Klausurtermin eingetragen haben
		List<KlausurReminderDto> data = findKlausurDates(usersWithNotificationOn.keySet().stream().toList());

		// Vorbereite Daten für das Templating + anschließender Versand
		for(Map.Entry<String, String> currentUser : usersWithNotificationOn.entrySet()) {
			List<String> module = new LinkedList<>();

			for(KlausurReminderDto klausurReminderDto : data) {
				if (klausurReminderDto.username().equals(currentUser.getKey())) {
					module.add(klausurReminderDto.modulName());
				}
			}

			if(module.isEmpty()) continue;

			templateMailService.prepareKlausurReminderTemplating(module, currentUser.getKey(), currentUser.getValue());
		}
	}

	private List<KlausurReminderDto> findKlausurDates(List<String> list) {
		return WebClient.create()
				.post()
				.uri("http://localhost:9080/api/modul/v1/data-klausur-reminding")
				.bodyValue(list)
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<KlausurReminderDto>>() {})
				.timeout(Duration.ofSeconds(5))
				.block();

	}

	private Map<String, String> getUsersWithNotificationOn() {
		return WebClient.create()
				.get()
				.uri("%s/get-users-notification".formatted(authApiUrl))
				.retrieve()
				.bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {})
				.timeout(Duration.ofSeconds(5))
				.block();
	}
}
