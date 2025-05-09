package com.studyhub.kartei.util;

import com.studyhub.kartei.domain.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static com.studyhub.kartei.util.KarteikarteMother.*;

public class StapelMother {

	public static Stapel initSet() {
		List<Karteikarte> karteikarten = new LinkedList<>();

		Antwort aw = new Antwort("antwort", true);
		List<Antwort> l = new ArrayList<>();
		l.add(aw);

		Karteikarte karte = new Karteikarte(
				UUID.randomUUID(),
				"Frage 1",
				"Antwort 1",
				l,
				LocalDateTime.now(),
				LocalDateTime.now(),
				LocalDateTime.now(),
				"Notiz 1",
				0,
				FrageTyp.NORMAL,
				10,
				"10m,10h,10d");

		karteikarten.add(karte);

		return new Stapel(
				UUID.randomUUID(),
				UUID.randomUUID(),
				"TestSet",
				"TestSet Beschreibung",
				"10m,1d",
				"user1",
				karteikarten);
	}

	public static List<Stapel> initTwoStapel() {
		UUID stapel1uuid = UUID.fromString("40c0ea04-fb47-4fbe-a512-76280cb7dd12");
		UUID stapel2uuid = UUID.fromString("aeb2b88d-6e21-4fd8-9c12-7e8fc78e49de");

		List<Stapel> twoStapel = new LinkedList<>();

		Stapel stapel1 = new Stapel(stapel1uuid, UUID.randomUUID(), "stapel1", "beschreibung1", "2m,2h,2d", "user1",new LinkedList<>());
		Stapel stapel2 = new Stapel(stapel2uuid, UUID.randomUUID(), "stapel2", "beschreibung2", "2m,2h,2d", "user1",new LinkedList<>());


		UUID k1uuid = UUID.fromString("0e7c40f7-6891-41f6-94cb-dc1ec946c774");
		UUID k2uuid = UUID.fromString("839ad96d-0f91-4b6a-91d1-792e9bda34db");
		Karteikarte k1 = KarteikarteMother.newKarteWithIdAndFälligAm(k1uuid, LocalDateTime.of(2025, 1, 5, 10, 0));
		Karteikarte k2 = KarteikarteMother.newKarteWithIdAndFälligAm(k2uuid, LocalDateTime.of(2025, 1, 12, 10, 0));
		stapel1.addKarteikarte(k1);
		stapel1.addKarteikarte(k2);


		UUID k3uuid = UUID.fromString("f6c8352c-8bb6-4b56-b4a1-833d69364b23");
		UUID k4uuid = UUID.fromString("32bce5c9-3e44-43a3-b65f-17b8e829e3d4");
		Karteikarte k3 = KarteikarteMother.newKarteWithIdAndFälligAm(k3uuid, LocalDateTime.of(2025, 1, 12, 10, 0));
		Karteikarte k4 = KarteikarteMother.newKarteWithIdAndFälligAm(k4uuid, LocalDateTime.of(2025, 1, 12, 10, 0));
		stapel2.addKarteikarte(k3);
		stapel2.addKarteikarte(k4);

		twoStapel.add(stapel1);
		twoStapel.add(stapel2);

		return twoStapel;
	}

