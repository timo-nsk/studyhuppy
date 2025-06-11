package com.studyhub.mail.application.service;

import com.studyhub.mail.application.service.filter.MailGesendetEventFilter;
import com.studyhub.mail.application.service.filter.MailSendFilter;
import com.studyhub.mail.application.service.filter.TemplateFilter;
import com.studyhub.mail.domain.model.MailTyp;
import jakarta.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailSendException;
import org.springframework.stereotype.Component;

@Component
public class MailingPipe {
    private MailingPipeState state = MailingPipeState.START;
    private final Logger log = LoggerFactory.getLogger(MailingPipe.class);
    private final TemplateFilter templateFilter;
    private final MailSendFilter mailSendFilter;
    private final MailGesendetEventFilter mailGesendetEventFilter;
    private String processedTemplate;

    public MailingPipe(TemplateFilter templateFilter,
                       MailSendFilter mailSendFilter,
                       MailGesendetEventFilter mailGesendetEventFilter) {
        this.templateFilter = templateFilter;
        this.mailSendFilter = mailSendFilter;
        this.mailGesendetEventFilter = mailGesendetEventFilter;
    }

    public MailingPipe withContextVariable(String value, Object obj) {
        checkState(MailingPipeState.START, MailingPipeState.TEMPLATE_READY);
        templateFilter.setContextVariable(value, obj);
        return this;
    }

    public MailingPipe withTemplatePath(String path) {
        checkState(MailingPipeState.START, MailingPipeState.TEMPLATE_READY);
        this.processedTemplate = templateFilter.setTemplatePath(path);
        this.state = MailingPipeState.TEMPLATE_READY;
        return this;
    }

    public MailingPipe withMessage(String subject, String from, String to) {
        try {
            checkState(MailingPipeState.TEMPLATE_READY);
            mailSendFilter.setMessage(subject, this.processedTemplate, from, to);
            this.state = MailingPipeState.MESSAGE_READY;
            return this;
        } catch (MessagingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public MailingPipe sendMail() {
        try {
            checkState(MailingPipeState.MESSAGE_READY);
            mailSendFilter.send();
            this.state = MailingPipeState.MAIL_SENT;
            log.info("successfully sent mail");
            return this;
        } catch (MailSendException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public MailingPipe saveMailGesendetEvent(MailTyp typ, boolean erfolgreichVersendet) {
        checkState(MailingPipeState.MAIL_SENT);
        mailGesendetEventFilter.save(typ, erfolgreichVersendet);
        this.state = MailingPipeState.END;
        return this;
    }

    public void close() {
        this.state = MailingPipeState.START;
    }

    public void checkState(MailingPipeState... validStates) {
        for (MailingPipeState valid : validStates) {
            if (this.state == valid) return;
        }
        throw new IllegalStateException(this.state + " not allowed yet.");
    }
}