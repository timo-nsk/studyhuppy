package com.studyhub.track.adapter.db.lernplan;

import com.studyhub.track.domain.model.lernplan.Tag;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Table("lernplan")
public record LernplanDto(
		@Id Long id,
		UUID fachId,
		String username,
		String titel,
		List<Tag> tagesListe,
		boolean isActive
) {
}
