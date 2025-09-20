package com.studyhub.mindmap.adapter.kafka;

import com.studyhub.kafka.dto.UserDto;
import com.studyhub.mindmap.application.service.IUserDeletionConsumer;
import com.studyhub.mindmap.application.service.MindmapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Consumer-Klasse für den Fall einer Benutzerlöschung, die im authentication-service produziert wird.
 * Alle Daten des Benutzers werden gelöscht.
 * topic: 'user-deletion'
 */
@Profile("dev-kafka")
@Service
public class UserDeletionConsumerImpl implements IUserDeletionConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDeletionConsumerImpl.class);

	private final MindmapService mindmapService;

	public UserDeletionConsumerImpl(MindmapService mindmapService) {
		this.mindmapService = mindmapService;
	}

	@Override
	@KafkaListener(
			topics="user-deletion",
			groupId ="studyhuppy-mindmap"
	)
	public void consumeUserDeletion(UserDto userDto) {
		deleteAllUserData(userDto);
	}

	@Override
	public void deleteAllUserData(UserDto userDto) {
		String username = userDto.username();
		mindmapService.deleteAllMindmapsByUsername(username);

		LOGGER.info("Deleted all associated user data from service 'mindmap'");
	}
}