package com.studyhub.kartei.adapter.kafka;

import com.studyhub.kafka.dto.UserDto;
import com.studyhub.kartei.domain.model.Stapel;
import com.studyhub.kartei.service.application.IUserDeletionConsumer;
import com.studyhub.kartei.service.application.KarteikarteService;
import com.studyhub.kartei.service.application.StapelService;
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

	private StapelService stapelService;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDeletionConsumerImpl.class);

	public UserDeletionConsumerImpl(StapelService stapelService) {
		this.stapelService = stapelService;
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
		List<Stapel> allStapel = stapelService.findByUsername(username);

		for (Stapel stapel : allStapel) {
			stapelService.deleteStapelByFachId(stapel.getFachId());
		}


		LOGGER.info("Deleted all associated user data from service 'modul'");
	}
}