	/**
	 * 3 Stapel, 1. Stapel soll eine fällige karte haben, 2. Stapel 2 und der dritte 3.
	 * Das Prüfdatum soll "LocalDateTime.of(2025, 1, 5, 10, 0)" sein
	 * @return
	 */
	public static List<Stapel> initManyStapel() {
		UUID stapel1uuid = UUID.fromString("40c0ea04-fb47-4fbe-a512-76280cb7dd12");
		UUID stapel2uuid = UUID.fromString("aeb2b88d-6e21-4fd8-9c12-7e8fc78e49de");
		UUID stapel3uuid = UUID.fromString("aeb2b88d-6e24-4fd8-9c12-7e8fc78e49de");

		List<Stapel> manyStapel = new LinkedList<>();

		Stapel stapel1 = new Stapel(stapel1uuid, UUID.randomUUID(), "stapel1", "beschreibung1", "2m,2h,2d", "user1", new LinkedList<>());
		Stapel stapel2 = new Stapel(stapel2uuid, UUID.randomUUID(), "stapel2", "beschreibung2", "2m,2h,2d", "user1", new LinkedList<>());
		Stapel stapel3 = new Stapel(stapel3uuid, UUID.randomUUID(), "stapel3", "beschreibung3", "2m,2h,4d", "user1", new LinkedList<>());


		UUID k1uuid = UUID.fromString("0e7c40f7-6891-41f6-94cb-dc1ec946c774");
		UUID k2uuid = UUID.fromString("839ad96d-0f91-4b6a-91d1-792e9bda34db");
		UUID k3uuid = UUID.fromString("0e7c40f7-6891-41f6-94cb-dc1ec946c774");
		UUID k4uuid = UUID.fromString("839ad96d-0f91-4b6a-91d1-792e9bda34db");
		Karteikarte k1 = KarteikarteMother.newKarteWithIdAndFälligAm(k1uuid, LocalDateTime.of(2025, 1, 4, 10, 0));
		Karteikarte k2 = KarteikarteMother.newKarteWithIdAndFälligAm(k2uuid, LocalDateTime.of(2025, 1, 10, 10, 0));
		Karteikarte k3 = KarteikarteMother.newKarteWithIdAndFälligAm(k3uuid, LocalDateTime.of(2025, 1, 11, 10, 0));
		Karteikarte k4 = KarteikarteMother.newKarteWithIdAndFälligAm(k4uuid, LocalDateTime.of(2025, 1, 12, 10, 0));
		stapel1.addKarteikarte(k1);
		stapel1.addKarteikarte(k2);
		stapel1.addKarteikarte(k3);
		stapel1.addKarteikarte(k4);


		UUID k5uuid = UUID.fromString("f6c8352c-8bb6-4b56-b4a1-833d69364b23");
		UUID k6uuid = UUID.fromString("32bce5c9-3e44-43a3-b65f-17b1e829e3d4");
		UUID k7uuid = UUID.fromString("a6c8352c-8bb6-4b56-b4a1-833d69364b23");
		UUID k8uuid = UUID.fromString("92bce5c9-3e44-43a3-b65f-17b8e829e3d4");
		Karteikarte k5 = KarteikarteMother.newKarteWithIdAndFälligAm(k5uuid, LocalDateTime.of(2025, 1, 3, 10, 0));
		Karteikarte k6 = KarteikarteMother.newKarteWithIdAndFälligAm(k6uuid, LocalDateTime.of(2025, 1, 4, 10, 0));
		Karteikarte k7 = KarteikarteMother.newKarteWithIdAndFälligAm(k7uuid, LocalDateTime.of(2025, 1, 12, 10, 0));
		Karteikarte k8 = KarteikarteMother.newKarteWithIdAndFälligAm(k8uuid, LocalDateTime.of(2025, 1, 13, 10, 0));
		stapel2.addKarteikarte(k5);
		stapel2.addKarteikarte(k6);
		stapel2.addKarteikarte(k7);
		stapel2.addKarteikarte(k8);

		UUID k9uuid = UUID.fromString("133ca2f2-52d7-4a4c-af3d-758ca3666c0e");
		UUID k10uuid = UUID.fromString("b9fd5232-76cf-4200-8fa7-7987854611f7");
		UUID k11uuid = UUID.fromString("d1625f83-75d8-48f7-80e1-a19436b2e4c0");
		UUID k12uuid = UUID.fromString("e7d45b3a-66dd-4cb9-a7af-32c318cce22d");
		Karteikarte k9 = KarteikarteMother.newKarteWithIdAndFälligAm(k9uuid, LocalDateTime.of(2025, 1, 12, 10, 0));
		Karteikarte k10 = KarteikarteMother.newKarteWithIdAndFälligAm(k10uuid, LocalDateTime.of(2025, 1, 1, 10, 0));
		Karteikarte k11 = KarteikarteMother.newKarteWithIdAndFälligAm(k11uuid, LocalDateTime.of(2025, 1, 2, 10, 0));
		Karteikarte k12 = KarteikarteMother.newKarteWithIdAndFälligAm(k12uuid, LocalDateTime.of(2025, 1, 3, 10, 0));
		stapel3.addKarteikarte(k9);
		stapel3.addKarteikarte(k10);
		stapel3.addKarteikarte(k11);
		stapel3.addKarteikarte(k12);

		manyStapel.add(stapel1);
		manyStapel.add(stapel2);
		manyStapel.add(stapel3);

		return manyStapel;
	}

