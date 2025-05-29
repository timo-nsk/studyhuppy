package com.studyhub.authentication.web;

import com.studyhub.authentication.config.JWTService;
import com.studyhub.authentication.config.JwtAuthFilter;
import com.studyhub.authentication.service.AccountService;
import com.studyhub.authentication.service.AppUserDetailsService;
import com.studyhub.authentication.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class UserProfilControllerTest {

	@Autowired
	private WebApplicationContext context;
	private MockMvc mvc;

	@MockitoBean AuthenticationService authenticationService;
	@MockitoBean JwtAuthFilter jwtAuthFilter;
	@MockitoBean
	JWTService jwtService;
	@MockitoBean AppUserDetailsService appUserDetailsService;
	@MockitoBean AccountService accountService;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}



}
