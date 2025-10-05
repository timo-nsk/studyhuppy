package com.studyhub.kartei.domain.model;

import java.util.List;

public enum FrageTyp {
	NORMAL("Normal"),
	SINGLE_CHOICE("Single Choice"),
	MULTIPLE_CHOICE("Multiple Choice");

	private final String displayName;

	FrageTyp(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public static List<FrageTyp> allTypes() {
		return List.of(values());
	}
}
