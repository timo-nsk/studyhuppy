package com.studyhub.authentication.service;

import com.studyhub.authentication.adapter.mail.MailRequestService;
import com.studyhub.authentication.db.AppUserRepository;
import com.studyhub.authentication.model.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

	private final AppUserRepository appUserRepository;
	private final MailRequestService mailRequestService;
	private final Logger log = LoggerFactory.getLogger(RegistrationService.class);

	public RegistrationService(AppUserRepository appUserRepository, MailRequestService mailRequestService) {
		this.appUserRepository = appUserRepository;
		this.mailRequestService = mailRequestService;
	}

	public boolean register(AppUser newUser)  {
		AppUser savedUser = appUserRepository.save(newUser);

		if (savedUser != null) {
			log.info("User registered successfully (%s)".formatted(newUser));
			mailRequestService.sendRegistrationConfirmation(newUser)
					.doOnError(e -> System.out.println("Endgültig fehlgeschlagen: " + e.getMessage()))
					.subscribe();;
			return true;
		} else {
			log.error("Failed registring new user with data: %s".formatted(newUser));
			return false;
		}
	}
}
