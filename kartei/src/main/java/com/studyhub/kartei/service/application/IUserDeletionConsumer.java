package com.studyhub.kartei.service.application;

import com.studyhub.kafka.dto.UserDto;

public interface IUserDeletionConsumer {

	void consumeUserDeletion(UserDto userDto);
	void deleteAllUserData(UserDto userDto);
}
