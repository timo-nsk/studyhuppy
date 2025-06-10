package com.studyhub.authentication.service;

import com.studyhub.authentication.db.AppUserRepository;
import com.studyhub.authentication.model.AppUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    private final Logger log = LoggerFactory.getLogger("AdminService");
    private final AppUserRepository appUserRepository;

    public AdminService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    public List<AppUser> findAllAppUser() {
        return appUserRepository.findAll();
    }
}
