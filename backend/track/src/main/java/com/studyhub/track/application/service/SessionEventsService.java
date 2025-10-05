package com.studyhub.track.application.service;

import com.studyhub.track.domain.model.session.SessionBeendetEvent;
import org.springframework.stereotype.Service;

@Service
public class SessionEventsService {

	private SessionBeendetEventRepository sessionBeendetEventRepository;

	public SessionEventsService(SessionBeendetEventRepository sessionBeendetEventRepository) {
		this.sessionBeendetEventRepository = sessionBeendetEventRepository;
	}

	public boolean save(SessionBeendetEvent event) {
		return sessionBeendetEventRepository.save(event) != null;
	}
}
