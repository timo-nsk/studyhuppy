package com.studyhub.mail;

import com.studyhub.mail.domain.model.MailTyp;
import com.studyhub.mail.adapter.db.MailGesendetEventRepository;
import com.studyhub.mail.domain.model.MailGesendetEvent;
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

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestcontainersConfiguration.class)
@DataJdbcTest
@ActiveProfiles("test")
@Rollback(false)
@Sql(scripts = "drop.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class MailGesendetEventRepositoryTest {

	@Autowired
	MailGesendetEventRepository repo;

	@Test
	@DisplayName("Wenn eine Mail erfolgreich versendet wurde, wird das Event abgespeichert")
	void test_1() {
		MailGesendetEvent event = new MailGesendetEvent(null, UUID.randomUUID(), LocalDateTime.now(), MailTyp.LERNEN_NOTIFICATION, true);

		MailGesendetEvent saved = repo.save(event);

		assertThat(saved.id()).isEqualTo(1);
		assertThat(saved.userId()).isEqualTo(event.userId());
		assertThat(saved.erfolgreichVersendet()).isTrue();
	}

	@Test
	@DisplayName("Wenn eine Mail nicht erfolgreich versendet wurde, wird das Event abgespeichert")
	void test_2() {
		MailGesendetEvent event = new MailGesendetEvent(null, UUID.randomUUID(), LocalDateTime.now(), MailTyp.LERNEN_NOTIFICATION, false);

		MailGesendetEvent saved = repo.save(event);

		assertThat(saved.id()).isEqualTo(2);
		assertThat(saved.userId()).isEqualTo(event.userId());
		assertThat(saved.erfolgreichVersendet()).isFalse();
	}

	@Test
	@DisplayName("DB wird erfolgreich für health check gepinged")
	void test_3() {
		assertThat(repo.isMailDbHealthy()).isNotNull();
	}
}
