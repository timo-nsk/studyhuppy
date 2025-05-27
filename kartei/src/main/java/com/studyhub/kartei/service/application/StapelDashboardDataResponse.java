package com.studyhub.kartei.service.application;

public record StapelDashboardDataResponse(
        String name,
        String fachId,
        int vorraussichtlicheLernzeit,
        long anzahlNeueKarteikarten,
        int anzahlFaelligeKarteikarten
        ) {
}
