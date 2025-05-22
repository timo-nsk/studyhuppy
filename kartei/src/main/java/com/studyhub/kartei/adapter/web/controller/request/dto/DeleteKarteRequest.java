package com.studyhub.kartei.adapter.web.controller.request.dto;

@Dto
public record DeleteKarteRequest(
        String stapelId,
        String karteId
) {
}
