package com.petlink.member.controller;

import com.petlink.RestDocsSupport;
import com.petlink.member.dto.request.LoginRequest;
import com.petlink.member.dto.response.LoginResponse;
import com.petlink.member.exception.MemberException;
import com.petlink.member.service.AuthenticationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.petlink.member.exception.MemberExceptionCode.NOT_FOUND_MEMBER_EXCEPTION;
import static com.petlink.member.exception.MemberExceptionCode.NOT_MATCHED_INFOMATION;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest extends RestDocsSupport {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private AuthenticationService authenticationService;

    @Override
    protected Object initController() {
        return new AuthenticationController(authenticationService);
    }

    @Test
    @DisplayName("로그인에 성공 시 토큰이 쿠키에 등록된다.")
    void login() throws Exception {
        // given
        String email = "id@google.com";
        String password = "password1234";
        String token = "dummy-token";

        LoginRequest loginRequest = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        LoginResponse loginResponse = LoginResponse.builder()
                .token(token)
                .build();

        // when
        when(authenticationService.login(email, password)).thenReturn(token);
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
                )
                // then
                .andExpect(status().isOk())
                .andDo(document("member/login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("email").type(JsonFieldType.STRING).description("로그인할 이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("로그인할 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("token").type(JsonFieldType.STRING).description("JWT 토큰")
                        )
                ))
                .andDo(print());

        verify(authenticationService, times(1)).login(email, password);
    }

    @Test
    @DisplayName("회원이 존재하지 않으면 예외가 발생한다.")
    void isNotMember() throws Exception {
        // given
        String email = "id@google.com";
        String password = "wrong-password";

        LoginRequest loginRequest = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        when(authenticationService.login(email, password)).thenThrow(
                new MemberException(NOT_FOUND_MEMBER_EXCEPTION));

        // when
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
                )// then
                .andExpect(status().isNotFound())
                .andDo(print());

        verify(authenticationService, times(1)).login(email, password);
    }

    @Test
    @DisplayName("비밀번호가 일치하지 않으면 예외가 발생한다.")
    void isNotMatchedPassword() throws Exception {
        // given
        String email = "id@google.com";
        String password = "wrong-password";

        LoginRequest loginRequest = LoginRequest.builder()
                .email(email)
                .password(password)
                .build();

        when(authenticationService.login(email, password)).thenThrow(
                new MemberException(NOT_MATCHED_INFOMATION));

        // when
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest))
                )// then
                .andExpect(status().isUnauthorized())
                .andDo(print());

        verify(authenticationService, times(1)).login(email, password);
    }

}
