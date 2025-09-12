package com.studyhub.authentication.adapter.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserDeletionProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserDeletionProducer.class);

	private KafkaTemplate<String, String> kafkaTemplate;

	public UserDeletionProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate = kafkaTemplate;
	}

	public void sendMessage(String userId) {
		LOGGER.info(String.format("Producing message for user deletion with user-id: %s", userId));
		kafkaTemplate.send("user-deletion", userId);
	}
}
