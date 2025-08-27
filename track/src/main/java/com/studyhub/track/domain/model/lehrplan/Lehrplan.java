package com.studyhub.track.domain.model.lehrplan;

import java.util.List;
import java.util.UUID;

public class Lehrplan {
	private UUID fachId;
	private String username;
	private String titel;
	private List<Tag> tagesListe;
	private boolean isActive;
}
