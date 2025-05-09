package com.studyhub.kartei.adapter.web.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SetFragetypRequest {

	private String frageTyp;
	private String karteiSetId;

	public boolean isValidRequest() {
		return isValidFrageTyp() && isValidUuid();
	}

	private boolean isValidFrageTyp() {
		if (frageTyp == null) return false;

		if (frageTyp.equals("NORMAL") ||
				frageTyp.equals("SINGLE_CHOICE") ||
				frageTyp.equals("MULTIPLE_CHOICE")) {
			return true;
		}
		return false;
	}

	private boolean isValidUuid() {
		if (karteiSetId == null) return false;

		try {
			UUID.fromString(karteiSetId);
			return true;
		} catch (IllegalArgumentException | NullPointerException e) {
			return false;
		}
	}
}
