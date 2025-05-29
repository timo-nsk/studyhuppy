package com.studyhub.authentication.web;

import com.studyhub.jwt.JWTService;
import jakarta.validation.Validator;
import com.studyhub.authentication.db.AppUserRepository;
import com.studyhub.authentication.service.AccountService;
import com.studyhub.authentication.service.AuthenticationService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ChangeAccountDetailsControllerTest {

	@Autowired  MockMvc mvc;
	@Autowired Validator validator;
	@Autowired
	JWTService jwtService;
	@MockitoBean AppUserRepository appUserRepository;
	@MockitoBean AccountService accountService;
	@MockitoBean AuthenticationService authenticationService;


}

