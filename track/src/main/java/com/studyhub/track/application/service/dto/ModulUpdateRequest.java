package com.studyhub.track.application.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ModulUpdateRequest(
		@JsonProperty("fachId") String fachId,
		@JsonProperty("secondsLearned") int secondsLearned,
		@JsonProperty("secondsLearnedThisSession") int secondsLearnedThisSession
) {
	@JsonCreator
	public ModulUpdateRequest { }
}
