package com.studyhub.kartei.service.application;

import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.domain.model.Stapel;
import com.studyhub.kartei.service.application.lernzeit.KarteikarteGelerntEventRepository;
import com.studyhub.kartei.service.application.lernzeit.LernzeitService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class StapelService {

	private StapelRepository repo;
	private KarteikarteGelerntEventRepository gelerntEventRepository;
	private Logger log = LoggerFactory.getLogger(StapelService.class);
	private LernzeitService lernzeitService;

	public StapelService(StapelRepository repo, KarteikarteGelerntEventRepository gelerntEventRepository, LernzeitService lernzeitService) {
		this.repo = repo;
		this.gelerntEventRepository = gelerntEventRepository;
		this.lernzeitService = lernzeitService;
	}


	public List<Stapel> findAllStapel() {
		return repo.findAll();
	}

	public void saveSet(Stapel set) throws StapelSaveException {
		Stapel res = repo.save(set);
		if (res == null) throw new StapelSaveException("could not save stapel");
		log.info("saved new stapel with id: '%s'".formatted(set.getFachId().toString()));
	}

	public Stapel findByFachId(String fachId) {
		return repo.findByFachId(UUID.fromString(fachId));
	}

	@Transactional
	public Stapel deleteKarteikarteByFachId(UUID setFachId, UUID karteiFachId) {
		Stapel set = repo.findByFachId(setFachId);
		List<Karteikarte> karteikarten = set.getKarteikarten();

		for(int i = 0; i < karteikarten.size(); i++) {
			if(karteikarten.get(i).getFachId().toString().equals(karteiFachId.toString())) {
				karteikarten.remove(i);
				break;
			}
		}

		Stapel res = repo.save(set);
		gelerntEventRepository.deleteAllByKarteiFachId(karteiFachId);
		log.info("deleted karteikarte '%s' from stapel '%s'".formatted(karteiFachId.toString(), setFachId.toString()));

		return res;
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


	public void updateSetWithNewKarteikarte(String stapelId, Karteikarte karteikarte) throws Exception {
		int res = repo.updateSetWithNewKarteikarte(stapelId, karteikarte);
		if (res == 0) throw  new StapelUpdateException("could not update stapel %s with new karteikarte".formatted(stapelId));
		log.info("new karteikarte ( '%s' ) was added to stapel ( '%s' )".formatted(karteikarte.getFachId(), stapelId));
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

	public List<StapelDashboardDataResponse> prepareDashboardInfo(List<Stapel> stapel) {
		List<StapelDashboardDataResponse> dtos = new LinkedList<>();
		LocalDateTime now = LocalDateTime.now();

		for (Stapel stap : stapel) {
			UUID fachId = stap.getFachId();
			StapelDashboardDataResponse dto = new StapelDashboardDataResponse(
					stap.getName(),
					fachId.toString(),
					lernzeitService.getVorraussichtlicheLernzeitFürStapel(fachId, now),
					stap.anzahlNeueKarteikarten(),
					stap.anzahlFälligeKarteikarten(now)
			);
			dtos.add(dto);
		}

		return dtos;
	}

	public Stapel findByFachIdWithFaelligeKarten(String fachId, LocalDateTime now) {
		Stapel s = repo.findByFachId(UUID.fromString(fachId));
		List<Karteikarte> l = s.getFälligeKarteikarten(now);
		s.setKarteikarten(l);
		return s;
	}

	public boolean removeAntwortFromKarte(RemoveAntwortRequest req) {
		Stapel s = repo.findByFachId(UUID.fromString(req.stapelId()));
		s.removeAntwortFromKarte(UUID.fromString(req.karteId()), req.antwortIndex());
		repo.save(s);
		return true;
	}
}
