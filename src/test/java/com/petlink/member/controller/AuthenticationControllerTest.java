package com.petlink.member.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.petlink.common.util.jwt.JwtToken;
import com.petlink.config.filter.JwtAuthenticationFilter;
import com.petlink.member.exception.NotFoundMemberException;
import com.petlink.member.exception.NotMatchedPasswordException;
import com.petlink.member.service.AuthenticationService;

@WebMvcTest(controllers = AuthenticationController.class,
	excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
	})
@AutoConfigureMockMvc(addFilters = false)
class AuthenticationControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	private AuthenticationService authenticationService;

	@Test
	@DisplayName("로그인에 성공 시 토큰이 쿠키에 등록된다.")
	void login() throws Exception {
		// given
		String email = "id@google.com";
		String password = "password1234";
		String token = "dummy-token";
		when(authenticationService.login(email, password)).thenReturn(token);

		// when
		mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.param("email", email)
				.param("password", password)
			)// then
			.andExpect(status().isOk())
			.andExpect(cookie().exists(JwtToken.JWT_TOKEN.getTokenName()))
			.andExpect(cookie().value(JwtToken.JWT_TOKEN.getTokenName(), token));

		verify(authenticationService, times(1)).login(email, password);
	}

	@Test
	@DisplayName("회원이 존재하지 않으면 예외가 발생한다.")
	void isNotMember() throws Exception {
		// given
		String email = "id@google.com";
		String password = "wrong-password";

		when(authenticationService.login(email, password)).thenThrow(new NotFoundMemberException());

		// when
		mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.param("email", email)
				.param("password", password)
			)// then
			.andExpect(status().isNotFound());

		verify(authenticationService, times(1)).login(email, password);
	}

	@Test
	@DisplayName("비밀번호가 일치하지 않으면 예외가 발생한다.")
	void isNotMatchedPassword() throws Exception {
		// given
		String email = "id@google.com";
		String password = "wrong-password";

		when(authenticationService.login(email, password)).thenThrow(new NotMatchedPasswordException());

		// when
		mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.param("email", email)
				.param("password", password)
			)// then
			.andExpect(status().isUnauthorized());

		verify(authenticationService, times(1)).login(email, password);
	}

}

