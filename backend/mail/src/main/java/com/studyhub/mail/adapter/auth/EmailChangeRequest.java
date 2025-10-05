package com.studyhub.mail.adapter.auth;

import lombok.Data;

@Data
public class EmailChangeRequest {
    private String userId;
    private String newMail;
    private String username;
}
