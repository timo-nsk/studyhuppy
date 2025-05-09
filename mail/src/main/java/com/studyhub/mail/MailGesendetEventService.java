package com.studyhub.mail;

import com.studyhub.mail.adapter.db.MailGesendetEventRepository;
import org.springframework.stereotype.Service;

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
}
