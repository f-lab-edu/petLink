package com.petlink.member.controller;

import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.petlink.common.util.jwt.JwtToken;
import com.petlink.config.filter.JwtAuthenticationFilter;
import com.petlink.member.exception.MemberException;
import com.petlink.member.exception.MemberExceptionCode;
import com.petlink.member.service.AuthenticationService;

@WebMvcTest(controllers = AuthenticationController.class,
	excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
	})
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
class AuthenticationControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	private AuthenticationService authenticationService;

	@BeforeEach
	public void setUp(WebApplicationContext webApplicationContext,
		RestDocumentationContextProvider restDocumentation) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
			.apply(documentationConfiguration(restDocumentation))
			.build();
	}

	@Test
	@DisplayName("로그인에 성공 시 토큰이 쿠키에 등록된다.")
	void login() throws Exception {
		// given
		String email = "id@google.com";
		String password = "password1234";
		String token = "dummy-token";
		when(authenticationService.login(email, password)).thenReturn(token);

		String requestBodyJson = objectMapper.writeValueAsString(new HashMap<String, String>() {
			{
				put("email", email);
				put("password", password);
			}
		});

		// when
		mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.param("email", email)
				.content(requestBodyJson)
			)
			// then
			.andExpect(status().isOk())
			.andExpect(cookie().exists(JwtToken.JWT_TOKEN.getTokenName()))
			.andExpect(cookie().value(JwtToken.JWT_TOKEN.getTokenName(), token))
			.andDo(document("login",
				requestFields(
					fieldWithPath("email").description("로그인할 이메일"),
					fieldWithPath("password").description("로그인할 비밀번호")
				),
				responseFields(
					fieldWithPath("token").description("JWT 토큰")
				)
			));

		verify(authenticationService, times(1)).login(email, password);
	}

	@Test
	@DisplayName("회원이 존재하지 않으면 예외가 발생한다.")
	void isNotMember() throws Exception {
		// given
		String email = "id@google.com";
		String password = "wrong-password";

		when(authenticationService.login(email, password)).thenThrow(
			new MemberException(MemberExceptionCode.NOT_FOUND_MEMBER_EXCEPTION));

		String requestBodyJson = objectMapper.writeValueAsString(new HashMap<String, String>() {
			{
				put("email", email);
				put("password", password);
			}
		});

		// when
		mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBodyJson)
			)// then
			.andExpect(status().isNotFound())
			.andDo(document("login",
				requestFields(
					fieldWithPath("email").description("로그인할 이메일"),
					fieldWithPath("password").description("로그인할 비밀번호")
				)
			));

		verify(authenticationService, times(1)).login(email, password);
	}

	@Test
	@DisplayName("비밀번호가 일치하지 않으면 예외가 발생한다.")
	void isNotMatchedPassword() throws Exception {
		// given
		String email = "id@google.com";
		String password = "wrong-password";

		when(authenticationService.login(email, password)).thenThrow(
			new MemberException(MemberExceptionCode.NOT_MATCHED_INFOMATION));

		String requestBodyJson = objectMapper.writeValueAsString(new HashMap<String, String>() {
			{
				put("email", email);
				put("password", password);
			}
		});

		// when
		mockMvc.perform(post("/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBodyJson)
			)// then
			.andExpect(status().isUnauthorized())
			.andDo(document("login",
				requestFields(
					fieldWithPath("email").description("로그인할 이메일"),
					fieldWithPath("password").description("로그인할 비밀번호")
				)
			));

		verify(authenticationService, times(1)).login(email, password);
	}


	/*
	 * 단위 테스트의 범위
	 *
	 * 컨트롤러도 하나의 단위로 볼 수 있다.
	 * Bean 을 띄우는 테스트들이
	 * WebMvcTest의 경우 매 CI 마다 속도로 인해 테스트하기 힘들다.
	 *
	 * 테스트의 중요한점은 속도.
	 *
	 *
	 * 통합 테스트는 별개의 클래스로 CI 과정에서는 무시되도록
	 * */
}