	public static Stapel initSetWithoutKarteikarten() {
		Stapel set = new Stapel(
				UUID.randomUUID(),
				UUID.randomUUID(),
				"TestSet",
				"TestSet Beschreibung",
				"10m,1d",
				"user1",
				new ArrayList<>());

		return set;
	}

	public static Stapel initSetWithIds(String setId, String karteikarteId) {
		List<Karteikarte> karteikarten = new LinkedList<>();

		Karteikarte karte = new Karteikarte(
				UUID.fromString(karteikarteId),
				"Frage 1",
				"Antwort 1",
				new ArrayList<>(),
				LocalDateTime.now(),
				LocalDateTime.now(),
				LocalDateTime.now(),
				"Notiz 1",
				0,
				FrageTyp.NORMAL,
				10,
				"10m,10h,10d");

		karteikarten.add(karte);

		Stapel set = new Stapel(
				UUID.fromString(setId),
				UUID.randomUUID(),
				"TestSet",
				"TestSet Beschreibung",
				"10m,1d",
				"user1",
				karteikarten);

		return set;
	}

	public static Stapel initSetWithALotKarteikarten() {
		LocalDateTime ldt1 = LocalDateTime.of(2025, 1, 15, 10, 0, 0);
		LocalDateTime ldt2 = LocalDateTime.of(2025, 1, 16, 10, 0, 0);
		LocalDateTime ldt3 = LocalDateTime.of(2025, 1, 17, 10, 0, 0);
		LocalDateTime ldt4 = LocalDateTime.of(2025, 1, 18, 10, 0, 0);
		LocalDateTime ldt5 = LocalDateTime.of(2025, 1, 19, 10, 0, 0);
		LocalDateTime ldt6 = LocalDateTime.of(2025, 1, 20, 10, 0, 0);
		LocalDateTime ldt7 = LocalDateTime.of(2025, 1, 21, 10, 0, 0);
		LocalDateTime ldt8 = LocalDateTime.of(2025, 1, 22, 10, 0, 0);
		LocalDateTime ldt9 = LocalDateTime.of(2025, 1, 23, 10, 0, 0);
		LocalDateTime ldt10 = LocalDateTime.of(2025, 1, 24, 10, 0, 0);

		Karteikarte karte1 = newKarteikarteWithFälligAm(ldt1);
		Karteikarte karte2 = newKarteikarteWithFälligAm(ldt2);
		Karteikarte karte3 = newKarteikarteWithFälligAm(ldt3);
		Karteikarte karte4 = newKarteikarteWithFälligAm(ldt4);
		Karteikarte karte5 = newKarteikarteWithFälligAm(ldt5);
		Karteikarte karte6 = newKarteikarteWithFälligAm(ldt6);
		Karteikarte karte7 = newKarteikarteWithFälligAm(ldt7);
		Karteikarte karte8 = newKarteikarteWithFälligAm(ldt8);
		Karteikarte karte9 = newKarteikarteWithFälligAm(ldt9);
		Karteikarte karte10 = newKarteikarteWithFälligAm(ldt10);

		List<Karteikarte> karteikarten = List.of(
				karte1, karte2, karte3, karte4, karte5,
				karte6, karte7, karte8, karte9, karte10);


		Stapel set = new Stapel(
				UUID.randomUUID(),
				UUID.randomUUID(),
				"TestSet",
				"TestSet Beschreibung",
				"10m,1d",
				"user1",
				karteikarten);

		return set;
	}

	public static Stapel initSetWithALotNewKarteikarten() {
		Karteikarte karte1 = newKarteikarteWithLevel(0);
		Karteikarte karte2 = newKarteikarteWithLevel(0);
		Karteikarte karte3 = newKarteikarteWithLevel(0);
		Karteikarte karte4 = newKarteikarteWithLevel(1);
		Karteikarte karte5 = newKarteikarteWithLevel(1);
		Karteikarte karte6 = newKarteikarteWithLevel(1);
		Karteikarte karte7 = newKarteikarteWithLevel(2);
		Karteikarte karte8 = newKarteikarteWithLevel(2);
		Karteikarte karte9 = newKarteikarteWithLevel(3);
		Karteikarte karte10 = newKarteikarteWithLevel(3);

		List<Karteikarte> karteikarten = List.of(
				karte1, karte2, karte3, karte4, karte5,
				karte6, karte7, karte8, karte9, karte10);


		Stapel set = new Stapel(
				UUID.randomUUID(),
				UUID.randomUUID(),
				"TestSet",
				"TestSet Beschreibung",
				"10m,1d",
				"user1",
				karteikarten);

		return set;
	}

