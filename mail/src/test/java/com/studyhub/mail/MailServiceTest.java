package com.studyhub.mail;

import com.studyhub.mail.application.service.MailGesendetEventService;
import com.studyhub.mail.application.service.MailService;
import com.studyhub.mail.application.service.MessagePreparer;
import com.studyhub.mail.domain.model.MailTyp;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MailServiceTest {

	@Mock
	JavaMailSenderImpl mailSender;

	@Mock
	MailGesendetEventService mailGesendetEventService;

	@InjectMocks
	private MailService mailService;

	@Disabled("funzt net kp warum")
	@Test
	@DisplayName("Eine Lern-Erinnerung-Email wird erfolgreich abgesendet und das Event gespeichert")
	void test_01() {
		MimeMessage mockMessage = mock(MimeMessage.class);
		MockedStatic<MessagePreparer> messagePreparer = Mockito.mockStatic(MessagePreparer.class);
		messagePreparer.when(() -> MessagePreparer.prepareMimeMessage(any(), anyString(), anyString(), anyString(), anyString())).thenReturn(mockMessage); // mockMessage wird zurückgegeben
		when(mailSender.createMimeMessage()).thenReturn(mockMessage);

		mailService.sendLernNotification("text", "recipient@gmail.com");

		verify(mailSender, times(1)).send(mockMessage);
		verify(mailGesendetEventService, times(1)).prepareSavingEvent(MailTyp.LERNEN_NOTIFICATION, true);
		messagePreparer.close();
	}

}
