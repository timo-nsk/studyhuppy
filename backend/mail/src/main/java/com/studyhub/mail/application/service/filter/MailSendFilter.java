package com.studyhub.mail.application.service.filter;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Component
public class MailSendFilter {

    private MimeMessage thisMimeMessage;
    private JavaMailSender mailSender;

    @Autowired
    public MailSendFilter(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setMessage(String subject, String text, String from, String to) throws MessagingException {
        thisMimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(thisMimeMessage, true, "UTF-8");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true);
    }

    public void send() throws MailException {
        mailSender.send(thisMimeMessage);
    }
}
