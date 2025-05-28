package com.studyhub.authentication.service;

import com.studyhub.authentication.db.AppUserRepository;
import com.studyhub.authentication.model.AppUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.utility.TestcontainersConfiguration;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
@DataJdbcTest
@ActiveProfiles("test")
@Rollback(false)
@Sql(scripts = "init_user_db.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
public class AppUserRepositoryTest {

	@Autowired
	AppUserRepository appUserRepository;

	@Test
	@DisplayName("Ein neuer User kann erfolgreich gespeichert werden")
	void test_1() {
		AppUser appUser = new AppUser(null, UUID.randomUUID(), "susi@gmail.com", "susi", "1234", true, true, 1);

		AppUser savedAppUser = appUserRepository.save(appUser);

		assertThat(savedAppUser).isEqualTo(appUser);
	}

	@Test
	@DisplayName("Ein User kann durch seinen username aus der Datenbank geholt werden")
	void test_2() {
		AppUser foundAppUser = appUserRepository.findByUsername("alice123");

		assertThat(foundAppUser.getUsername()).isEqualTo("alice123");
	}

	@Test
	@DisplayName("Wenn kein User mit einem username in der Datenbank ist, wird null zurückgegeben")
	void test_3() {
		AppUser foundAppUser = appUserRepository.findByUsername("susi89");

		assertThat(foundAppUser).isNull();
	}

	@Test
	@DisplayName("Ein User wird anhand der User-Id aus der Datenbank entfernt")
	void test_4() {
		UUID uuid = UUID.fromString("550e8400-e29b-41d4-a716-446655440009");

		appUserRepository.deleteByUserId(uuid);

		AppUser foundAppUser = appUserRepository.findByUsername("judy_rules");
		assertThat(foundAppUser).isNull();
	}

	@Test
	@DisplayName("notificationSubscription wird erfolgreich geändert von false auf true")
	void test_5() {
		appUserRepository.updateNotificationSubscription(true, "davey");

		AppUser foundAppUser = appUserRepository.findByUsername("davey");
		assertThat(foundAppUser.getNotificationSubscription()).isTrue();
	}

	@Test
	@DisplayName("notificationSubscription wird erfolgreich geändert von true auf false")
	void test_6() {
		appUserRepository.updateNotificationSubscription(false, "frank007");

		AppUser foundAppUser = appUserRepository.findByUsername("frank007");
		assertThat(foundAppUser.getNotificationSubscription()).isFalse();
	}

	@Test
	@DisplayName("Der Status für die Notification-Subscription wird erfolgreich zurückgegeben")
	void test_7() {
		Boolean active = appUserRepository.getNotificationSubscription("alice123");

		assertThat(active).isTrue();
	}

	@Test
	@DisplayName("Das Passwort eines Users wird erfolgreich geändert")
	void test_8() {
		UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
		String newPassword = "12345";

		appUserRepository.updatePassword(newPassword, userId);

		AppUser foundAppUser = appUserRepository.findByUsername("alice123");
		assertThat(foundAppUser.getPassword()).isEqualTo(newPassword);
	}

	@Test
	@DisplayName("Das Semester eines Users wird erfolgreich geholt")
	void test_9() {
		Integer semester = appUserRepository.findSemesterByUsername("eve_secure");

		assertThat(semester).isEqualTo(2);
	}

	@Test
	@DisplayName("E-Mail-Adresse eines Users kann erfolgreich geändert werden")
	void test_10() {
		String newMail = "newalice@example.com";
		UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440000");
		appUserRepository.updateMailByUserId(newMail, userId);

		AppUser changedMailAppUser = appUserRepository.findByUsername("alice123");
		assertThat(changedMailAppUser.getMail()).isEqualTo(newMail);
	}

	@Test
	@DisplayName("Ein User kann anhand seiner User-Id gefunden werden")
	void test_11() {
		UUID userId = UUID.fromString("550e8400-e29b-41d4-a716-446655440006");

		AppUser foundUser = appUserRepository.findByUserId(userId);

		assertThat(foundUser.getUserId()).isEqualTo(userId);
		assertThat(foundUser.getUsername()).isEqualTo("grace_hopper");
	}
}
