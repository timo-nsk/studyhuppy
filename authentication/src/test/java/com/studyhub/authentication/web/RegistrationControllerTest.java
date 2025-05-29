package com.studyhub.authentication.web;

import com.studyhub.authentication.model.AppUser;
import com.studyhub.authentication.config.JwtAuthFilter;
import com.studyhub.authentication.service.AppUserDetailsService;
import com.studyhub.authentication.service.RegistrationService;
import com.studyhub.jwt.JWTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@WebAppConfiguration
public class RegistrationControllerTest {

	@Autowired WebApplicationContext context;
	@MockitoBean RegistrationService registrationService;
	@MockitoBean JwtAuthFilter jwtAuthFilter;
	@MockitoBean
	JWTService jwtService;
	@MockitoBean AppUserDetailsService appUserDetailsService;
	MockMvc mvc;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}

}
