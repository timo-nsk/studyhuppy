package com.studyhub.authentication.web;

import lombok.Data;

@Data
public class EmailChangeRequest {
    private String userId;
    private String newMail;
    private String username;
}
