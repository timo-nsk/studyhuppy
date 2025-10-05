package com.studyhub.track.domain.model.modul;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Kreditpunkte {
	private int anzahlPunkte;
	private int kontaktzeitStunden;
	private int selbststudiumStunden;
}
