package com.studyhub.kartei.adapter.db.dto;

import com.studyhub.kartei.domain.model.Karteikarte;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Table("stapel")
public record StapelDto(@Id Integer id,
                        UUID fachId,
                        UUID modulFachId,
                        String name,
                        String beschreibung,
                        String lernIntervalle,
						String username,
                        List<Karteikarte> karteikarten) {
}
