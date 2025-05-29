package com.studyhub.authentication.web;

import com.studyhub.jwt.JWTService;
import jakarta.servlet.http.Cookie;
import com.studyhub.authentication.config.RedirectEntryPoint;
import com.studyhub.authentication.model.AppUser;
import com.studyhub.authentication.config.JwtAuthFilter;
import com.studyhub.authentication.service.AccountService;
import com.studyhub.authentication.service.AppUserDetailsService;
import com.studyhub.authentication.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
	@MockitoBean RedirectEntryPoint redirectEntryPoint;
	@MockitoBean AccountService accountService;
	@MockitoBean UserPasswordValidator userPasswordValidator;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
	}



}
