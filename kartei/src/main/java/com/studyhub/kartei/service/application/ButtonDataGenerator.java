package com.studyhub.kartei.service.application;

import com.studyhub.kartei.domain.model.Karteikarte;
import com.studyhub.kartei.domain.model.Schwierigkeit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ButtonDataGenerator {

	private Karteikarte kartekarte;
	private List<ButtonData> buttonDataList;

	public ButtonDataGenerator(Karteikarte karteikarte) {
		this.kartekarte = karteikarte;
		this.buttonDataList = new ArrayList<>();
	}

	public List<ButtonData> generateButtons() {
		String[] splittedIntervalle = kartekarte.getLernstufen().split(",");

		Deque<Schwierigkeit> schwierigkeiten = new ArrayDeque<>(List.of(Schwierigkeit.HARD, Schwierigkeit.NORMAL, Schwierigkeit.EASY));

		for (int i = 0; i <= 2; i++) {
			ButtonData data = new ButtonData(splittedIntervalle[i], schwierigkeiten.peekFirst());
			buttonDataList.add(data);
			schwierigkeiten.pollFirst();
		}

		return buttonDataList;
	}
}