package com.studyhub.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MessagePreparer {

	public static SimpleMailMessage prepareSimpleMailMessage(String recipient, String subject, String text) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("noreply@example.com");
		message.setTo(recipient);
		message.setSubject(subject);
		message.setText(text);
		return message;
	}

	public static MimeMessage prepareMimeMessage(JavaMailSender jms, String subject, String text, String from, String to) {
		MimeMessage mimeMessage = jms.createMimeMessage();
		MimeMessageHelper helper;
		try {
			helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject("Zeit zu lernen!");
			helper.setText(text, true);
		} catch (MessagingException e) {
			return null;
		}

		return mimeMessage;
	}
}
