package com.studyhub.kartei.service.application;

import com.studyhub.kartei.adapter.web.controller.EditChoiceKarteikarteRequest;
import com.studyhub.kartei.adapter.web.controller.EditNormalKarteikarteRequest;
import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.domain.model.Stapel;
import com.studyhub.kartei.service.application.lernzeit.KarteikarteGelerntEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.studyhub.kartei.domain.model.Schwierigkeit.*;

@Service
public class KarteikarteService {

	private final StapelRepository stapelRepository;
	private final KarteikarteRepository karteikarteRepository;
	private final KarteikarteGelerntEventRepository eventRepo;

	private final Logger log = LoggerFactory.getLogger(KarteikarteService.class);

	public KarteikarteService(StapelRepository stapelRepository, KarteikarteRepository karteikarteRepository, KarteikarteGelerntEventRepository eventRepo) {
		this.stapelRepository = stapelRepository;
		this.karteikarteRepository = karteikarteRepository;
		this.eventRepo = eventRepo;
	}

	@Transactional
	public boolean updateKarteikarteForNextReview(UpdateInfo updateInfo) {
		LocalDateTime now = LocalDateTime.now();
		Stapel stapel = stapelRepository.findByFachId(UUID.fromString(updateInfo.stapelId()));
		Karteikarte karteToUpdate = stapel.findKarteikarteByFachId(updateInfo.karteId());
		String karteLernstufen = karteToUpdate.getLernstufen();

		LocalDateTime newFaelligAm = null;
		StringBuilder buildNewLernstufen = new StringBuilder();

		String[] splittedLernstufen = karteLernstufen.split(",");
		switch (updateInfo.schwierigkeit()) {
			// update karte fälligkeit mit ersten wert
			case HARD -> {
				String hardStufe = splittedLernstufen[0];
				int time = Integer.parseInt(hardStufe.substring(0, hardStufe.length() - 1));
				char unit = hardStufe.charAt(hardStufe.length() - 1);
				newFaelligAm = addTimeToDate(now, unit, time);
				karteToUpdate.karteikarteWasHard();
			}
			// update "10m,4h,2d" -> "4h,2d,4d"
			case NORMAL -> {
				String normalStufe = splittedLernstufen[1];
				int time = Integer.parseInt(normalStufe.substring(0, normalStufe.length() - 1));
				char unit = normalStufe.charAt(normalStufe.length() - 1);
				newFaelligAm = addTimeToDate(now, unit, time);

				karteToUpdate.updateLernstufen(NORMAL);
			}
			// update "10m,4h,2d" -> "2d,4d,8d"
			case EASY -> {
				String easyStufe = splittedLernstufen[2];
				int time = Integer.parseInt(easyStufe.substring(0, easyStufe.length() - 1));
				char unit = easyStufe.charAt(easyStufe.length() - 1);
				newFaelligAm = addTimeToDate(now, unit, time);

				karteToUpdate.updateLernstufen(EASY);
				}
		}



		karteToUpdate.setFaelligAm(newFaelligAm);
		stapelRepository.save(stapel);
		eventRepo.save(updateInfo.prepareHappenedEvent());

		log.info("updated fälligAm-date for Karteikarte '%s'".formatted(karteToUpdate.getFachId()));
		return true;
	}

	public LocalDateTime addTimeToDate(LocalDateTime now, char timeUnit, int time) {
		return switch (timeUnit) {
			case 'm' -> now.plusMinutes(time);
			case 'h' -> now.plusHours(time);
			case 'd' -> now.plusDays(time);
			default -> now;
		};
	}


	/**
	 * @param stapelId Search stapel repository by stapel.fachId
	 * @param karteId Iterate its list of Karteikarte filtering the desired Karteikarte by karteId
	 * @param editedKarte If found, replace its data with the data of editedKarte
	 * @return <strong>true</strong>, if process succeeds, <strong>false</strong> otherwise
	 */
	public boolean editNormalKarteikarte(String stapelId, String karteId, Karteikarte editedKarte) {
		System.out.println(editedKarte == null);
		if(editedKarte == null || stapelId.isEmpty() || karteId.isEmpty()) {
			return false;
		} else {
			Stapel stapel = stapelRepository.findByFachId(UUID.fromString(stapelId));
			List<Karteikarte> karten = stapel.getKarteikarten();

			for (Karteikarte currentKarte : karten) {
				if (currentKarte.getFachId().toString().equals(karteId)) {
					currentKarte.setFrage(editedKarte.getFrage());
					currentKarte.setAntwort(editedKarte.getAntwort());
					currentKarte.setNotiz(editedKarte.getNotiz());
					currentKarte.setLetzteAenderungAm(LocalDateTime.now());
					break;
				}
			}
			stapelRepository.save(stapel);
			log.info("edited karteikarte of set '%s'".formatted(stapelId));
			return true;
		}
	}

	public boolean editNormalKarteikarte(EditNormalKarteikarteRequest editRequest) {
		if(editRequest == null) {
			return false;
		} else {
			Stapel stapel = stapelRepository.findByFachId(UUID.fromString(editRequest.stapelId()));
			List<Karteikarte> karten = stapel.getKarteikarten();

			for (Karteikarte currentKarte : karten) {
				if (currentKarte.getFachId().toString().equals(editRequest.karteId())) {
					currentKarte.setFrage(editRequest.frage());
					currentKarte.setAntwort(editRequest.antwort());
					currentKarte.setNotiz(editRequest.notiz());
					currentKarte.setLetzteAenderungAm(LocalDateTime.now());
					break;
				}
			}
			stapelRepository.save(stapel);
			log.info("edited karteikarte of stapel '%s'".formatted(editRequest.stapelId()));
			return true;
		}
	}

	public boolean karteNotExistsById(String karteiSetId) {
		Karteikarte karteikarte = karteikarteRepository.findByFachId(karteiSetId);
		return karteikarte == null;
	}

	public void editChoiceKarteikarte(EditChoiceKarteikarteRequest editRequest) {

	}
}
