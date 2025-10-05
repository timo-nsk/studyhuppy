package com.studyhub.authentication.service;

import com.studyhub.authentication.db.AppUserRepository;
import com.studyhub.authentication.model.AppUser;
import com.studyhub.authentication.model.kontakt.KontaktNachricht;
import com.studyhub.authentication.web.controller.api.KontaktNachrichtRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    private final Logger log = LoggerFactory.getLogger("AdminService");
    private final AppUserRepository appUserRepository;
    private final KontaktNachrichtRepository kontaktNachrichtRepository;

    public AdminService(AppUserRepository appUserRepository, KontaktNachrichtRepository kontaktNachrichtRepository) {
        this.appUserRepository = appUserRepository;
        this.kontaktNachrichtRepository = kontaktNachrichtRepository;
    }

    public List<AppUser> findAllAppUser() {
        return appUserRepository.findAll();
    }

    public void deleteUserById(UUID userId) {
        appUserRepository.deleteByUserId(userId);
    }

    public void saveKontaktNachricht(KontaktNachrichtRequest req) {
        kontaktNachrichtRepository.save(req.toKontaktNachricht());
        log.info(" Saved new KontaktNachricht");
    }
}
