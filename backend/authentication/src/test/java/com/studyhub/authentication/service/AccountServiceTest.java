package com.studyhub.authentication.service;

import com.studyhub.authentication.adapter.kartei.KarteiRequestService;
import com.studyhub.authentication.adapter.track.TrackRequestService;
import com.studyhub.authentication.db.AppUserRepository;
import com.studyhub.authentication.config.JWTService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

	@Mock AppUserRepository appUserRepository;
	@Mock TrackRequestService trackRequestService;
	@Mock KarteiRequestService karteiRequestService;
	@Mock
	JWTService jwtService;

	@InjectMocks
	AccountService accountService;


	@Test
	@DisplayName("Wenn der Benutzer Benachrichtigungen aktiviert, wird der Wert in der Datenbank aktualisiert")
	void test_6() {
		accountService.editNotificationSubscription(true, "username");

		verify(appUserRepository).updateNotificationSubscription(true, "username");
	}

	@Test
	@DisplayName("Wenn der Benutzer Benachrichtigungen deaktiviert, wird der Wert in der Datenbank aktualisiert")
	void test_7() {
		accountService.editNotificationSubscription(false, "username");

		verify(appUserRepository).updateNotificationSubscription(false, "username");
	}

	@Test
	@DisplayName("Wenn der Status der Notification-Subscription abgefragt wird, wird dieser aus der Datenbank gelesen")
	void test_8() {
		accountService.getNotificationSubscription("username");
		verify(appUserRepository).getNotificationSubscription("username");
	}

}
