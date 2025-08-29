package com.studyhub.authentication.model;

import java.util.UUID;

public record UserDto(UUID userId,
                      String mail,
                      String username,
                      Boolean notificationSubscription,
                      Integer semester,
                      String profilbildPath) {
}
