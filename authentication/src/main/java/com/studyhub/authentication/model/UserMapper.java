package com.studyhub.authentication.model;

public class UserMapper {

    public static UserDto toDto(AppUser user) {
        String profilbildPath = user.getProfilbildPath();
        String filename = "none";
        if(!profilbildPath.equals("none")) {
            filename = user.getProfilbildPath().split("/")[3];
        }
        return new UserDto(user.getUserId(),
                user.getMail(),
                user.getUsername(),
                user.getNotificationSubscription(),
                user.getSemester(),
                filename);
    }
}
