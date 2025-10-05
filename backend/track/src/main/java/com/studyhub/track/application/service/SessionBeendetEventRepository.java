package com.studyhub.track.application.service;

import com.studyhub.track.domain.model.session.SessionBeendetEvent;

import java.util.List;
import java.util.UUID;

public interface SessionBeendetEventRepository {
	SessionBeendetEvent save(SessionBeendetEvent event);
	List<SessionBeendetEvent> findAllByUsername(String username);
	SessionBeendetEvent findByEventId(UUID eventId);
	int deleteAllByUsername(String username);
}