	public static Stapel initStapelWithLernIntervalleAndWithKarteikartenLevel(String lernIntervalle, int level) {
		return new Stapel(
				UUID.randomUUID(),
				UUID.randomUUID(),
				"TestSet",
				"TestSet Beschreibung",
				lernIntervalle,
				"user1",
				List.of(newKarteikarteWithLevel(level)));
	}

	public static Stapel initStapelWithKarteikarteLevelAndLernstufen(int level, String lernstufen) {
		return new Stapel(
				UUID.randomUUID(),
				UUID.randomUUID(),
				"TestSet",
				"TestSet Beschreibung",
				lernstufen,
				"user1",
				List.of(newKarteikarteWithLevelAndLernstufen(level, lernstufen)));
	}

	public static Stapel initSetWithoutModulFachId() {
		return new Stapel(
				UUID.randomUUID(),
				null,
				"TestSet",
				"TestSet Beschreibung",
				"10m,1d",
				"user1",
				new ArrayList<>());
	}

	public static Stapel initStapelWithNAnworten(int n) {
		Stapel s =  new Stapel(
				UUID.randomUUID(),
				null,
				"TestSet",
				"TestSet Beschreibung",
				"10m,1d",
				"user1",
				new ArrayList<>());

		s.addKarteikarte(KarteikarteMother.newKarteWithAntworten(s.getFachId(), n));

		return s;
	}

	public static List<Stapel> initSomeStapel() {
		List<Stapel> manyStapel = new LinkedList<>();

		Stapel stapel1 = new Stapel(UUID.randomUUID(), UUID.randomUUID(), "stapel1", "beschreibung1", "2m,2h,2d", "user1", new LinkedList<>());
		Stapel stapel2 = new Stapel(UUID.randomUUID(), UUID.randomUUID(), "stapel2", "beschreibung2", "2m,2h,2d", "user2", new LinkedList<>());
		Stapel stapel3 = new Stapel(UUID.randomUUID(), UUID.randomUUID(), "stapel1", "beschreibung1", "2m,2h,2d", "user2", new LinkedList<>());
		Stapel stapel4 = new Stapel(UUID.randomUUID(), UUID.randomUUID(), "stapel2", "beschreibung2", "2m,2h,2d", "user2", new LinkedList<>());
		Stapel stapel5 = new Stapel(UUID.randomUUID(), UUID.randomUUID(), "stapel1", "beschreibung1", "2m,2h,2d", "user1", new LinkedList<>());
		Stapel stapel6 = new Stapel(UUID.randomUUID(), UUID.randomUUID(), "stapel2", "beschreibung2", "2m,2h,2d", "user1", new LinkedList<>());
		Stapel stapel7 = new Stapel(UUID.randomUUID(), UUID.randomUUID(), "stapel1", "beschreibung1", "2m,2h,2d", "user2", new LinkedList<>());
		Stapel stapel8 = new Stapel(UUID.randomUUID(), UUID.randomUUID(), "stapel2", "beschreibung2", "2m,2h,2d", "user2", new LinkedList<>());
		Stapel stapel9 = new Stapel(UUID.randomUUID(), UUID.randomUUID(), "stapel1", "beschreibung1", "2m,2h,2d", "user1", new LinkedList<>());
		Stapel stapel10 = new Stapel(UUID.randomUUID(), UUID.randomUUID(), "stapel2", "beschreibung2", "2m,2h,2d", "user2", new LinkedList<>());

		manyStapel.add(stapel1);
		manyStapel.add(stapel2);
		manyStapel.add(stapel3);
		manyStapel.add(stapel4);
		manyStapel.add(stapel5);
		manyStapel.add(stapel6);
		manyStapel.add(stapel7);
		manyStapel.add(stapel8);
		manyStapel.add(stapel9);
		manyStapel.add(stapel10);

		return manyStapel;
	}
}
