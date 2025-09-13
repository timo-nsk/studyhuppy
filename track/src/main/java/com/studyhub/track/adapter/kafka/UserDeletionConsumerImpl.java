package com.studyhub.track.adapter.kafka;

import com.studyhub.kafka.dto.UserDto;
import com.studyhub.track.application.service.IUserDeletionConsumer;
import com.studyhub.track.application.service.ModulService;
import com.studyhub.track.domain.model.modul.Modul;
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

	public UserDeletionConsumerImpl(ModulService modulService) {
		this.modulService = modulService;
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
		List<Modul> allModules = modulService.findAllByUsername(userDto.username());

		for (Modul modul : allModules) {
			modulService.deleteModul(modul.getFachId(), userDto.username());
		}

		LOGGER.info("Deleted all associated user data from service 'modul'");
	}
}