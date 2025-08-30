package com.studyhub.track.adapter.web.controller.request.dto;

import com.studyhub.track.domain.model.session.Block;
import java.util.List;

public record LernplanSessionInfoDto(
		String weekday,
		String beginn,
		String sessionId,
		List<Block> blocks
) {
}