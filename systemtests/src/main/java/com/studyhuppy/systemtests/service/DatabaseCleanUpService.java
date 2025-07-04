package com.studyhuppy.systemtests.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DatabaseCleanUpService {

    public DatabaseCleanUpService() {
    }

    @Value("${dummy-data.registation.username}")
    private String dummyRegistrationUser;

    public void deleteDummyRegistrationUser() {
        // TODO: implement Webdriver.create...
    }
}
