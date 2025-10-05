package com.studyhub.mail.application.service;

import com.studyhub.mail.domain.model.MailTyp;
import com.studyhub.mail.adapter.db.MailGesendetEventRepository;
import com.studyhub.mail.domain.model.MailGesendetEvent;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MailGesendetEventService {

	private final MailGesendetEventRepository repo;

	public MailGesendetEventService(MailGesendetEventRepository repo) {
		this.repo = repo;
	}

	public void prepareSavingEvent(MailTyp typ, boolean erfolgreichVersendet) {
		MailGesendetEvent event = MailGesendetEvent.createEventSuccess(typ, erfolgreichVersendet);
		repo.save(event);
	}

	public boolean isMailDbHealthy() {
		Integer result = repo.isMailDbHealthy();
		return result != null;
	}

	public void deleteAllByUsername(UUID userId) {
		repo.deleteAllByUserId(userId);
	}
}
