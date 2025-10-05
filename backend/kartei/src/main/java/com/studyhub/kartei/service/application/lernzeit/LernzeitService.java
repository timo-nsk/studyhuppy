package com.studyhub.kartei.service.application.lernzeit;

import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.domain.model.KarteikarteGelerntEvent;
import com.studyhub.kartei.service.application.StapelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Class providing methods to calculate the expected learning time for a Stapel of Karteikarten.
 */
@Service
public class LernzeitService {
	private final int THRESHOLD = 5;

	private final StapelRepository stapelRepository;
	private final KarteikarteGelerntEventRepository repo;

	private final Logger log = LoggerFactory.getLogger(LernzeitService.class);

	public LernzeitService(StapelRepository stapelRepository, KarteikarteGelerntEventRepository repo) {
		this.stapelRepository = stapelRepository;
		this.repo = repo;
	}

	/**
	 * Calculates the expected learning time for a Stapel of Karteikarten that are 'fällig'.
	 * For each Karteikarte in the Stapel, the average of the secondsNeeded of all KarteikarteGelerntEvents is calculated.
	 * Then the sum of all partial averages is calculated.
	 * @param stapelId is the id of the Stapel to calculate the expected learning time for
	 * @param now  is the time to calculate the expected learning time for
	 * @return expected learning time
	 */
	public int getVorraussichtlicheLernzeitFürStapel(UUID stapelId, LocalDateTime now) {
		List<KarteikarteGelerntEvent> events = repo.findByStapelId(stapelId);

		if (events.isEmpty()) return 0;

		List<Karteikarte> karteikartenDesStapels = stapelRepository.findByFachId(stapelId).getFälligeKarteikarten(now);

		if (karteikartenDesStapels.isEmpty()) return 0;

		int summeSecondsNeeded = 0;

		for(Karteikarte karte : karteikartenDesStapels) {
			int summe = 0;
			int count = 0;
			for (KarteikarteGelerntEvent event : events) {
				if(karte.getFachId().toString().equals(event.getKarteikarteId().toString())) {
					summe += event.getSecondsNeeded();
					count++;
				}
			}

			if(!(count == 0)) {
				summeSecondsNeeded += summe / count;
				count = 0;
			}
		}

		return summeSecondsNeeded;
	}

	/**
	 * Formats the expected learning time as a String.
	 * @param summeSecondsNeeded is the expected learning time in seconds
	 * @return expected learning time as a String, i.e., summeSecondsNeeded = 3600 -> "~1h"
	 */
	public String calculateZeitAsString(int summeSecondsNeeded) {
		int minutes = summeSecondsNeeded / 60;

		if (minutes >= 60) {
			int hours = minutes / 60;
			return String.format("~%dh", hours);
		}

		return String.format("~%dm", minutes);
	}

	/**
	 * Checks if the expected learning time for a Stapel is greater than a threshold.
	 * @param stapelId is the id of the Stapel to check
	 * @param now is the time to check
	 * @return true if the expected learning time is greater than the threshold, false otherwise
	 */
	public boolean showVorraussichtlicheLernzeitWhen(UUID stapelId, LocalDateTime now) {
		int secondsNeeded = getVorraussichtlicheLernzeitFürStapel(stapelId, now);
		if (secondsNeeded == 0) return false;
		return secondsNeeded / 60 > THRESHOLD;
	}

	/**
	 * Returns the current date and time.
	 * Is a helper method for testing.
	 * @return current date and time
	 */
	public LocalDateTime dateOfToday() {
		return LocalDateTime.now();
	}
}