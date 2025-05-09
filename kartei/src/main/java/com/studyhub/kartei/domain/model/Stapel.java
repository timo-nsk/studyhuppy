package com.studyhub.kartei.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Stapel {

	private String DEFAULT_LEARN_INTERVALL = "5m,15m,30m,2h,1d";

	private UUID fachId;
	private UUID modulFachId;
	private String name;
	private String beschreibung;
	private String lernIntervalle;
	private String username;
	private List<Karteikarte> karteikarten;

	public Stapel(UUID fachId, UUID modulFachId, String name, String beschreibung, String lernIntervalle, String username, List<Karteikarte> karteikarten) {
		this.fachId = fachId;
		this.modulFachId = modulFachId;
		this.name = name;
		this.beschreibung = beschreibung;
		this.lernIntervalle = getValidLernIntervalle(lernIntervalle);
		this.username = username;
		this.karteikarten = karteikarten;
	}

	public Stapel(UUID modulFachId, String name, String beschreibung, String username, String lernIntervalle) {
		this(UUID.randomUUID(), modulFachId, name, beschreibung, lernIntervalle, username, new ArrayList<>());
	}

	private String getValidLernIntervalle(String lernIntervalle) {
		return (lernIntervalle == null || lernIntervalle.isEmpty()) ? DEFAULT_LEARN_INTERVALL : lernIntervalle;
	}

	public boolean addKarteikarte(Karteikarte k) {
		return karteikarten.add(k);
	}

	public Karteikarte findKarteikarteByFachId(String fachId) {
		return karteikarten.stream()
				.filter(karte -> karte.getFachId().toString().equals(fachId))
				.findAny()
				.get();
	}

	public String getFirstKarteikarteId() {
		if (karteikarten.isEmpty()) return null;
		return karteikarten.get(0).getFachId().toString();
	}

	public boolean karteikartenAvailable() {
		if (karteikarten.isEmpty()) return false;
		return true;
	}

	public int anzahlFälligeKarteikarten(LocalDateTime today) {
		if(karteikarten == null || karteikarten.isEmpty()) return 0;

		int count = 0;

		for(Karteikarte k : karteikarten) {
			LocalDateTime fälligAm = k.getFaelligAm();
			if (fälligAm.isBefore(today)) count++;
		}

		return count;
	}

	public long anzahlNeueKarteikarten() {
		return karteikarten.stream().filter(Karteikarte::isNewKarteikarte).count();
	}


	public int getKarteikartenSize() {
		return karteikarten.size();
	}

	public boolean hasModulFachId() {
		return !(modulFachId == null);
	}

	public int getLernIntervalleLength() {
		return lernIntervalle.split(",").length;
	}

	public List<Karteikarte> getFälligeKarteikarten(LocalDateTime now) {
		return karteikarten.stream()
				.filter(k -> k.getFaelligAm().isBefore(now)).toList();
	}
}
