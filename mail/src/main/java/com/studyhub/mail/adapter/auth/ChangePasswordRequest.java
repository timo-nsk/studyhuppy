package com.studyhub.mail.adapter.auth;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String userId;
    private String oldPw;
    private String newPw;
    private String username;
    private String mail;
}
