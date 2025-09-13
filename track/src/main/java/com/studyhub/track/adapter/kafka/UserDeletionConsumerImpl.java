package com.studyhub.track.adapter.kafka;

import com.studyhub.kafka.dto.UserDto;
import com.studyhub.track.application.service.IUserDeletionConsumer;
import com.studyhub.track.application.service.LernplanService;
import com.studyhub.track.application.service.ModulService;
import com.studyhub.track.application.service.SessionService;
import com.studyhub.track.domain.model.lernplan.Lernplan;
import com.studyhub.track.domain.model.modul.Modul;
import com.studyhub.track.domain.model.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Consumer-Klasse für den Fall einer Benutzerlöschung, die im authentication-service produziert wird.
 * Alle Daten des Benutzers werden gelöscht.
 * topic: 'user-deletion'
 */
@Service
public class UserDeletionConsumerImpl implements IUserDeletionConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDeletionConsumerImpl.class);

	private final ModulService modulService;
	private final LernplanService lernplanService;
	private final SessionService sessionService;

	public UserDeletionConsumerImpl(
			ModulService modulService,
			LernplanService lernplanService,
			SessionService sessionService) {
		this.modulService = modulService;
		this.lernplanService = lernplanService;
		this.sessionService = sessionService;
	}

	@Override
	@KafkaListener(
			topics="user-deletion",
			groupId ="studyhuppy"
	)
	public void consumeUserDeletion(UserDto userDto) {
		deleteAllUserData(userDto);
	}

	@Override
	public void deleteAllUserData(UserDto userDto) {
		String username = userDto.username();
		List<Modul> allModules = modulService.findAllByUsername(username);
		List<Lernplan> allLernplaene = lernplanService.getAllLernplaeneByUsername(username);
		List<Session> allSession = sessionService.getSessionsByUsername(username);

		for (Modul modul : allModules) modulService.deleteModul(modul.getFachId(), userDto.username());
		for(Lernplan lernplan : allLernplaene) lernplanService.deleteLernplanByFachId(lernplan.getFachId());
		for (Session session : allSession) sessionService.deleteSession(session.getFachId());

		LOGGER.info("Deleted all associated user data from service 'modul'");
	}
}