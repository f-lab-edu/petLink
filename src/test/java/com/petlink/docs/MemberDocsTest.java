package com.petlink.docs;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.payload.JsonFieldType;

import com.petlink.member.controller.MemberController;
import com.petlink.member.dto.request.SignUpRequestDto;
import com.petlink.member.dto.response.MemberInfoResponseDto;
import com.petlink.member.service.MemberService;

@ExtendWith(MockitoExtension.class)
@DisplayName("MemberController 테스트")
class MemberDocsTest extends RestDocsSupport {

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
		ResponseEntity<MemberInfoResponseDto> responseEntity = memberController.signUp(request);

		mockMvc.perform(
				post("/members/signup")
					.content(objectMapper.writeValueAsString(request))
					.contentType(APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isCreated())
			.andDo(document("sign-up",
				requestFields(
					fieldWithPath("name").type(JsonFieldType.STRING).description("이름(닉네임)"),
					fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
					fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
					fieldWithPath("tel").type(JsonFieldType.STRING).description("전화번호"),
					fieldWithPath("zipCode").type(JsonFieldType.STRING).description("우편번호"),
					fieldWithPath("address").type(JsonFieldType.STRING).description("주소"),
					fieldWithPath("detailAddress").type(JsonFieldType.STRING).description("상세주소")
				),
				responseFields(
					fieldWithPath("id").type(JsonFieldType.NUMBER).description("회원번호"),
					fieldWithPath("name").type(JsonFieldType.STRING).description("이름(닉네임)"),
					fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
				)
			));
	}

}
