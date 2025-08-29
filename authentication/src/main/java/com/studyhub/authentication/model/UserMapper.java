package com.studyhub.authentication.model;

public class UserMapper {

    public static UserDto toDto(AppUser user) {
        return new UserDto(user.getUserId(),
                user.getMail(),
                user.getUsername(),
                user.getNotificationSubscription(),
                user.getSemester(),
                user.getProfilbildPath());
    }
}
