package com.studyhub.track.application.service.dto;

import com.studyhub.track.domain.model.session.Block;
import java.util.List;

public record LernplanTagesuebersicht(
		String weekday,
		String beginn,
		String sessionId,
		List<Block> blocks
) {
}