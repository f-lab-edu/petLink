package com.petlink.member.controller;

import com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper;
import com.petlink.RestDocsSupport;
import com.petlink.common.exception.TokenException;
import com.petlink.member.dto.request.SignUpRequestDto;
import com.petlink.member.dto.response.MemberInfoResponseDto;
import com.petlink.member.dto.response.ResultResponse;
import com.petlink.member.exception.MemberException;
import com.petlink.member.exception.MemberExceptionCode;
import com.petlink.member.service.MemberService;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;

import static com.petlink.common.exception.TokenExceptionCode.INVALID_TOKEN_EXCEPTION;
import static com.petlink.member.dto.Message.AVAILABLE_NAME;
import static com.petlink.member.dto.Message.WITHDRAWAL_SUCCESS;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest extends RestDocsSupport {

    @InjectMocks
    private MemberController memberController;

    @Mock
    private MemberService memberService;

    @Override
    protected Object initController() {
        return new MemberController(memberService);
    }

    @Test
    @DisplayName("회원가입을 진행할 수 있다.")
    void signUpTest() throws Exception {
        SignUpRequestDto request = SignUpRequestDto.builder()
                .name("TestName")
                .email("test@example.com")
                .password("password")
                .tel("1234567890")
                .zipCode("12345")
                .address("TestAddress")
                .detailAddress("TestDetailAddress")
                .build();

        MemberInfoResponseDto response = MemberInfoResponseDto.builder()
                .id(1L)
                .name(request.getName())
                .email(request.getEmail())
                .build();

        when(memberService.signUp(any(SignUpRequestDto.class))).thenReturn(response);

        mockMvc.perform(
                        RestDocumentationRequestBuilders.post("/members/signup")
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(jsonPath("id").value(response.getId()))
                .andExpect(status().isCreated())
                .andDo(MockMvcRestDocumentationWrapper.document("member/sign-up",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름(닉네임)"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("tel").type(JsonFieldType.STRING).description("전화번호"),
                                fieldWithPath("zipCode").type(JsonFieldType.STRING).description("우편번호"),
                                fieldWithPath("address").type(JsonFieldType.STRING).description("주소"),
                                fieldWithPath("detailAddress").optional().type(JsonFieldType.STRING).description("상세주소")
                        ),
                        responseFields(
                                fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원번호"),
                                fieldWithPath("name").type(JsonFieldType.STRING).description("이름(닉네임)"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
                        )
                ));

        verify(memberService, times(1)).signUp(any(SignUpRequestDto.class));
    }

    @Test
    @DisplayName("이름(닉네임)을 중복 검증 할 수 있다.")
    void checkNameTest() throws Exception {
        String testName = "TestName";
        ResultResponse resultResponse = new ResultResponse(false, AVAILABLE_NAME);
        when(memberService.isNameDuplicated(testName)).thenReturn(false);

        mockMvc.perform(
                        RestDocumentationRequestBuilders.get("/members/duplicate/{name}", testName)
                )
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("member/name-duplicate",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("name").description("이름(닉네임)")
                        ),
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.BOOLEAN).description("중복 여부 ( true: 중복, false: 중복 아님 )"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("코드")
                        )
                ));

        verify(memberService, times(1)).isNameDuplicated(testName);
    }

    @Test
    @DisplayName("중복된 이름(닉네임)은 회원가입 할 수 없다.")
    void checkNameFailTest() throws Exception {

        String testName = "TestName";
        when(memberService.isNameDuplicated(testName)).thenThrow(
                new MemberException(MemberExceptionCode.ALREADY_USED_NAME));

        mockMvc.perform(
                RestDocumentationRequestBuilders.get("/members/duplicate/{name}", testName)
        ).andExpect(status().isConflict());

        verify(memberService, times(1)).isNameDuplicated(testName);
    }

    @Test
    @DisplayName("회원을 탈퇴를 진행할 수 있다.")
    void withdrawalMemberTest() throws Exception {
        String token = "로그인 토큰";
        ResultResponse resultResponse = new ResultResponse(true, WITHDRAWAL_SUCCESS);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/members/withdrawal")
                        .cookie(new Cookie("token", token)))  // 쿠키 추가)
                .andExpect(status().isOk())
                .andDo(MockMvcRestDocumentationWrapper.document("member/withdrawal",
                        responseFields(
                                fieldWithPath("result").type(JsonFieldType.BOOLEAN).description("성공 여부"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("메시지"),
                                fieldWithPath("code").type(JsonFieldType.STRING).description("코드")
                        )
                ))
                ;
    }


    @Test
    @DisplayName("로그인 토큰이 유효하지 않으면 회원탈퇴가 실패 한다.")
    void withdrawalMemberTestFail() throws Exception {
        String token = "로그인 토큰";

        doThrow(new TokenException(INVALID_TOKEN_EXCEPTION))
                .when(memberService).withdrawal(token);

        mockMvc.perform(post("/members/withdrawal")
                        .cookie(new Cookie("token", token)))  // 쿠키에 토큰 추가
                .andExpect(status().isBadRequest())
                ;
    }

}
