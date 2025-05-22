package com.studyhub.kartei.adapter.web.controller;

public record RemoveAntwortRequest(
		String stapelId,
		String karteId,
		Integer antwortIndex
) {
}
