package com.studyhub.mail.application.service.filter;

import com.studyhub.mail.adapter.db.MailGesendetEventRepository;
import com.studyhub.mail.domain.model.MailGesendetEvent;
import com.studyhub.mail.domain.model.MailTyp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MailGesendetEventFilter {

    @Autowired
    private MailGesendetEventRepository repo;


    public void save(MailTyp typ, boolean erfolgreichVersendet) {
        repo.save(MailGesendetEvent.createEventSuccess(typ, erfolgreichVersendet));
    }
}
