package com.studyhub.authentication.adapter.mail;

import com.studyhub.authentication.model.AppUser;
import com.studyhub.authentication.web.EmailChangeRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import java.time.Duration;

@Service
public class MailRequestService {
	@Value("${mail.api-url}")
	private String mailApiUrl;

	public Mono<Void> sendRegistrationConfirmation(AppUser user) {
		String uri = "%s/new-user-registration".formatted(mailApiUrl);

		return WebClient.create()
				.post()
				.uri(uri)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.bodyValue(new RegistrationRequest(user.getUsername(), user.getMail()))
				.retrieve()
				.onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException("Fehlerhafte Antwort: " + response.statusCode())))
				.bodyToMono(String.class)
				.timeout(Duration.ofSeconds(10))
				.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(10)))  // 3 Retries, dann Exception
				.then();
	}

	public Mono<Void> sendChangeMailInformation(EmailChangeRequest request) {
		String uri = "%s/user-change-mail".formatted(mailApiUrl);

		return WebClient.create()
				.post()
				.uri(uri)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.bodyValue(request)
				.retrieve()
				.onStatus(HttpStatusCode::isError, response -> Mono.error(new RuntimeException("Fehlerhafte Antwort: " + response.statusCode())))
				.bodyToMono(String.class)
				.timeout(Duration.ofSeconds(10))
				.retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(10)))  // 3 Retries, dann Exception
				.then();
	}

	public boolean sendDeleteAllRequest(String username) {
		//TODO IMPLEMENT: when user deletes account, all data should be deleted
		return true;
	}
}
