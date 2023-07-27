package com.petlink.funding.controller;

import com.petlink.RestDocsSupport;
import com.petlink.funding.dto.request.FundingPostDto;
import com.petlink.funding.dto.response.FundingCreateResponse;
import com.petlink.funding.dto.response.FundingImageResponse;
import com.petlink.funding.service.FundingManagementService;
import com.petlink.image.dto.ImageDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import static com.petlink.common.util.date.DateConverter.toLocalDateTime;
import static com.petlink.funding.domain.FundingCategory.FOOD;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.partWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParts;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FundingManagementControllerTest extends RestDocsSupport {

    @InjectMocks
    private FundingManagementController fundingManagementController;

    @Mock
    private FundingManagementService fundingManagementService;

    @Override
    protected Object initController() {
        return new FundingManagementController(fundingManagementService);
    }

    @Test
    @DisplayName("펀딩등록-A(게시글 작성) : API_DOCS")
    void createFundingForApiDocs() throws Exception {
        FundingPostDto postDto = FundingPostDto.builder()
                .managerId(1L)
                .title("펀딩 제목")
                .miniTitle("펀딩 소제목")
                .content("펀딩 내용")
                .category(FOOD)
                .startDate("20210801")
                .endDate("20210831")
                .targetDonation(100000L)
                .build();
        FundingCreateResponse response = FundingCreateResponse.builder()
                .id(1L)
                .registeredAt(toLocalDateTime("20210801"))
                .build();

        when(fundingManagementService.createFunding(any(FundingPostDto.class))).thenReturn(response);

        mockMvc.perform(post("/fundings/manage/create")
                        .content(objectMapper.writeValueAsString(postDto))
                        .contentType(APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("funding-management/create-funding",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("managerId").description("펀딩 관리자 ID"),
                                fieldWithPath("title").description("펀딩 제목"),
                                fieldWithPath("miniTitle").description("펀딩 소제목"),
                                fieldWithPath("content").description("펀딩 내용"),
                                fieldWithPath("category").description("펀딩 카테고리"),
                                fieldWithPath("startDate").description("펀딩 시작일"),
                                fieldWithPath("endDate").description("펀딩 종료일"),
                                fieldWithPath("targetDonation").description("목표 모금액")
                        ),
                        responseFields(
                                fieldWithPath("id").description("펀딩 ID"),
                                fieldWithPath("registeredAt").description("펀딩 등록일")
                        )
                ));


    }

    @Test
    @DisplayName("펀딩등록-B(이미지 업로드) : API_DOCS")
    void uploadImageForApiDocs() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "펀딩 이미지 파일명", "image/png", "펀딩 이미지 파일내용".getBytes());
        ImageDto request = ImageDto.of(image);

        FundingImageResponse response = FundingImageResponse.builder()
                .id(1L)
                .link("펀딩 이미지 링크")
                .name("펀딩 이미지 파일명")
                .uploadedAt(toLocalDateTime("20210801")).build();

        when(fundingManagementService.uploadImage(any(ImageDto.class))).thenReturn(response);

        // JSON 문자열을 멀티파트 요청에 추가합니다.

        mockMvc.perform(multipart("/fundings/manage/image/upload")
                        .file(image)
                        .param("objectName", "펀딩 이미지 파일명")
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("funding-management/upload-image",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestParts(
                                partWithName("image").description("이미지 파일")
                        ),
                        responseFields(
                                fieldWithPath("id").description("펀딩 이미지 ID"),
                                fieldWithPath("link").description("펀딩 이미지 링크"),
                                fieldWithPath("name").description("펀딩 이미지 파일명"),
                                fieldWithPath("uploadedAt").description("펀딩 이미지 업로드 일시")
                        )
                ));

    }

}