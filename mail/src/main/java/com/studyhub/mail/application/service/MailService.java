package com.studyhub.mail.application.service;

import com.studyhub.mail.domain.model.MailTyp;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.studyhub.mail.application.service.MessagePreparer.*;

@Service
public class MailService {
	@Value("${application.mail.address}")
	private String appMailAddress;

	private JavaMailSender mailSender;
	private MailGesendetEventService mailGesendetEventService;

	public MailService(JavaMailSender mailSender, MailGesendetEventService mailGesendetEventService) {
		this.mailSender = mailSender;
		this.mailGesendetEventService = mailGesendetEventService;
	}

	void sendSimpleMailMessage(String recipient, String subject, String text) {
		SimpleMailMessage message = prepareSimpleMailMessage(recipient, subject, text);
		mailSender.send(message);
	}

	public void sendLernNotification(String text, String to){
		MimeMessage mimeMessage = prepareMimeMessage(mailSender, "Studyhuppy - Zeit zu lernen!", text, appMailAddress, to);

		try {
			mailSender.send(mimeMessage);
			mailGesendetEventService.prepareSavingEvent(MailTyp.LERNEN_NOTIFICATION, true);
		} catch (MailException e) {
			mailGesendetEventService.prepareSavingEvent(MailTyp.LERNEN_NOTIFICATION, false);
			throw new RuntimeException(e);
		}

	}

	public void sendRegistrationConfirmation(String text, String to) {
		MimeMessage mimeMessage = prepareMimeMessage(mailSender, "Studyhuppy - Registrierung erfolgreich", text, appMailAddress, to);

		try {
			mailSender.send(mimeMessage);
			mailGesendetEventService.prepareSavingEvent(MailTyp.REGISTRATION_CONFIRMATION, true);
		} catch (MailException e) {
			mailGesendetEventService.prepareSavingEvent(MailTyp.REGISTRATION_CONFIRMATION, false);
			throw new RuntimeException(e);
		}
	}

	public void sendKlausurReminder(String text, String to) {
		MimeMessage mimeMessage = prepareMimeMessage(mailSender, "Studyhuppy - Klausuren?", text, appMailAddress, to);

		mailSender.send(mimeMessage);
	}

	public void sendEmailChangeConfirmation(String text, String to) throws MessagingException {
		MimeMessage mimeMessage = prepareMimeMessage(mailSender, "Studyhuppy - E-Mail-Addresse geändert", text, appMailAddress, to);


		mailSender.send(mimeMessage);
		mailGesendetEventService.prepareSavingEvent(MailTyp.USER_DATA_CHANGE, true);
	}

	public void sendPasswordChangeConfirmation(String text, String to) {
		MimeMessage mimeMessage = prepareMimeMessage(mailSender, "Studyhuppy - Passwort geändert", text, appMailAddress, to);


		mailSender.send(mimeMessage);
		mailGesendetEventService.prepareSavingEvent(MailTyp.USER_DATA_CHANGE, true);

	}
}
