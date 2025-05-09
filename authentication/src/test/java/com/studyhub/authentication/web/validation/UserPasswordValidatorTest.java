package com.studyhub.authentication.web.validation;

import com.studyhub.jwt.JWTService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidatorContext;
import com.studyhub.authentication.db.AppUserRepository;
import com.studyhub.authentication.model.AppUser;
import com.studyhub.authentication.web.UserPasswordValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserPasswordValidatorTest {

	@Mock
	AppUserRepository appUserRepository;

	@Mock
	JWTService jwtService;

	@Mock
	HttpServletRequest request;

	@InjectMocks
	UserPasswordValidator userPasswordValidator;

	@Test
	@DisplayName("Wenn kein Token im Request gefunden werden kann, wird false zurückgegeben")
	void test_1() {
		ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
		Cookie[] cookies = {new Cookie("some_cookie", "some_value")};
		when(request.getCookies()).thenReturn(cookies);

		boolean valid = userPasswordValidator.isValid("password", context);

		assertThat(valid).isFalse();
	}

	@Test
	@DisplayName("Wenn ein Token im Request gefunden wird und das Passwort stimmt, wird true zurückgegeben")
	void test_2() {
		ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
		Cookie[] cookies = {new Cookie("auth_token", "some_value")};
		when(request.getCookies()).thenReturn(cookies);
		when(jwtService.extractUsername("some_value")).thenReturn("peter98");
		String hashedPassword = new BCryptPasswordEncoder().encode("password123");
		AppUser appUser = new AppUser(1, UUID.randomUUID(), "mail@mail.com", "peter98", hashedPassword, true, true,1 );
		when(appUserRepository.findByUsername("peter98")).thenReturn(appUser);

		boolean valid = userPasswordValidator.isValid("password123", context);

		assertThat(valid).isTrue();
	}

	@Test
	@DisplayName("Wenn ein Token im Request gefunden wird und das Passwort nicht stimmt, wird false zurückgegeben")
	void test_3() {
		ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
		Cookie[] cookies = {new Cookie("auth_token", "some_value")};
		when(request.getCookies()).thenReturn(cookies);
		when(jwtService.extractUsername("some_value")).thenReturn("peter98");
		String hashedPassword = new BCryptPasswordEncoder().encode("padfrhydrh");
		AppUser appUser = new AppUser(1, UUID.randomUUID(), "mail@mail.com", "peter98", hashedPassword, true, true,1);
		when(appUserRepository.findByUsername("peter98")).thenReturn(appUser);

		boolean valid = userPasswordValidator.isValid("password123", context);

		assertThat(valid).isFalse();
	}

	@Test
	@DisplayName("Wenn kein AppUser gefunden wird, wird false zurückgegeben")
	void test_4() {
		ConstraintValidatorContext context = mock(ConstraintValidatorContext.class);
		Cookie[] cookies = {new Cookie("auth_token", "some_value")}; // Korrekt setzen
		when(request.getCookies()).thenReturn(cookies);
		when(jwtService.extractUsername("some_value")).thenReturn("peter98");
		String hashedPassword = new BCryptPasswordEncoder().encode("padfrhydrh");
		AppUser appUser = new AppUser(1, UUID.randomUUID(), "mail@mail.com", "peter98", hashedPassword, true, true, 1);
		when(appUserRepository.findByUsername("peter98")).thenReturn(null);

		boolean valid = userPasswordValidator.isValid("password123", context);

		assertThat(valid).isFalse();
	}



}
