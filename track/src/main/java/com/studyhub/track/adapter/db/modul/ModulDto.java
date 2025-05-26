package com.studyhub.track.adapter.db.modul;

import com.studyhub.track.domain.model.modul.Kreditpunkte;
import com.studyhub.track.domain.model.modul.Lerntage;
import com.studyhub.track.domain.model.modul.Modultermin;
import com.studyhub.track.domain.model.semester.Semester;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Table("modul")
public record ModulDto(@Id Integer id,
                       UUID fachId,
                       String name,
                       Integer secondsLearned,
                       Kreditpunkte kreditpunkte,
                       String username,
                       boolean active,
                       int semesterstufe,
                       Semester semester,
                       LocalDateTime klausurDate,
                       Lerntage lerntage,
                       List<Modultermin> modultermine) {
}
