package com.studyhub.track.application.service.dto;

import com.studyhub.track.domain.model.session.Block;
import com.studyhub.track.domain.model.session.Session;

import java.util.List;
import java.util.UUID;

public record SessionRequest(
		String fachId,
		String titel,
		String beschreibung,
		List<Block> blocks
) {

	public Session toEntity(String username) {
		for(Block block : blocks) if(block.getFachId() == null) block.setFachId(UUID.randomUUID());

		return new Session(
				UUID.fromString(fachId),
				username,
				titel,
				beschreibung,
				blocks
		);
	}
}