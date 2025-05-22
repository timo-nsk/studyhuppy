package com.studyhub.kartei.adapter.web.controller.request.dto;
@Dto
public record RemoveAntwortRequest(
		String stapelId,
		String karteId,
		Integer antwortIndex
) {
}
