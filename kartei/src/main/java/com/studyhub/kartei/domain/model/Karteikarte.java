package com.studyhub.kartei.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Karteikarte {
	private UUID fachId;
	private String frage;
	private String antwort;
	private List<Antwort> antworten;
	private LocalDateTime erstelltAm;
	private LocalDateTime letzteAenderungAm;
	private LocalDateTime faelligAm;
	private String notiz;
	private int wasHard;
	private FrageTyp frageTyp;
	private int antwortzeitSekunden;
	private String lernstufen;

	/**
	 * Factory-Method for a default Karteikarte with automatically set dates, wasHard and antwortZeit.
	 * Method should primarily be used if a new Karteikarte should be created for persisting usage.,
	 * @param frage is the actual question
	 * @param antwort is the actual answer to the question
	 * @param notiz is information regarding this card
	 * @param frageTyp is the type of question
	 * @return the build Karteikarte-Instance
	 */
	@Factory
	public static Karteikarte initNewKarteikarte(String frage, String antwort, String notiz, FrageTyp frageTyp) {
		LocalDateTime today = LocalDateTime.now();
		return Karteikarte.builder()
				.fachId(UUID.randomUUID())
				.frage(frage)
				.antwort(antwort)
				.erstelltAm(today)
				.letzteAenderungAm(today)
				.faelligAm(today)
				.notiz(notiz)
				.wasHard(0)
				.frageTyp(frageTyp)
				.antwortzeitSekunden(0)
				.build();
	}

	/**
	 * Factory-Method for a default Single Choice Karteikarte with automatically set dates, wasHard and antwortZeit.
	 * Method should primarily be used if a new Karteikarte should be created for persisting usage.,
	 * @param frage is the actual question
	 * @param antworten are the actual answer options to the question
	 * @param notiz is information regarding this card
	 * @param frageTyp is the type of question
	 * @return the build Karteikarte-Instance
	 */
	@Factory
	public static Karteikarte initNewSingleChoiceKarteikarte(String frage, List<Antwort> antworten, String notiz, FrageTyp frageTyp) {
		LocalDateTime today = LocalDateTime.now();
		return Karteikarte.builder()
				.fachId(UUID.randomUUID())
				.frage(frage)
				.antworten(antworten)
				.erstelltAm(today)
				.letzteAenderungAm(today)
				.faelligAm(today)
				.notiz(notiz)
				.wasHard(0)
				.frageTyp(frageTyp)
				.antwortzeitSekunden(0)
				.build();
	}

	/**
	 * Method updates the lernstufe of the Karteikarte.
	 * @param schwierigkeit is the difficulty which the user has decided to answer the question
	 * @return the updated lernstufen-String i.e. '2m,2h,2d', NORMAL -> '2h,2d,4d' - '2m,2h,2d', EASY -> '2d,4d,8d'
	 */
	public boolean updateLernstufen(Schwierigkeit schwierigkeit) {

		String[] lernstufen = this.lernstufen.split(",");
		StringBuilder lernstufenBuilder = new StringBuilder();

		switch (schwierigkeit) {
			case NORMAL -> {
				String normalStufe = lernstufen[1];
				lernstufenBuilder.append(normalStufe).append(",");
				lernstufenBuilder.append(lernstufen[2]).append(",");
				String toDouble = lernstufen[2];
				int time2 = Integer.parseInt(toDouble.substring(0, toDouble.length() - 1));
				char unit2 = toDouble.charAt(toDouble.length() - 1);
				int doubledTime = time2 * 2;
				lernstufenBuilder.append(doubledTime).append(unit2);
			}

			case EASY -> {
				String easyStufe = lernstufen[2];
				int time = Integer.parseInt(easyStufe.substring(0, easyStufe.length() - 1));
				char unit = easyStufe.charAt(easyStufe.length() - 1);
				lernstufenBuilder.append(easyStufe).append(",");
				int time2 = time * 2;
				int time3 = time2 * 2;
				lernstufenBuilder.append(time2).append(unit).append(",").append(time3).append(unit);

				for(int i = 3; i < lernstufen.length; i++) {
					time3 *=  2;
					lernstufenBuilder.append(",").append(time3).append(unit);
					if(i != lernstufen.length-1) lernstufenBuilder.append(",");
				}
			}
		}

		if(schwierigkeit != Schwierigkeit.HARD) setLernstufen(lernstufenBuilder.toString());

		return true;
	}

	/**
	 * Increase the wasHard field when user decided that the Karteikarte was Schwierigkeit.HARD while learning.
	 * Should only be used, when updating the card.
	 */
	public void karteikarteWasHard() {
		this.wasHard++;
	}

	/**
	 * Check if the lersntufen-String is in valid format: Contains time units m,h or d after numbers.
	 * Number and time unit form a valid learnstufe value. Values are separated by ','
	 * @param lernstufen
	 * @return <strong>true</strong> if the lernstufe is valid, <strong>false</strong> otherwise.
	 */
	public boolean validLernstufen(String lernstufen) {
		System.out.println("validating: " + lernstufen);
		if (lernstufen == null || lernstufen.isBlank()) return false;
		String regex = "^(?:(?:[1-9][0-9]*m|[1-9][0-9]*h|[1-9][0-9]*d)(?:,(?:[1-9][0-9]*m|[1-9][0-9]*h|[1-9][0-9]*d))*)$";
		boolean b = Pattern.compile(regex).matcher(lernstufen).matches();
		System.out.println("after regex matching: " + b);
		return Pattern.compile(regex).matcher(lernstufen).matches();
	}

	/**
	 * Set lernstufen of this Karteikarte.
	 *
	 * @param lernstufen the new lernstufen for this Karteikarte.
	 * @throws AggregateInconsistencyException if the lernstufen are invalid.
	 */
	public void setLernstufen(String lernstufen) {
		System.out.println(lernstufen);
		System.out.println(validLernstufen(lernstufen));
		if (!validLernstufen(lernstufen)) throw new AggregateInconsistencyException("wanted lernstufen <%s> was rejected due to incorrect format".formatted(lernstufen));
		this.lernstufen = lernstufen;
	}

	/**
	 * Check if this karteikarte is new. A Karteikarte is new if it has not been learned yet. If it was learned, then the faelligAm date would at least be greater than
	 * the erstelltAm date by one day.
	 * @return <strong>true</strong> if the erstelltAm date is equal to faelligAm date, <strong>false otherwise.</strong>
	 */
	public boolean isNewKarteikarte() {
		return erstelltAm.toLocalDate().isEqual(faelligAm.toLocalDate());
	}

	public String getFrageTypFormatted() {
		switch (frageTyp) {
			case NORMAL -> {
				return "Normal";
			}
			case SINGLE_CHOICE -> {
				return "Single Choice";
			}
			case MULTIPLE_CHOICE -> {
				return "Multiple Choice";
			}
		}
		return "Unknown";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Karteikarte that)) return false;
		return Objects.equals(getFachId(), that.getFachId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getFachId());
	}
}
