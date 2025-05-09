package com.studyhub.kartei.service.application;

import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.domain.model.Stapel;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
public class StapelService {

	private StapelRepository repo;
	private Logger log = LoggerFactory.getLogger(StapelService.class);

	public StapelService(StapelRepository repo) {
		this.repo = repo;
	}


	public List<Stapel> findAllStapel() {
		return repo.findAll();
	}

	public void saveSet(Stapel set) {
		repo.save(set);
		log.info("saved new stapel with id: '%s'".formatted(set.getFachId().toString()));
	}

	public Stapel findByFachId(String fachId) {
		return repo.findByFachId(UUID.fromString(fachId));
	}

	public Stapel deleteKarteikarteByFachId(UUID setFachId, UUID karteiFachId) {
		Stapel set = repo.findByFachId(setFachId);
		List<Karteikarte> karteikarten = set.getKarteikarten();

		for(int i = 0; i < karteikarten.size(); i++) {
			if(karteikarten.get(i).getFachId().toString().equals(karteiFachId.toString())) {
				karteikarten.remove(i);
				break;
			}
		}

		log.info("deleted karteikarte '%s' from stapel '%s'".formatted(karteiFachId.toString(), setFachId.toString()));
		return repo.save(set);
	}


	public Karteikarte findKarteikarteByFachId(String karteiSetId, String karteId) {
		return repo.findByFachId(UUID.fromString(karteiSetId)).findKarteikarteByFachId(karteId);
	}


	public boolean stapelNotExists(String karteiSetId) {
		Stapel set = repo.findByFachId(UUID.fromString(karteiSetId));
		return set == null;
	}


	public boolean areKarteiSetsAvailable() {
		return repo.countAll() != 0;
	}


	public void updateSetWithNewKarteikarte(String karteiSetId, Karteikarte karteikarte) {
		repo.updateSetWithNewKarteikarte(karteiSetId, karteikarte);
		log.info("new karteikarte ( '%s' ) was added to stapel ( '%s' )".formatted(karteikarte.getFachId(), karteiSetId));
	}

	public Stapel maybeGetHttpSessionForStapel(HttpSession session, Stapel stapel) {
		Stapel currentStapel;

		if(session.getAttribute("currentSet") == null) {
			currentStapel = stapel;
			session.setAttribute("currentSet", currentStapel);
			log.debug("set session attribute 'currentSet' with stapel id='%s' to HttpSession id='%s'".formatted(stapel.getFachId().toString(), session.getId()));
		} else {
			currentStapel = (Stapel) session.getAttribute("currentSet");
			log.debug("got 'currentSet' id='%s' from HttpSession id='%s'".formatted(stapel.getFachId().toString(), session.getId()));
		}

		return currentStapel;
	}

	public void removeSetFromSession(HttpSession session) {
		session.removeAttribute("currentSet");
	}

	public boolean isLastKarteikarteReached(int karteikartenSize, int karteiKartenIndex) {
		if (karteiKartenIndex > karteikartenSize) return false;
		return karteikartenSize == karteiKartenIndex;
	}

	public Map<String, Integer> getAnzahlFaelligeKartenForEachStapel(LocalDateTime now) {

		Map<String, Integer> responseMap = new HashMap<>();
		List<Stapel> allStapel = repo.findAll();

		allStapel.forEach(stapel -> {
			int anzahl = stapel.anzahlFälligeKarteikarten(now);
			String stapelName = stapel.getName();

			responseMap.put(stapelName, anzahl);
		});

		return responseMap;
	}

	public List<Stapel> findByUsername(String username) {
		return repo.findByUsername(username);
	}

	public boolean isStapelDbHealthy() {
		return repo.isStapelDbHealthy();
	}
}
