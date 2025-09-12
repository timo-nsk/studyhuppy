package com.studyhub.track.adapter.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class UserDeletionTopicConfig {

	@Bean
	public NewTopic userDeletionTopic() {
		return TopicBuilder
				.name("user-deletion")
				.build();
	}
}