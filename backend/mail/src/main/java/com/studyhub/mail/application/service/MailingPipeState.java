package com.studyhub.mail.application.service;

public enum MailingPipeState {
    START,
    TEMPLATE_READY,
    MESSAGE_READY,
    MAIL_SENT,
    END
}