package com.studyhub.authentication.model;

public class UserMapper {

    public static UserDto toDto(AppUser user) {
        String filename = user.getProfilbildPath().split("/")[3];
        return new UserDto(user.getUserId(),
                user.getMail(),
                user.getUsername(),
                user.getNotificationSubscription(),
                user.getSemester(),
                filename);
    }
}
