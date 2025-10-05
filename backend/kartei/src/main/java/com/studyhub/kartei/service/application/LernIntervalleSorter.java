package com.studyhub.kartei.service.application;

import java.util.Arrays;
import java.util.List;

public class LernIntervalleSorter {

	public static String[] sortIntervalleLexikographisch(String[] intervalle) {
		List<String> times = Arrays.asList(intervalle);

		times.sort((a, b) -> {
			// Extrahiere die Einheit (m, h oder d)
			char unitA = a.charAt(a.length() - 1);
			char unitB = b.charAt(b.length() - 1);

			// Definiere die Sortierreihenfolge: m (Minuten) < h (Stunden) < d (Tage)
			String order = "mhd";
			int unitComparison = Integer.compare(order.indexOf(unitA), order.indexOf(unitB));

			if (unitComparison != 0) {
				return unitComparison; // Sortiere nach Einheit
			}

			// Falls gleiche Einheit, nach der numerischen Größe aufsteigend sortieren
			int numA = Integer.parseInt(a.substring(0, a.length() - 1));
			int numB = Integer.parseInt(b.substring(0, b.length() - 1));

			return Integer.compare(numA, numB); // Aufsteigende Reihenfolge
		});

		return times.toArray(new String[0]);
	}
}
