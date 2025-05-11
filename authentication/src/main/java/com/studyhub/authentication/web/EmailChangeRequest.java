package com.studyhub.authentication.web;

public record EmailChangeRequest(String userId, String newMail) {
}
