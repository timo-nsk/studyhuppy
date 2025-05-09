package com.studyhub.track.adapter.web;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record FachIdRequest(@JsonProperty("fachId") String fachId) {
	@JsonCreator
	public FachIdRequest { }
}
