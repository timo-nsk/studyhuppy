package com.studyhub.track.adapter.db.session;

import com.studyhub.track.application.service.SessionBeendetEventRepository;
import com.studyhub.track.domain.model.session.SessionBeendetEvent;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import static com.studyhub.track.adapter.db.session.SessionBeendetEventMapper.*;

@Repository
public class SessionBeendetEventRepositoryImpl implements SessionBeendetEventRepository {

	private SessionBeendetEventDao eventDao;

	public SessionBeendetEventRepositoryImpl(SessionBeendetEventDao eventDao) {
		this.eventDao = eventDao;
	}


	@Override
	public SessionBeendetEvent save(SessionBeendetEvent event) {
		SessionBeendetEventDto dto = toDto(event);
		return toDomain(eventDao.save(dto));
	}

	@Override
	public List<SessionBeendetEvent> findAllByUsername(String username) {
		return eventDao.findAllByUsername(username).stream()
				.map(SessionBeendetEventMapper::toDomain)
				.toList();
	}

	@Override
	public SessionBeendetEvent findByEventId(UUID eventId) {
		return toDomain(eventDao.findByEventId(eventId));
	}

	@Override
	public int deleteAllByUsername(String username) {
		return eventDao.deleteAllByUsername(username);
	}
}
