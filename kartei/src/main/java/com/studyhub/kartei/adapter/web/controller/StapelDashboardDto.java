package com.studyhub.kartei.adapter.web.controller;

public record StapelDashboardDto(
        String name,
        String fachId,
        int vorraussichtlicheLernzeit,
        long anzahlNeueKarteikarten,
        int anzahlFaelligeKarteikarten
        ) {
}
