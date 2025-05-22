package com.studyhub.kartei.adapter.web.controller.request.dto;

@Dto
public record StapelDashboardDataResponse(
        String name,
        String fachId,
        int vorraussichtlicheLernzeit,
        long anzahlNeueKarteikarten,
        int anzahlFaelligeKarteikarten
        ) {
}
