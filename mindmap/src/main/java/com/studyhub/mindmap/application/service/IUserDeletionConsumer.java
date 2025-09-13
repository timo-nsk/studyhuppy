package com.studyhub.mindmap.application.service;

import com.studyhub.kafka.dto.UserDto;

public interface IUserDeletionConsumer {

	void consumeUserDeletion(UserDto userDto);
	void deleteAllUserData(UserDto userDto);
}
