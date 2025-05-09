package com.studyhub.track.domain.model.modul;

import com.studyhub.track.domain.model.AggregateRoot;
import com.studyhub.track.domain.model.semester.Semester;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;
import static com.studyhub.track.domain.model.ModulSecondsConverter.*;

@AllArgsConstructor
@AggregateRoot
@Data
public class Modul {
	private UUID fachId;
	private String name;
	private Integer secondsLearned;
	private Kreditpunkte kreditpunkte;
	private String username;
	private boolean active;
	private int semesterstufe;
	private Semester semester;
	private LocalDateTime klausurDate;
	private Lerntage lerntage;

	public String getTimeLearned() {
		return convertToString(secondsLearned);
	}

	public String getGesamtArbeitsaufwand() {
		return String.valueOf(kreditpunkte.getSelbststudiumStunden() + kreditpunkte.getKontaktzeitStunden());
	}

	public boolean überschreitetGesamtarbeitsaufwand() {
		return secondsLearned >= Integer.parseInt(getGesamtArbeitsaufwand())*60*60;
	}

	public boolean überschreitetSelbststudiumaufwand() {
		return secondsLearned >= kreditpunkte.getSelbststudiumStunden()*60*60;
	}

	public boolean klausurDatumEingetragen() {
		return klausurDate != null;
	}

	public boolean vorlesungDatumangabenEingetragen() {
		return semester.getVorlesungBeginn() != null && semester.getVorlesungEnde() != null;
	}

	public int getRemainingSelbststudiumZeit() {
		return (kreditpunkte.getSelbststudiumStunden()*60*60) - secondsLearned;
	}
}
