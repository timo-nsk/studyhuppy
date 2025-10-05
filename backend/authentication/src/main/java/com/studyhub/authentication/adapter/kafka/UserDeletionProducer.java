package com.studyhub.authentication.adapter.kafka;

import com.studyhub.kafka.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UserDeletionProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDeletionProducer.class);

	private KafkaTemplate<String, String> kafkaTemplate;

	public UserDeletionProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(String username, String userId) {
		LOGGER.info(String.format("Producing message for user deletion with user-id: %s", userId));
		UserDto dto = new UserDto(UUID.fromString(userId), username);

		Message<UserDto> message = MessageBuilder
				.withPayload(dto)
				.setHeader(KafkaHeaders.TOPIC, "user-deletion")
				.build();

		kafkaTemplate.send(message);
	}
}
