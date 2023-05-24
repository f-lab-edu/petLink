package com.petlink.member.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.petlink.member.service.LoginService;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

	@InjectMocks
	private LoginController loginController;

	@Mock
	private LoginService loginService;

	private MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
	}

	@Test
	@DisplayName("로그인을 진행할 수 있다.")
	void login() throws Exception {
		String email = "test@email.com";
		String password = "password1234";

		mockMvc.perform(
				MockMvcRequestBuilders.post("/auth/login")
					.param("email", email)
					.param("password", password)
			).andExpect(status().is(HttpStatus.OK.value()))
			.andExpect(content().contentType("application/json"))
			.andReturn();

	}
}
