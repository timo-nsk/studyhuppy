package com.studyhub.track.application.service;

import com.studyhub.kafka.dto.UserDto;

public interface IUserDeletionConsumer {

	void consumeUserDeletion(UserDto userDto);
	void deleteAllUserData(UserDto userDto);
}
